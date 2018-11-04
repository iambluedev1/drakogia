package fr.drakogia.shop.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.inventories.CustomInventoryManager;
import fr.drakogia.shop.inventory.gui.SellInventory;

public class FarmCommand extends AbstractCommand {

	public FarmCommand() {
		super("farm", Arrays.asList("farm"), "View Shop");
		this.permission = "drakogia.farm";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (!sender.hasPermission("drakogia.farm") && !(sender instanceof Player)) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
			return;
		}
		
		Player player = (Player) sender;
		CustomInventoryManager.getInstance().open(new SellInventory(), player);
	}

	@Override
	public void helpInfo(CommandSender player) {}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return Arrays.asList();
	}
}