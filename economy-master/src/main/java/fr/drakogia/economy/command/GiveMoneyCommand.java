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

public class GiveMoneyCommand extends AbstractCommand {

	public GiveMoneyCommand() {
		super("moneygive", Arrays.asList("moneygive"), "Give Money To Player");
		this.permission = "drakogia.economy.give";
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (!sender.hasPermission("drakogia.economy.give")) {
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

		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(toPlayer);


		drakogiaPlayer.updateData("money", (int) drakogiaPlayer.getData("money") + amount);
		PlayerManager.getInstance().getModel().update(new Field("user_money", drakogiaPlayer.getData("money")), new Field("user_login", drakogiaPlayer.getName()));

		toPlayer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_receive_success", String.valueOf(amount)));
		sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_send_success", String.valueOf(amount), drakogiaPlayer.getName()));

		Integer sender_id = -1;
		if(sender instanceof Player){
			sender_id = PlayerManager.getInstance().get((Player) sender).getId();
		}else{
			sender_id = 0;
		}

		Transactions.getInstance().save(sender_id, drakogiaPlayer.getId(), amount, EnumTransactionType.GIVE);
		
		if(DrakogiaEconomy.getInstance().getDrakogiaConfig().getFileConfig().getInt("transaction.alert") <= amount){
			for(Player staff : Bukkit.getOnlinePlayers()){
				if(staff.hasPermission("drakogia.economy.alert")){
					staff.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_alert", String.valueOf(amount), sender.getName(), drakogiaPlayer.getName()));
				}
			}
		}
	}

	@Override
	public void helpInfo(CommandSender player) {
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_help_give", ""));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return (args.size() == 1) ? null : Arrays.asList();
	}

}
