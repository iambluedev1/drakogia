package fr.drakogia.gifts.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.inventories.CustomInventoryManager;
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.gifts.inventory.SendInventory;
import fr.drakogia.gifts.manager.GiftsManager;
import fr.drakogia.gifts.object.Gift;

public class SendCommand extends AbstractCommand {

	public SendCommand() {
		super("don", Arrays.asList("don"), "Make a Gift");
		this.permission = "drakogia.dons.send";
	}

	@Override
	public void handle(CommandSender s, List<String> args) {
		if (!s.hasPermission("drakogia.dons.send") && !(s instanceof Player)) {
			s.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
			return;
		}
		
		if(args.size() != 1){
			this.helpInfo(s);
			return;
		}
			
		Player sender = (Player) s;
		Player recipient = Bukkit.getPlayerExact(args.get(0));

        if (recipient == null) {
            sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_online", ""));
            return;
        }
        
        if (sender.equals(recipient)) {
            sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_send_not_to_you", ""));
            return;
        }

        if (!GiftsManager.getInstance().canSendGift(sender) && !sender.hasPermission("drakogia.dons.nocooldown")) {
            int hours = (int) Math.floor((GiftsManager.getInstance().getHowManyTime(sender)) / 3600);
            int minutes = (int) ((GiftsManager.getInstance().getHowManyTime(sender)) % 3600 / 60);
            sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_send_cooldown",  hours + "h" + minutes));
            return;
        }

        if (GiftsManager.getInstance().isIgnored(recipient, sender)) {
            sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_send_error_ignore_you", recipient.getName()));
            return;
        }
        
        Player player = (Player) sender;
		DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(player);
		
		if(drakogiaPlayer.getData("sendingGiftRecipient") != null){
			drakogiaPlayer.updateData("sendingGiftRecipient", recipient.getName());
		}else{
			drakogiaPlayer.addData("sendingGiftRecipient", recipient.getName());
		}
		
		Gift gift = new Gift(drakogiaPlayer.getId(), PlayerManager.getInstance().getModel().getId(recipient.getName()), Integer.valueOf((int) (System.currentTimeMillis() / 1000)), null);
		GiftsManager.getInstance().setSendingGift(player, gift);
		
		CustomInventoryManager.getInstance().open(new SendInventory(), player);
	}

	@Override
	public void helpInfo(CommandSender player) {
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_help_dons_send", ""));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return null;
	}

}
