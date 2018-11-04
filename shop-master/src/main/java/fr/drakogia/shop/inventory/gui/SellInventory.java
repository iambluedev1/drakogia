package fr.drakogia.shop.inventory.gui;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.drakogia.api.economy.EnumTransactionType;
import fr.drakogia.api.heads.CustomHeads;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.heads.HeadBuilder;
import fr.drakogia.core.inventories.CustomInventory;
import fr.drakogia.core.inventories.InventoryUtil;
import fr.drakogia.core.items.ItemBuilder;
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.economy.table.Transactions;
import fr.drakogia.shop.inventory.InventoryPage;
import fr.drakogia.shop.inventory.InventoryType;
import fr.drakogia.shop.inventory.ItemRegistry;
import fr.drakogia.shop.manager.InventoryManager;
import xyz.anana.pine.core.field.Field;

public class SellInventory extends CustomInventory{

	@Override
	public String name() {
		return DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_gui_title_sell", "");
	}

	@Override
	public Inventory contents(Player p) {
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		
		int page = 1;
		
		if(drakogiaPlayer.getData("sellInventoryPage") != null){
			page = (int) drakogiaPlayer.getData("sellInventoryPage");
			drakogiaPlayer.updateData("sellInventoryShouldRender", false);
		}else{
			drakogiaPlayer.addData("sellInventoryPage", page);
			drakogiaPlayer.addData("sellInventoryShouldRender", false);
		}
		
		Inventory view = Bukkit.createInventory(null, this.getSize(), this.name());
		
		InventoryPage items = InventoryManager.getInstance().getPage(InventoryType.SELL, page);
		
		for(ItemRegistry registry : items.getItems()){
			view.addItem(registry.getItem());
		}
		
		if(page > 1){
			view.setItem(45, new HeadBuilder(CustomHeads.ARROW_LEFT.getName()).withDisplayName("Précédent").build());
		}
		
		view.setItem(49, new ItemBuilder("Page " + page + "/" + InventoryManager.getInstance().countPages(InventoryType.SELL)).withType(Material.BOOK).build());
		
		if(page < InventoryManager.getInstance().countPages(InventoryType.SELL)){
			view.setItem(53, new HeadBuilder(CustomHeads.ARROW_RIGHT.getName()).withDisplayName("Suivant").build());
		}
		
		return view;
	}

	@Override
	public void dynamicContent(Player p) {
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		 if((boolean) drakogiaPlayer.getData("sellInventoryShouldRender")){
			 Inventory inv = p.getOpenInventory().getTopInventory();
			 Inventory inv2 = this.contents(p);
			 
			 for (int i = 0; i < inv.getSize(); i++) {
				 inv.setItem(i, inv2.getItem(i));
			 }
		 }
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(Player p, Inventory inv, ItemStack current, ClickType type, InventoryClickEvent event) {
		event.setCancelled(true);
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		
		if(current.getType() == Material.BOOK && current.getItemMeta().getDisplayName().equals("Suivant")){
			drakogiaPlayer.updateData("sellInventoryPage", (int) drakogiaPlayer.getData("sellInventoryPage") + 1);
			drakogiaPlayer.updateData("sellInventoryShouldRender", true);
			return;
		}
		
		if(current.getType() == Material.BOOK && current.getItemMeta().getDisplayName().equals("Précédent")){
			drakogiaPlayer.updateData("sellInventoryPage", (int) drakogiaPlayer.getData("sellInventoryPage") - 1);
			drakogiaPlayer.updateData("sellInventoryShouldRender", true);
			return;
		}
		
		if(current.getType() == Material.AIR){
			return;
		}
		
		
		InventoryPage items = InventoryManager.getInstance().getPage(InventoryType.SELL, (int) drakogiaPlayer.getData("sellInventoryPage"));
		
		for(ItemRegistry registry : items.getItems()){
			ItemStack item = registry.getItem();
			
			if(item != null && item.hasItemMeta() && item.getType() == current.getType() && item.getAmount() == current.getAmount() && item.getData().getData() == current.getData().getData()){
				ItemStack lisaSimpson = new ItemStack(item.getType());
				ItemMeta itM = item.getItemMeta();
				itM.setLore(Arrays.asList());
				lisaSimpson.setDurability(item.getDurability());
				lisaSimpson.setItemMeta(itM);
				
				
				int amountInInv = InventoryUtil.getAmount(p, lisaSimpson);
				
				if(amountInInv > 0){
					int multiplier = 1;
					int price = registry.getPrice();
					
					switch(type) {
						case RIGHT:
							if(amountInInv < 64){
								p.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_sell_no_item_specified", item.getType().toString(), "64"));
								return;
							}
							multiplier = 64;
							price = registry.getPrice() * multiplier;
							break;
						case MIDDLE:
							multiplier = amountInInv;
							price = registry.getPrice() * multiplier;
							break;
						case LEFT:
						default:
					}
					
					Transactions.getInstance().save(0, drakogiaPlayer.getId(), price, EnumTransactionType.SHOP);
					
					drakogiaPlayer.updateData("money", price + registry.getPrice());
					PlayerManager.getInstance().getModel().update(new Field("user_money", drakogiaPlayer.getData("money")), new Field("user_login", drakogiaPlayer.getName()));
					
					p.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_sell_success", String.valueOf(price), item.getType().toString(), String.valueOf(multiplier)));
					
					InventoryUtil.takeAmount(p, lisaSimpson, multiplier);
				}else{
					p.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_sell_no_item", item.getType().toString()));
				}
			}
		}
	}

	@Override
	public void onClose(Player p, Inventory inv, InventoryCloseEvent event) {
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		drakogiaPlayer.updateData("sellInventoryShouldRender", false);
		drakogiaPlayer.updateData("sellInventoryPage", 1);
	}
	
	public void open(final Player player){
		player.openInventory(this.contents(player));
		if(isDynamic()){
			if(player.getOpenInventory() != null){
				BukkitRunnable tasks = new BukkitRunnable(){
					public void run() {
						if(player.getOpenInventory().getTitle().equals(name())){
							dynamicContent(player);
						}else{
							cancel();
						}
					}
				};
				tasks.runTaskTimer(DrakogiaApi.getInstance(), 0, 0);
			}
		}
	}

	@Override
	public int getSize() {
		return 9*6;
	}

	@Override
	public Boolean isDynamic() {
		return true;
	}
}
