package fr.drakogia.rank.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.rank.manager.RankManager;

public class ReloadCommand extends AbstractCommand {

	public ReloadCommand() {
		super("rankreload", Arrays.asList("rankreload"), "Tool to Reload Ranks' Cache");
		this.permission = "drakogia.rank.reload";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (sender instanceof ConsoleCommandSender || (sender instanceof Player && (sender.hasPermission("drakogia.rank.reload")))) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_rank_reload_start", ""));
			System.out.println("[RANK] RELOAD RANKS");
			RankManager.getInstance().load();
			System.out.println("[RANK] RANKS RELOADED");
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_rank_reload_end", ""));
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
