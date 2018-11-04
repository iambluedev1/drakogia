package fr.drakogia.chat.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.chat.DrakogiaChat;
import fr.drakogia.core.DrakogiaApi;

public class LockChatCommand extends AbstractCommand {

	public LockChatCommand() {
		super("chatlock", Arrays.asList("chatlock"), "Tool to Lock Chat");
		this.permission = "drakogia.chat.toggle";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (sender instanceof ConsoleCommandSender || (sender instanceof Player && (sender.hasPermission("drakogia.chat.toggle")))) {
			if (DrakogiaChat.getInstance().isChatLocked()) {
				sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_chat_unlocked", ""));
			} else {
				sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_chat_locked", ""));
			}
			
			DrakogiaChat.getInstance().toggleChat();
		} else {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
		}
	}

	@Override
	public void helpInfo(CommandSender player) {
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return null;
	}

}
