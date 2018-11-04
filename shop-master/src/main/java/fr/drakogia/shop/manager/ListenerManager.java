package fr.drakogia.shop.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.drakogia.api.manager.IListenerManager;
import fr.drakogia.shop.DrakogiaShop;
import fr.drakogia.shop.listener.RemoteCommandEvent;
import lombok.Getter;

@Getter
public class ListenerManager implements IListenerManager {
	
	private PluginManager pluginManager;
	
	public ListenerManager(){
		this.pluginManager = Bukkit.getPluginManager();
	}
	
	@Override
	public void setupManager() {
		this.pluginManager.registerEvents(new RemoteCommandEvent(), DrakogiaShop.getInstance());
	}

}
