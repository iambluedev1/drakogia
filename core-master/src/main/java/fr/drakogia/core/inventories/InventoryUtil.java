package fr.drakogia.core.inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

	public static Integer getAmount(Player p, ItemStack it) {
		if (it == null) return 0;
		Integer amount = 0;
		
		for (int i = 0; i < 36; i++) {
			ItemStack slot = p.getInventory().getItem(i);
			if (slot == null || !slot.isSimilar(it)) continue;
			
			amount += slot.getAmount();
		}
		
		return amount;
	}
	
	public static void takeAmount(Player p, ItemStack it, Integer amount){
		ItemStack search = it.clone();
		search.setAmount(amount);
		p.getInventory().removeItem(search);
	}
	
	public static Inventory copyInventory(Inventory inventory, String title) {
        Inventory inv = Bukkit.createInventory(null, 3 * 9, title);

        for (int i = 0; i < 27; i++) {
            if (inventory.getItem(i) != null)
            	inv.setItem(i, inventory.getItem(i));
        }
        
        return inv;
    }

}
