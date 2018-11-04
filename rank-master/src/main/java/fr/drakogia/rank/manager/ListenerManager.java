package fr.drakogia.rank.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.drakogia.api.manager.IListenerManager;
import fr.drakogia.rank.DrakogiaRank;
import fr.drakogia.rank.listener.AsyncPlayerChat;
import fr.drakogia.rank.listener.PlayerFirstJoin;
import fr.drakogia.rank.listener.PlayerJoin;
import fr.drakogia.rank.listener.RemoteCommandEvent;
import lombok.Getter;

@Getter
public class ListenerManager implements IListenerManager {

	private PluginManager pluginManager;
	
	public ListenerManager(){
		this.pluginManager = Bukkit.getPluginManager();
	}
	
	@Override
	public void setupManager() {
		this.pluginManager.registerEvents(new PlayerJoin(), DrakogiaRank.getInstance());
		this.pluginManager.registerEvents(new AsyncPlayerChat(), DrakogiaRank.getInstance());
		this.pluginManager.registerEvents(new PlayerFirstJoin(), DrakogiaRank.getInstance());
		this.pluginManager.registerEvents(new RemoteCommandEvent(), DrakogiaRank.getInstance());
	}
}
