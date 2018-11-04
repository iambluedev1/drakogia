package fr.drakogia.shop.inventory.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.inventories.CustomInventory;

public class TrashInventory extends CustomInventory {

	@Override
	public String name() {
		return DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_gui_title_trash", "");
	}

	@Override
	public Inventory contents(Player p) {
		Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER, this.name());
		return inv;
	}

	@Override
	public void dynamicContent(Player p) {}

	@Override
	public void onClick(Player p, Inventory inv, ItemStack current, ClickType type, InventoryClickEvent event) {
		event.setCancelled(false);
	}

	@Override
	public void onClose(Player p, Inventory inv, InventoryCloseEvent event) {}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public Boolean isDynamic() {
		return false;
	}

}
