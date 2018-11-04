package fr.drakogia.gifts.inventory;

import java.util.Arrays;
import java.util.Objects;

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
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.gifts.manager.GiftsManager;
import fr.drakogia.gifts.object.Gift;

public class MoneyInventory extends CustomInventory {

	@Override
	public String name() {
		return "§aAjouter de l'argent";
	}

	@Override
	public Inventory contents(Player p) {
		Inventory view = Bukkit.createInventory(null, this.getSize(), this.name());
		Gift gift = GiftsManager.getInstance().getSendingGift(p);
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		
		if (drakogiaPlayer.getData("moneyGiftInventoryShouldRender") != null) {
			drakogiaPlayer.updateData("moneyGiftInventoryShouldRender", false);
		}else{
			drakogiaPlayer.addData("moneyGiftInventoryShouldRender", false);
		}
		
		for (int i = 0; i < view.getSize(); i++) {
            view.setItem(i, new ItemBuilder(" ").withType(Material.STAINED_GLASS_PANE).withData((byte) 7).build());
        }

		int i = 0;
		
        for (int amount : Arrays.asList(50, 100, 1000, 10000)) {
            view.setItem(14 + i, new ItemBuilder("§a+ " + amount + "€").withType(Material.STAINED_GLASS_PANE).withData((byte) 5).build());
            i++;
        }
        
        i = 0;
        
        for (int amount : Arrays.asList(10000, 1000, 100, 50)) {
            view.setItem(9 + i, new ItemBuilder("§c- " + amount + "€").withType(Material.STAINED_GLASS_PANE).withData((byte) 14).build());
            i++;
        }

        view.setItem(4, new ItemBuilder("§aMontant actuel : §b" + gift.getAmount()).withType(Material.GOLD_INGOT).build());
        view.setItem(view.getSize() - 1, new ItemBuilder("§7Retour").withType(Material.ARROW).build());
        
		return view;
	}

	@Override
	public void dynamicContent(Player p) {
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		if ((boolean) drakogiaPlayer.getData("moneyGiftInventoryShouldRender")) {
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
		
		if(current.getType() == Material.ARROW){
			if(current.hasItemMeta()){
				if(current.getItemMeta().getDisplayName().equals("§7Retour")){
					Gift gift = GiftsManager.getInstance().getSendingGift(p);
					
					if (gift == null) {
	                    p.closeInventory();
	                    return;
	                }
					
					CustomInventoryManager.getInstance().open(new SendInventory(), p);
				}
			}
		}
		
		if(current.getType() == Material.STAINED_GLASS_PANE){
			if(current.getItemMeta().getDisplayName().startsWith("§c-") || current.getItemMeta().getDisplayName().startsWith("§a+")){
				String name = current.getItemMeta().getDisplayName();
				
				String method;
                Integer amount;
                try {
                    String[] args = name.split(" ");

                    method = args[0].replaceAll("([§][a-z0-9])", "");
                    amount = Integer.valueOf(args[1].substring(0, args[1].length() - 1));
                } catch (NumberFormatException e) {
                    event.setCancelled(true);
                    return;
                }

                Gift gift = GiftsManager.getInstance().getSendingGift(p);
                
                if (gift == null){
                    event.setCancelled(true);
                    return;
                }
                
                DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
                if (Objects.equals(method, "+")) {
                    if ((int) drakogiaPlayer.getData("money") > (amount + gift.getAmount())) {
                        gift.setAmount(gift.getAmount() + amount);
                        amount = gift.getAmount();
                        drakogiaPlayer.updateData("moneyGiftInventoryShouldRender", true);
                    } else {
                        event.setCancelled(true);
                        p.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_no_enough_money"));
                        return;
                    }
                } else if (Objects.equals(method, "-")) {
                    if (!(gift.getAmount() < amount)) {
                        gift.setAmount(gift.getAmount() - amount);
                        amount = gift.getAmount();
                        drakogiaPlayer.updateData("moneyGiftInventoryShouldRender", true);
                    } else {
                        event.setCancelled(true);
                        return;
                    }
                } else {
                    event.setCancelled(true);
                    return;
                }
			}
		}
		
	}

	@Override
	public void onClose(Player p, Inventory inv, InventoryCloseEvent event) {
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(p);
		drakogiaPlayer.updateData("moneyGiftInventoryShouldRender", false);
	}

	@Override
	public int getSize() {
		return 3*9;
	}

	@Override
	public Boolean isDynamic() {
		return true;
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

}
