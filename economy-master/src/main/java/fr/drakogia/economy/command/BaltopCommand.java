package fr.drakogia.economy.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.economy.table.Players;

public class BaltopCommand extends AbstractCommand {

	public BaltopCommand() {
		super("baltop", Arrays.asList("baltop"), "View The Server's Baltop");
		this.permission = "drakogia.economy.baltop";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (!sender.hasPermission("drakogia.economy.baltop") && !(sender instanceof Player)) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
			return;
		}
		
		if(args.size() == 0){
			this.displayBaltop(sender, Players.getInstance().getRanking(), 0);
		} else if(args.size() == 1){
			Integer page = 0;
			
			try {
				page = Integer.getInteger(args.get(1));
				page = page - 1 < 0 ? 0 : page - 1;
			} catch (NumberFormatException e) {
				sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_error_baltop_page", ""));
				return;
			}
			
			this.displayBaltop(sender, Players.getInstance().getRanking(page), page);
		}else{
			this.helpInfo(sender);
		}
	}
	
	/*
	 * @author rovergames
	 */
	private void displayBaltop(CommandSender sender, Map<String, Integer> baltop, int page){
		if (baltop.size() == 0) {
            sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_baltop_empty_page", ""));
        } else {
            sender.sendMessage("§e----- §6Baltop §e- §6Page " + (page + 1) + " §e-----");

            int i = 1;
            for (Map.Entry<String, Integer> entry : baltop.entrySet()) {
                String color = "";
                if (page == 0) {
                    switch (i) {
                        case 1: {
                            color = "§" + ChatColor.YELLOW.getChar();
                            break;
                        }

                        case 2: {
                            color = "§" + ChatColor.GREEN.getChar();
                            break;
                        }

                        case 3: {
                            color = "§" + ChatColor.DARK_GREEN.getChar();
                            break;
                        }
                    }
                }
                sender.sendMessage(color + (i + ((page) * 10)) + ". " + entry.getKey() + ": " + entry.getValue() + "€");
                i++;
            }
        }

	}

	@Override
	public void helpInfo(CommandSender player) {
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_help_baltop", ""));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return Arrays.asList();
	}

}
