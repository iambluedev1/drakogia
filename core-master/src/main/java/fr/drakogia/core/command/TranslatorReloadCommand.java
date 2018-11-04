package fr.drakogia.core.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;

public class TranslatorReloadCommand extends AbstractCommand {

	public TranslatorReloadCommand() {
		super("reloadi18n", Arrays.asList("reloadi18n"), "Reload translation's cache");
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if ((sender instanceof Player)) {
			Player player = (Player)sender;
			if ((player.isOp()) || (player.hasPermission("drakogia.i18n.reload"))) {
				DrakogiaApi.getInstance().getTranslator().clearCache();
				player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_i18n_reload_success", ""));
			}else{
				player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_i18n_cant_reload", ""));
			}
		}
	}

	@Override
	public void helpInfo(CommandSender player) {}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return null;
	}

}
