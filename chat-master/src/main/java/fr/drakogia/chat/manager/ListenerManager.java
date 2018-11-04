package fr.drakogia.chat.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import fr.drakogia.api.manager.IListenerManager;
import fr.drakogia.chat.DrakogiaChat;
import fr.drakogia.chat.listeners.AsyncPlayerChat;
import fr.drakogia.chat.listeners.RemoteCommandEvent;
import lombok.Getter;

@Getter
public class ListenerManager implements IListenerManager {

	private PluginManager pluginManager;
	
	public ListenerManager(){
		this.pluginManager = Bukkit.getPluginManager();
	}
	
	@Override
	public void setupManager() {
		this.pluginManager.registerEvents(new AsyncPlayerChat(), DrakogiaChat.getInstance());
		this.pluginManager.registerEvents(new RemoteCommandEvent(), DrakogiaChat.getInstance());
	}
}
