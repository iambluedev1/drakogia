package fr.drakogia.economy.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.economy.EnumTransactionType;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.economy.DrakogiaEconomy;
import fr.drakogia.economy.table.Transactions;
import xyz.anana.pine.core.field.Field;

public class PayCommand extends AbstractCommand {

	public PayCommand() {
		super("pay", Arrays.asList("pay"), "Pay Player For Something");
		this.permission = "drakogia.economy.pay";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (!sender.hasPermission("drakogia.economy.pay") && !(sender instanceof Player)) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
			return;
		}
		
		if (args.size() != 2) {
			this.helpInfo(sender);
			return;
		}
		
		Player toPlayer = Bukkit.getPlayerExact(args.get(0));
		Integer amount = 0;

		try {
			amount = Integer.valueOf(args.get(1));
			
			if(amount < 0){
				sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_error_amount", ""));
				return;
			}
		} catch (NumberFormatException e) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_error_amount", ""));
			return;
		}
		
		if (toPlayer == null) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_online", ""));
			return;
		}
		
		DrakogiaPlayer drakogiaToPlayer = PlayerManager.getInstance().get(toPlayer);
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get((Player) sender);
		
		if(drakogiaToPlayer.getName().equals(drakogiaPlayer.getName())){
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_error_pay_yourself", ""));
			return;
		}
		
		if((int) drakogiaPlayer.getData("money") <= amount){
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_error_hasnt_money", ""));
			return;
		}
		
		drakogiaPlayer.updateData("money", (int) drakogiaPlayer.getData("money") - amount); 
		drakogiaToPlayer.updateData("money", (int) drakogiaToPlayer.getData("money") + amount); 
		
		toPlayer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_receive_from_success", String.valueOf(amount), drakogiaPlayer.getName()));
		sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_send_to_success", String.valueOf(amount), drakogiaToPlayer.getName()));
	
		Transactions.getInstance().save(drakogiaPlayer.getId(), drakogiaToPlayer.getId(), amount, EnumTransactionType.PAY);
		
		PlayerManager.getInstance().getModel().update(new Field("user_money", drakogiaPlayer.getData("money")), new Field("user_login", drakogiaPlayer.getName()));
		PlayerManager.getInstance().getModel().update(new Field("user_money", drakogiaToPlayer.getData("money")), new Field("user_login", drakogiaToPlayer.getName()));
		
		if(DrakogiaEconomy.getInstance().getDrakogiaConfig().getFileConfig().getInt("transaction.alert") <= amount){
			for(Player staff : Bukkit.getOnlinePlayers()){
				if(staff.hasPermission("drakogia.economy.alert")){
					staff.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_alert", String.valueOf(amount), drakogiaPlayer.getName(), drakogiaToPlayer.getName()));
				}
			}
		}
	}

	@Override
	public void helpInfo(CommandSender player) {
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_help_pay", ""));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return (args.size() == 1) ? null : Arrays.asList();
	}

}
