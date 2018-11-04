package fr.drakogia.api.player;

import java.util.Map;

import org.bukkit.entity.Player;

import fr.drakogia.api.manager.IManager;

public interface IPlayerManager<T> extends IManager {

	Map<String, T> getPlayers();
	
	void init(Player player);
	
	T get(Player player);
	
	T get(String name);
	
	void remove(Player player);
	
}
