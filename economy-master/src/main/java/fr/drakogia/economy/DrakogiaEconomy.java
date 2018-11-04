package fr.drakogia.economy;

import org.bukkit.plugin.java.JavaPlugin;

import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.command.CommandManager;
import fr.drakogia.economy.command.BaltopCommand;
import fr.drakogia.economy.command.GiveMoneyCommand;
import fr.drakogia.economy.command.MoneyCommand;
import fr.drakogia.economy.command.PayCommand;
import fr.drakogia.economy.command.RemoveMoneyCommand;
import fr.drakogia.economy.config.DrakogiaConfig;
import fr.drakogia.economy.manager.ListenerManager;
import lombok.Getter;

@Getter
public class DrakogiaEconomy extends JavaPlugin {

	@Getter
	private static DrakogiaEconomy instance;
	
	private DrakogiaConfig drakogiaConfig;
	
	@Override
	public void onEnable(){
		instance = this;
		
		System.out.println("Load Configs");
		this.drakogiaConfig = new DrakogiaConfig(DrakogiaApi.getInstance());
		this.drakogiaConfig.setupConfig();
		
		System.out.println("Setup Listeners");
		new ListenerManager().setupManager();
		
		System.out.println("Init commands");
		CommandManager.getInstance().register(new GiveMoneyCommand());
		CommandManager.getInstance().register(new PayCommand());
		CommandManager.getInstance().register(new MoneyCommand());
		CommandManager.getInstance().register(new BaltopCommand());
		CommandManager.getInstance().register(new RemoveMoneyCommand());
	}
	
	@Override
	public void onDisable(){}
}
