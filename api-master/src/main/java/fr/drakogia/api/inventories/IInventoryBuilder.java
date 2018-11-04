package fr.drakogia.api.inventories;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import fr.drakogia.api.builder.IBuilder;

public interface IInventoryBuilder<T> extends IBuilder<Inventory> {

	List<String[]> getLines();
	
	Map<String, ItemStack> getItems();
	
	String getName();
	
	T addLine(String[] line);
	
	T addItem(String code, ItemStack itemStack);
	
	Inventory build(Player player);
	
	Inventory buildWithPlayerInv(Player player);
	
	InventoryView updateInventoryWithPlayerInv(Player player, Player target);
	
	InventoryView updateInventoryWithPlayerInv(Player player, Player target, Boolean enderchest);
}
