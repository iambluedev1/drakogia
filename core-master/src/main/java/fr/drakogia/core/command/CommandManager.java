package fr.drakogia.core.command;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.command.ICommandManager;
import fr.drakogia.core.DrakogiaApi;
import lombok.Getter;

@Getter
public class CommandManager implements ICommandManager {

	private static CommandManager instance;
	
	private List<AbstractCommand> commands;
	
	private static final Constructor<PluginCommand> PLUGIN_COMMAND_CONSTRUCTOR;
	private static final CommandMap COMMAND_MAP;
	
	static
	{
		try
		{
			PLUGIN_COMMAND_CONSTRUCTOR = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			PLUGIN_COMMAND_CONSTRUCTOR.setAccessible(true);
			Field commandMap = SimplePluginManager.class.getDeclaredField("commandMap");
			commandMap.setAccessible(true);
			COMMAND_MAP = (CommandMap) commandMap.get(Bukkit.getPluginManager());
		}
		catch (ReflectiveOperationException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	private CommandManager(){
		this.commands = new ArrayList<AbstractCommand>();
	}
	
	public void register(AbstractCommand toRegister) {
		try {
			PluginCommand command = PLUGIN_COMMAND_CONSTRUCTOR.newInstance(toRegister.getName(), DrakogiaApi.getInstance());
			
			command.getAliases().addAll(toRegister.getCmd());
			command.setExecutor(new CommandExecutor() {
				public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
					return CommandManager.getInstance().onCommand(arg0, arg1, arg2, arg3);
				}
			});
			command.setTabCompleter(new TabCompleter() {
				public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
					return CommandManager.getInstance().onTabComplete(arg0, arg1, arg2, arg3);
				}
			});
			
			if(toRegister.getPermission() != null){
				command.setPermission(toRegister.getPermission());
			}
			
			COMMAND_MAP.register(toRegister.getDescription(), command);
			
			this.commands.add(toRegister);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}
	
	public static CommandManager getInstance(){
		if(instance == null){
			instance = new CommandManager();
		}
		
		return instance;
	}
	
	public boolean onCommand(CommandSender commandSender, Command command, String string, String[] arrstring) {
        for (AbstractCommand abstractCommand : CommandManager.getInstance().getCommands()) {
            if (!abstractCommand.cmd.contains(string.toLowerCase())) continue;
            if (arrstring.length == 0) {
                abstractCommand.handle(commandSender, Arrays.asList(arrstring));
                continue;
            }
            List<String> list = Arrays.asList(arrstring);
            abstractCommand.handle(commandSender, list);
        }
        return true;
    }
	
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
		for (AbstractCommand abstractCommand : CommandManager.getInstance().getCommands()) {
        	if (!abstractCommand.cmd.contains(alias.toLowerCase())) continue;
        	if (args.length == 0) {
                return abstractCommand.onTabComplete(sender, Arrays.asList(args));
            }
            List<String> list = Arrays.asList(args);
            return abstractCommand.onTabComplete(sender, list);
        }
		return null;
	}

	
}
