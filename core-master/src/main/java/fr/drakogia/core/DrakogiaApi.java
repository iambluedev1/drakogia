package fr.drakogia.core;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import fr.drakogia.api.i18n.Lang;
import fr.drakogia.api.utils.Callback;
import fr.drakogia.core.command.CommandManager;
import fr.drakogia.core.command.TranslatorReloadCommand;
import fr.drakogia.core.config.DrakogiaConfig;
import fr.drakogia.core.i18n.Translator;
import fr.drakogia.core.loader.Loaders;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.core.redis.ChannelHandler;
import fr.drakogia.core.redis.Redis;
import fr.drakogia.modules.xp.BottleCommand;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import xyz.anana.pine.core.connector.MariaDBConnector;
import xyz.anana.pine.core.connector.MySQLConnector;
import xyz.anana.pine.core.database.Database;

@Getter
public class DrakogiaApi extends JavaPlugin {

	@Getter
	private static DrakogiaApi instance;

	private Translator translator;
	private DrakogiaConfig drakogiaConfig;
	private Database database;
	private PlayerManager playerManager;
	
	private Redis redis;

	@Override
	public void onLoad() {
		try {
			Loaders.getInstance().loadLibraries(false);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		if (Bukkit.getScoreboardManager() != null) {
			if (Bukkit.getScoreboardManager().getMainScoreboard() != null) {
				if (Bukkit.getScoreboardManager().getMainScoreboard().getTeams() != null) {
					if (!Bukkit.getScoreboardManager().getMainScoreboard().getTeams().isEmpty()) {
						for (Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
							team.unregister();
						}
					}
				}
			}
		}
	}

	@Override
	public void onEnable() {
		instance = this;

		this.drakogiaConfig = new DrakogiaConfig(this);
		this.drakogiaConfig.setupConfig();

		this.database = new Database(this.drakogiaConfig.getFileConfig().getString("mysql.host"),
				this.drakogiaConfig.getFileConfig().getString("mysql.dbname"),
				this.drakogiaConfig.getFileConfig().getString("mysql.username"),
				this.drakogiaConfig.getFileConfig().getString("mysql.password"),
				this.drakogiaConfig.getFileConfig().getInt("mysql.port"),
				((this.drakogiaConfig.getFileConfig().getString("mysql.type").equals("mariadb"))
						? new MariaDBConnector() : new MySQLConnector()));
		
		System.out.println("Testing connection");
		this.database.connect();
		this.database.close();
		
		if(this.drakogiaConfig.getFileConfig().getString("env").equals("prod")){
			this.redis = new Redis(this.drakogiaConfig.getFileConfig().getString("redis.host"), this.drakogiaConfig.getFileConfig().getInt("redis.port"));
			System.out.println("Successfully connected to redis");
			
			new Thread(new Runnable(){
				@Override
				public void run() {
					DrakogiaApi.this.getRedis().get(new Callback<Jedis>() {
						@Override
						public void call(Jedis jedis) {
							jedis.subscribe(new ChannelHandler(), "commands");
							System.out.println("Successfully registered channels to jedis pub/sub");
						}
					});
				}
			}).start();
		}
		
		this.translator = new Translator(Lang.FRENCH);
		
		CommandManager.getInstance().register(new TranslatorReloadCommand());
		CommandManager.getInstance().register(new BottleCommand());
		
		this.playerManager = PlayerManager.getInstance();
		System.out.println("Api Loaded");
	}

	@Override
	public void onDisable() {
		System.out.println("Api unLoaded");
	}
}
