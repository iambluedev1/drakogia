package fr.drakogia.gifts.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.inventories.CustomInventory;
import fr.drakogia.core.inventories.CustomInventoryManager;
import fr.drakogia.core.items.ItemBuilder;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.gifts.DrakogiaGifts;
import fr.drakogia.gifts.manager.GiftsManager;
import fr.drakogia.gifts.object.Gift;

public class SendInventory extends CustomInventory {

	@Override
	public String name() {
		return "Faire un don";
	}

	@Override
	public Inventory contents(Player p) {
		Inventory view = Bukkit.createInventory(null, this.getSize(), this.name());

		Gift gift = GiftsManager.getInstance().getSendingGift(p);
		
		
		for (int i = 0; i < 9; i++) {
			if (i == 0) {
				view.setItem(3 * 9 + i, new ItemBuilder("§aAjouter de l'argent").withType(Material.GOLD_INGOT).withLore(new String[]{
						"Montant actuel: " + gift.getAmount() + "€"
				}).build());
			} else if (i == 8) {
				view.setItem(3 * 9 + i, new ItemBuilder("§cAnnuler").withType(Material.ARROW).build());
			} else if (i != 4) {
				view.setItem(3 * 9 + i, new ItemBuilder(" ").withType(Material.STAINED_GLASS_PANE).withData((byte)7) .build());
			} else {
				view.setItem(3 * 9 + i, new ItemBuilder("§2Envoyer").withType(Material.STAINED_GLASS_PANE).withData((byte)13) .build());
			}
		}
		
		if(gift.getInventory() == null)
			gift.setInventory(view);
        
		for (int i = 0; i < 27; i++) {
            if (gift.getInventory().getItem(i) != null){
            	ItemStack item = gift.getInventory().getItem(i);
        		view.setItem(i, item);
            }
        }

		return view;
	}

	@Override
	public void dynamicContent(Player p) {}

	@Override
	public void onClick(Player p, Inventory inv, ItemStack current, ClickType type, InventoryClickEvent event) {
		event.setCancelled(false);
		
		if(current.getType() == Material.ARROW && current.getItemMeta().getDisplayName().equals("§cAnnuler")){
			event.setCancelled(true);
			ItemStack[] items = inv.getContents();
			for (int i = 0; i < 27; i++) {
	            ItemStack item = items[i];
	            if (item != null)
	                p.getWorld().dropItemNaturally(p.getLocation(), item);
	        }
			GiftsManager.getInstance().setSendingGift(p, null);
			p.closeInventory();
			
			return;
		} 
		
		if(current.getType() == Material.STAINED_GLASS_PANE){
			if (current.getItemMeta().getDisplayName().equals(" ") || current.getItemMeta().getDisplayName().equals("§2Envoyer")){
				if (event.getCurrentItem().getItemMeta().getDisplayName().equals("§2Envoyer")) {
					Gift gift = GiftsManager.getInstance().getSendingGift(p);
					
					if (gift == null) {
                        p.closeInventory();
                        event.setCancelled(true);
                        return;
                    }

					
					Player recipient = Bukkit.getPlayer(PlayerManager.getInstance().getModel().getName(gift.getRecipient()));
					
					if (recipient == null) {
                        p.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_online", ""));
                        event.setCancelled(true);
                        return;
                    }

					 boolean empty = true;
                     for (int i = 0; i < 27; i++) {
                         if (inv.getItem(i) != null) empty = false;
                     }

                     if (empty && gift.getAmount() > 0) {
                    	 p.performCommand("pay " + recipient.getName() + " " + gift.getAmount());

                         event.setCancelled(true);
                         p.closeInventory();
                         GiftsManager.getInstance().setSendingGift(p, null);
                         return;
                     }
                     
                     if (empty) {
                         event.setCancelled(true);
                         return;
                     }

                     p.closeInventory();

                     gift.setInventory(inv);
                     GiftsManager.getInstance().addGift(recipient, gift);

                     GiftsManager.getInstance().setSendingGift(p, null);

                     GiftsManager.getInstance().hadSendGift(p);

                     p.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_sucess_sent_to", recipient.getName()));
                     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + recipient.getName() + " {\"text\": \"" + DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_sucess_receive", p.getName()) + "\", \"clickEvent\":{\"action\":\"run_command\",\"value\":\"/donbox\"}}");
				}
				event.setCancelled(true);
			}
			
			return;
		}
		
		if (current.getType() == Material.GOLD_INGOT) {
			if(current.getItemMeta().getDisplayName().equals("§aAjouter de l'argent")){
				Gift gift = GiftsManager.getInstance().getSendingGift(p);
				
				if (gift == null) {
                    p.closeInventory();
                    event.setCancelled(true);
                    return;
                }
				
				gift.setInventory(inv);
				CustomInventoryManager.getInstance().open(new MoneyInventory(), p);
				event.setCancelled(true);
			}
			
			return;
		}
	}

	@Override
	public void onClose(Player p, Inventory inv, InventoryCloseEvent event) {
		if (GiftsManager.getInstance().isSendingGift(p)) {
			Gift gift = GiftsManager.getInstance().getSendingGift(p);
			
            if (gift != null) {
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                    	if(!p.isOnline()){
                    		cancel();
                    	}
                        if (GiftsManager.getInstance().isSendingGift(p) && !p.getOpenInventory().getTopInventory().getTitle().equals("§aAjouter de l'argent")) {
                        	CustomInventoryManager.getInstance().open(new SendInventory(), p);
                        }
                    }
                };
                runnable.runTaskLaterAsynchronously(DrakogiaGifts.getInstance(), 0);
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
