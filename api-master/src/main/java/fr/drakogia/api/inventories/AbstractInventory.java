package fr.drakogia.api.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractInventory {

	public abstract String name();
	
	public abstract Inventory contents(Player p);
	
	public abstract void dynamicContent(Player p);
	
	public abstract void onClick(Player p, Inventory inv, ItemStack current, ClickType type, InventoryClickEvent event);

	public abstract void onClose(Player p, Inventory inv, InventoryCloseEvent event);

	public abstract int getSize();
	
	public abstract Boolean isDynamic();
	
	public Inventory inv;
	
	public abstract void open(final Player player);
	
	public abstract void open(final Player player, Object... params);
	
	public Inventory getInventory(){
		return this.inv;
	}
	
}
