package fr.drakogia.shop.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.shop.inventory.InventoryType;
import fr.drakogia.shop.inventory.ItemRegistry;
import fr.drakogia.shop.manager.InventoryManager;
import fr.drakogia.shop.table.Items;

public class ReloadCommand extends AbstractCommand {

	public ReloadCommand() {
		super("shopreload", Arrays.asList("shopreload"), "Tool to Reload Shop's Cache");
		this.permission = "drakogia.shop.reload";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (sender instanceof ConsoleCommandSender || (sender instanceof Player && (sender.hasPermission("drakogia.rank.reload")))) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_reload_start", ""));
			System.out.println("[SHOP] RELOAD SHOP");
			
			Map<InventoryType, List<ItemRegistry>> items = Items.getInstance().getItems();
			InventoryManager.getInstance().clear();
			InventoryManager.getInstance().format(InventoryType.BUY, items.get(InventoryType.BUY));
			InventoryManager.getInstance().format(InventoryType.SELL, items.get(InventoryType.SELL));
			
			System.out.println("[SHOP] SHOP RELOADED");
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_reload_end", ""));
		}else{
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
		}
	}

	@Override
	public void helpInfo(CommandSender player) {}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return null;
	}

}