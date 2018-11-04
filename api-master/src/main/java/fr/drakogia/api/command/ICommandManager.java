package fr.drakogia.api.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.drakogia.api.manager.IManager;

public interface ICommandManager extends IManager {

	void register(AbstractCommand toRegister);
	
	boolean onCommand(CommandSender commandSender, Command command, String string, String[] arrstring);
	
	List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);
}
