package fr.drakogia.gifts.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.inventories.CustomInventoryManager;
import fr.drakogia.gifts.inventory.BoxInventory;

public class ViewCommand extends AbstractCommand {

	public ViewCommand() {
		super("donbox", Arrays.asList("donbox"), "Dons Ignore Command");
		this.permission = "drakogia.dons.view";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (!sender.hasPermission("drakogia.dons.ignore") && !(sender instanceof Player)) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
			return;
		}
		
		Player player = (Player) sender;
		CustomInventoryManager.getInstance().open(new BoxInventory(), player);
	}

	@Override
	public void helpInfo(CommandSender player) {}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return Arrays.asList();
	}
	
}
