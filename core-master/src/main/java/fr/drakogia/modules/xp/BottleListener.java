package fr.drakogia.modules.xp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.inventories.InventoryUtil;

public class BottleListener implements Listener {

	@EventHandler
	public void onItemUse(PlayerInteractEvent event){
		if (((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) && (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.EXP_BOTTLE || event.getPlayer().getInventory().getItemInOffHand().getType() == Material.EXP_BOTTLE)) {
			Player player = event.getPlayer();
			
			ItemStack item = null;
			
			if(player.getInventory().getItemInMainHand().getType() == Material.EXP_BOTTLE){
				item = player.getInventory().getItemInMainHand();
			} else if(player.getInventory().getItemInOffHand().getType() == Material.EXP_BOTTLE){
				item = player.getInventory().getItemInOffHand();
			}
			
			if (item.hasItemMeta()) {
				ItemMeta im = item.getItemMeta();
				if(im.getLore().size() == 5){
					if(im.getLore().get(0).equals(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", ""))
						&& im.getLore().get(4).equals(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", ""))
						&& im.getLore().get(2).startsWith(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_bottlexp_lore_text_prefix", ""))){
						event.setCancelled(true);
						
						String count = im.getLore().get(2).replace(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_bottlexp_lore_text_prefix", ""), "");
						count = count.trim();
						count = count.substring(2);
						
						Integer amount = Integer.valueOf(count);
						
						if(item.getAmount() > 1){
							InventoryUtil.takeAmount(player, item, 1);
						}else{
							player.getInventory().removeItem(new ItemStack[] { item });
						}
						
						player.giveExpLevels(amount);
						player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_bottlexp_give_msg", count, ((amount > 1) ? "x" : ""), ((amount > 1) ? "s" : "")));
					}
				}
			}
		}
	}
	
}
