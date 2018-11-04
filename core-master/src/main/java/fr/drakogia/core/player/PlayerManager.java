package fr.drakogia.core.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.drakogia.api.events.PlayerFirstJoinEvent;
import fr.drakogia.api.events.PlayerPostJoinEvent;
import fr.drakogia.api.player.IPlayerManager;
import fr.drakogia.core.DrakogiaApi;
import lombok.Getter;

@Getter
public class PlayerManager implements IPlayerManager<DrakogiaPlayer>, Listener {

	private static PlayerManager instance;
	private Map<String, DrakogiaPlayer> players;
	private PlayerModel model;

	private PlayerManager() {
		this.players = new HashMap<String, DrakogiaPlayer>();
		this.model = new PlayerModel();
	}

	public static PlayerManager getInstance() {
		if (instance == null) {
			instance = new PlayerManager();
			Bukkit.getServer().getPluginManager().registerEvents(instance, DrakogiaApi.getInstance());
		}

		return instance;
	}

	@Override
	public void init(Player player) {
		if (this.players.get(player.getName()) == null) {
			this.players.put(player.getName(), new DrakogiaPlayer(player));
		}
	}

	@Override
	public DrakogiaPlayer get(Player player) {
		return this.players.get(player.getName());
	}

	@Override
	public DrakogiaPlayer get(String name) {
		if(!this.players.containsKey(name)){
			return null;
		}
		return this.players.get(name);
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		if(!this.model.existPlayer(event.getPlayer().getName())){
			this.model.add(event.getPlayer().getName(), event.getAddress().getHostAddress());
			Bukkit.getPluginManager().callEvent(new PlayerFirstJoinEvent(event.getPlayer()));
		}
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		PlayerManager.getInstance().init(event.getPlayer());
		
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(event.getPlayer());
		drakogiaPlayer.setId(this.model.getId(drakogiaPlayer.getName()));
		drakogiaPlayer.addData("lastConnected", (System.currentTimeMillis() / 1000));
		this.model.updateLastConnected(drakogiaPlayer.getName());
		
		Bukkit.getScheduler().runTaskLater(DrakogiaApi.getInstance(), new Runnable() {
			@Override
			public void run() {
				Bukkit.getPluginManager().callEvent(new PlayerPostJoinEvent(event.getPlayer()));
			}
		}, 20*2);
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(event.getPlayer());
		
		if(drakogiaPlayer == null) return;
		this.updateConnectionDuration(drakogiaPlayer);
		drakogiaPlayer.getTablist().unregister();
		PlayerManager.getInstance().remove(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(event.getPlayer());
		
		if(drakogiaPlayer == null) return;
		this.updateConnectionDuration(drakogiaPlayer);
		drakogiaPlayer.getTablist().unregister();
		PlayerManager.getInstance().remove(event.getPlayer());
	}

	private void updateConnectionDuration(DrakogiaPlayer player){
		Long start = (Long) player.getData("lastConnected");
		Long end = (System.currentTimeMillis() / 1000);
		
		this.model.updateConnectionDuration(player.getName(), this.model.getConnectionDuration(player.getName()) + (end - start));
	}
	
	@Override
	public void remove(Player player) {
		if (this.players.get(player.getName()) != null) {
			this.players.remove(player.getName());
		}
	}
}
