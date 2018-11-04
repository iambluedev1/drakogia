package fr.drakogia.gifts.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.inventories.CustomInventory;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.gifts.manager.GiftsManager;

public class ViewInventory extends CustomInventory {

	private Player player;
	
	@Override
	public String name() {
		return "Voir le don de " + PlayerManager.getInstance().getModel().getName(GiftsManager.getInstance().getViewingGift(this.player).getSender());
	}

	@Override
	public Inventory contents(Player p) {
		Inventory view = Bukkit.createInventory(null, this.getSize(), this.name());
		return view;
	}

	@Override
	public void dynamicContent(Player p) {}

	@Override
	public void onClick(Player p, Inventory inv, ItemStack current, ClickType type, InventoryClickEvent event) {
		
	}

	@Override
	public void onClose(Player p, Inventory inv, InventoryCloseEvent event) {
		
	}
	
	public void open(final Player player){
		this.player = player;
		
		if(!GiftsManager.getInstance().isViewingGift(player)){
			player.closeInventory();
			return;
		}
		
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
		return 4 * 9;
	}

	@Override
	public Boolean isDynamic() {
		return false;
	}

}
