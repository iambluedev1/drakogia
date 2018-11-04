package fr.drakogia.gifts.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.drakogia.api.manager.IListenerManager;
import fr.drakogia.gifts.DrakogiaGifts;
import fr.drakogia.gifts.listener.PlayerPostJoin;
import lombok.Getter;

@Getter
public class ListenerManager implements IListenerManager {

	private PluginManager pluginManager;
	
	public ListenerManager(){
		this.pluginManager = Bukkit.getPluginManager();
	}

	@Override
	public void setupManager() {
		this.pluginManager.registerEvents(new PlayerPostJoin(), DrakogiaGifts.getInstance());
	}

}
