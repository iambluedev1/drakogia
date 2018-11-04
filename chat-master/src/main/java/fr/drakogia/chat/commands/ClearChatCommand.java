package fr.drakogia.chat.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;

public class ClearChatCommand extends AbstractCommand {

	public ClearChatCommand() {
		super("clearchat", Arrays.asList("clearchat"), "Tool to Clear the Chat");
		this.permission = "drakogia.chat.clear";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (sender instanceof ConsoleCommandSender || (sender instanceof Player && (sender.hasPermission("drakogia.chat.clear")))) {
            for (int i = 0; i < 100;) {
                Bukkit.broadcastMessage("");
                i++;
            }
        } else {
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
