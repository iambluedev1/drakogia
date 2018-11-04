package fr.drakogia.economy.command;

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

public class MoneyCommand extends AbstractCommand{

	public MoneyCommand() {
		super("money", Arrays.asList("money"), "View Player's Balance");
		this.permission = "drakogia.economy.money";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (!sender.hasPermission("drakogia.economy.money") && !(sender instanceof Player)) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
			return;
		}
		
		if(sender.hasPermission("drakogia.economy.money") && args.size() == 0){
			DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get((Player) sender);
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_view_balance", String.valueOf(drakogiaPlayer.getData("money"))));
		}else if(sender.hasPermission("drakogia.economy.money.other") && args.size() == 1){
			Player toPlayer = Bukkit.getPlayerExact(args.get(0));
			
			if (toPlayer == null) {
				sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_online", ""));
				return;
			}
			
			DrakogiaPlayer drakogiaToPlayer = PlayerManager.getInstance().get(toPlayer);
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_view_balance_of", String.valueOf(drakogiaToPlayer.getData("money")), drakogiaToPlayer.getName()));
		}else{
			this.helpInfo(sender);
			return;
		}
	}

	@Override
	public void helpInfo(CommandSender player) {
		if(player.hasPermission("drakogia.economy.money.other")){
			player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_help_money_2", ""));
		}else{
			player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_help_money_1", ""));
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return null;
	}
}
