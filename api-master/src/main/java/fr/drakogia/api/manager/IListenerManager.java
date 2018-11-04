package fr.drakogia.api.manager;

import org.bukkit.plugin.PluginManager;

public interface IListenerManager extends IManager {

	void setupManager();
	
	PluginManager getPluginManager();
}
