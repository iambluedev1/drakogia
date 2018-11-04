package fr.drakogia.core.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class CustomInventoryListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		if(current != null){
			int invID = CustomInventoryManager.getInstance().getInventoryID(player.getUniqueId());
			if (invID != -1) {
				CustomInventory cInv = CustomInventoryManager.getInstance().getInventory(invID);
				if (cInv == null) return;

				if (cInv.name().equals(event.getInventory().getName())) {
					event.setCancelled(true);
					cInv.onClick(player, event.getInventory(), current, event.getClick(), event);
				}
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event){
			int invID = CustomInventoryManager.getInstance().getInventoryID(event.getPlayer().getUniqueId());
			if (invID != -1) {
				CustomInventory cInv = CustomInventoryManager.getInstance().getInventory(invID);
				if (cInv == null) return;

				if (cInv.name().equals(event.getInventory().getName()))
					CustomInventoryManager.getInstance().closeInventory(invID, event);
			}
	}
}
