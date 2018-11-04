package fr.drakogia.gifts.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.gifts.manager.GiftsManager;

public class IgnoreCommand extends AbstractCommand {

	public IgnoreCommand() {
		super("donignore", Arrays.asList("donignore"), "Dons Ignore Command");
		this.permission = "drakogia.dons.ignore";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (!sender.hasPermission("drakogia.dons.ignore") && !(sender instanceof Player)) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
			return;
		}
		
		if(args.size() == 0){
			this.helpInfo(sender);
			return;
		}
		
		if (args.size() != 2) {
			if(!args.get(0).equalsIgnoreCase("list")){
				this.helpInfo(sender);
				return;
			}
		}
		
		
		
		 Player ignorer = (Player) sender;
		
		if (args.get(0).equalsIgnoreCase("add") || args.get(0).equalsIgnoreCase("remove")) {
            Player ignored = Bukkit.getPlayerExact(args.get(1));

            if (ignored == null && args.get(0).equalsIgnoreCase("add")) {
            	ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_online", ""));
                return;
            }

            if (args.get(0).equalsIgnoreCase("add")) {
                if (ignored.equals(ignorer)) {
                	ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_error_no_to_you", ""));
                	return;
                }
                
                if (!GiftsManager.getInstance().isIgnored(ignorer, ignored)) {
                	GiftsManager.getInstance().addIgnore(ignorer, ignored);
                    ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_success_ignored", ignored.getName()));
                } else {
                	ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_error_already_ignored", ignored.getName()));
                }
            } else {
            	DrakogiaPlayer player = PlayerManager.getInstance().get(ignorer);
            	
                if (!GiftsManager.getInstance().isIgnored(player.getId(), PlayerManager.getInstance().getModel().getId(args.get(1)))) {
                	ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_error_dont_ignored", args.get(1)));
                } else {
                	GiftsManager.getInstance().removeIgnore(player.getId(), PlayerManager.getInstance().getModel().getId(args.get(1)), true);
                	ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_success_un_ignored", args.get(1)));
                }
            }
            
            return;
        } else if (args.get(0).equalsIgnoreCase("list")) {
        	List<String> ignored = GiftsManager.getInstance().getIgnored(ignorer);
            if (ignored.size() == 0) {
            	ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_error_no_ignored", ""));
                return;
            }
            
            String msg = "";
            ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line", ""));
            ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_msg_list", ""));
            
            for (String player : ignored) {
                msg += player + "/";
            }
            
            if (msg.length() > 0) {
                sender.sendMessage(msg.substring(0, msg.length() - 1));
            }
            
            ignorer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line", ""));
            
            return;
        }

		this.helpInfo(sender);
	}

	@Override
	public void helpInfo(CommandSender player) {
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line", ""));
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_help_ignore_1", ""));
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_help_ignore_2", ""));
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_help_ignore_3", ""));
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line", ""));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		if (args.size() < 2) {
            List<String> actions = Arrays.asList("add", "remove", "list");
            List<String> tab = new ArrayList<String>();
            for (String action : actions) {
                if (action.startsWith(args.get(0))) tab.add(action);
            }
            return tab;
        }
		
		if(args.size() == 2 && args.get(0).equalsIgnoreCase("remove")){
			return GiftsManager.getInstance().getIgnored((Player) sender);
		}

		return null;
	}

}
