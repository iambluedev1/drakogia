package fr.drakogia.gifts.inventory;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.drakogia.api.heads.CustomHeads;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.heads.HeadBuilder;
import fr.drakogia.core.inventories.CustomInventory;
import fr.drakogia.core.inventories.CustomInventoryManager;
import fr.drakogia.core.items.ItemBuilder;
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.gifts.manager.GiftsManager;
import fr.drakogia.gifts.object.Gift;

public class BoxInventory extends CustomInventory {

	@Override
	public String name() {
		return "Mes dons";
	}

	@Override
	public Inventory contents(Player p) {
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);

		int page = 1;

		if (drakogiaPlayer.getData("boxInventoryPage") != null) {
			page = (int) drakogiaPlayer.getData("boxInventoryPage");
			drakogiaPlayer.updateData("boxInventoryShouldRender", false);
		} else {
			drakogiaPlayer.addData("boxInventoryPage", page);
			drakogiaPlayer.addData("boxInventoryShouldRender", false);
		}

		Inventory view = Bukkit.createInventory(null, this.getSize(), this.name());

		List<Gift> gifts = GiftsManager.getInstance().getPage(p, page - 1);
		System.out.println(GiftsManager.getInstance().getGifts().toString());
		
		for (Gift gift : gifts) {
			view.addItem(new HeadBuilder(PlayerManager.getInstance().getModel().getName(gift.getSender())).withDisplayName(PlayerManager.getInstance().getModel().getName(gift.getSender())).withLore(gift.getLore())
				.build());
		}

		if (page > 1) {
			view.setItem(45, new HeadBuilder(CustomHeads.ARROW_LEFT.getName()).withDisplayName("Précédent").build());
		}

		view.setItem(49, new ItemBuilder("Page " + page + "/" + GiftsManager.getInstance().countPages(p))
				.withType(Material.BOOK).build());

		if (page < GiftsManager.getInstance().countPages(p)) {
			view.setItem(53, new HeadBuilder(CustomHeads.ARROW_RIGHT.getName()).withDisplayName("Suivant").build());
		}

		return view;
	}

	@Override
	public void dynamicContent(Player p) {
		if(!p.isOnline()){
			return;
		}
		
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		
		if ((boolean) drakogiaPlayer.getData("boxInventoryShouldRender")) {
			Inventory inv = p.getOpenInventory().getTopInventory();
			Inventory inv2 = this.contents(p);

			for (int i = 0; i < inv.getSize(); i++) {
				inv.setItem(i, inv2.getItem(i));
			}
		}
	}

	@Override
	public void onClick(Player p, Inventory inv, ItemStack current, ClickType type, InventoryClickEvent event) {
		event.setCancelled(true);
		
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		
		if(current.getType() == Material.BOOK && current.getItemMeta().getDisplayName().equals("Suivant")){
			drakogiaPlayer.updateData("boxInventoryPage", (int) drakogiaPlayer.getData("boxInventoryPage") + 1);
			drakogiaPlayer.updateData("boxInventoryShouldRender", true);
			return;
		}
		
		if(current.getType() == Material.BOOK && current.getItemMeta().getDisplayName().equals("Précédent")){
			drakogiaPlayer.updateData("boxInventoryPage", (int) drakogiaPlayer.getData("boxInventoryPage") - 1);
			drakogiaPlayer.updateData("boxInventoryShouldRender", true);
			return;
		}
		
		if(current.getType() == Material.AIR){
			return;
		}
			
		for(Gift gift : GiftsManager.getInstance().getGifts(p)){
			if(current.getItemMeta().getLore().equals(gift.getLore())){
				GiftsManager.getInstance().setViewingGift(p, gift);
				p.closeInventory();
				System.out.println(GiftsManager.getInstance().getViewingGift(p).getTime());
				//CustomInventoryManager.getInstance().open(new ViewInventory(), p);
				return;
			}
		}
	}

	@Override
	public void onClose(Player p, Inventory inv, InventoryCloseEvent event) {
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		drakogiaPlayer.updateData("boxInventoryShouldRender", false);
		drakogiaPlayer.updateData("boxInventoryPage", 1);
	}

	public void open(final Player player){
		player.openInventory(this.contents(player));
		if(isDynamic()){
			if(player.getOpenInventory() != null){
				BukkitRunnable tasks = new BukkitRunnable(){
					public void run() {
						if(!player.isOnline()) cancel();
							
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
		return 9 * 6;
	}

	@Override
	public Boolean isDynamic() {
		return true;
	}
}
