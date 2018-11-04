package fr.drakogia.shop.inventory.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.inventories.CustomInventory;
import fr.drakogia.core.inventories.CustomInventoryManager;
import fr.drakogia.core.items.ItemBuilder;

public class MainInventory extends CustomInventory {

	@Override
	public Inventory contents(Player p) {
		Inventory view = Bukkit.createInventory(null, this.getSize(), this.name());
		
		view.setItem(10, new ItemBuilder("Grades").withType(Material.EYE_OF_ENDER).build());
		view.setItem(16, new ItemBuilder("Spawners").withType(Material.MOB_SPAWNER).build());
		view.setItem(21, new ItemBuilder("Ventes").withType(Material.CONCRETE).withData((byte) 14).build());
		view.setItem(23, new ItemBuilder("Achats").withType(Material.CONCRETE).withData((byte) 5).build());
		view.setItem(28, new ItemBuilder("Kits").withType(Material.EMERALD).build());
		view.setItem(34, new ItemBuilder("Site").withType(Material.SKULL_ITEM).withData((byte) 5).build());
		return view;
	}

	@Override
	public void dynamicContent(Player p) {}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(Player p, Inventory inv, ItemStack current, ClickType type, InventoryClickEvent event) {
		event.setCancelled(true);
		if(current.getType() == Material.CONCRETE && current.getData().getData() == 14){
			CustomInventoryManager.getInstance().open(new SellInventory(), p);
		}
		
		if(current.getType() == Material.CONCRETE && current.getData().getData() == 5){
			CustomInventoryManager.getInstance().open(new BuyInventory(), p);
		}
	}

	@Override
	public void onClose(Player p, Inventory inv, InventoryCloseEvent event) {}

	@Override
	public int getSize() {
		return 9*5;
	}

	@Override
	public Boolean isDynamic() {
		return false;
	}

	@Override
	public String name() {
		return DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_gui_title_main", "");
	}

}
