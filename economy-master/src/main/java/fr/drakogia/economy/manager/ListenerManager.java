package fr.drakogia.economy.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.drakogia.api.manager.IListenerManager;
import fr.drakogia.economy.DrakogiaEconomy;
import fr.drakogia.economy.listener.PlayerFirstJoin;
import fr.drakogia.economy.listener.PlayerPostJoin;
import fr.drakogia.economy.listener.RemoteCommandEvent;
import lombok.Getter;

@Getter
public class ListenerManager implements IListenerManager {

	private PluginManager pluginManager;
	
	public ListenerManager(){
		this.pluginManager = Bukkit.getPluginManager();
	}

	@Override
	public void setupManager() {
		this.pluginManager.registerEvents(new PlayerFirstJoin(), DrakogiaEconomy.getInstance());
		this.pluginManager.registerEvents(new PlayerPostJoin(), DrakogiaEconomy.getInstance());
		this.pluginManager.registerEvents(new RemoteCommandEvent(), DrakogiaEconomy.getInstance());
	}

}
