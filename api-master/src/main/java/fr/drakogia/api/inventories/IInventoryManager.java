package fr.drakogia.api.inventories;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import fr.drakogia.api.manager.IManager;

public interface IInventoryManager<T> extends IManager {

	Integer addMenu(T inv);
	
	void open(Integer id, Player player);
	
	Integer getInventoryID(UUID uuid);
	
	T getInventory(Integer id);
	
	void closeInventory(int id, InventoryCloseEvent event);
	
	Integer getId();
}
