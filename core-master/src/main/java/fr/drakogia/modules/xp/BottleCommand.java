package fr.drakogia.modules.xp;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.drakogia.api.command.AbstractCommand;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.items.ItemBuilder;

public class BottleCommand extends AbstractCommand {

	public BottleCommand() {
		super("bottlexp", Arrays.asList("bottlexp", "bouteillexp"), "BottleXp");
		this.permission = "drakogia.bottlexp";
		
		Bukkit.getServer().getPluginManager().registerEvents(new BottleListener(), DrakogiaApi.getInstance());
	}

	@Override
	public void handle(CommandSender sender, List<String> args) {
		if (!sender.hasPermission("drakogia.bottlexp") && !(sender instanceof Player)) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
			return;
		}
		
		if(args.size() < 2){
			
			Integer amount = 0;
			Player player = (Player) sender;
			
			if(player.getLevel() <= 0){
				sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_bottlexp_error_nolevel", ""));
				return;
			}
			
			if(args.size() == 1){
				try {
					amount = Integer.valueOf(args.get(0));
					
					if(amount < 0){
						sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_error_amount", ""));
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_economy_error_amount", ""));
					return;
				}
				
				if(player.getLevel() < amount){
					sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_bottlexp_error_no_enough_level", ""));
					return;
				}
			} else {
				amount = player.getLevel();
			}
			
			player.setLevel(player.getLevel() - amount);
			player.getInventory().addItem(
				new ItemBuilder(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_bottlexp_item_name", ""))
					.withType(Material.EXP_BOTTLE)
					.withLore(new String[]{
							DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", ""),
							" ",
							DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_bottlexp_lore_text", String.valueOf(amount)),
							" ",
							DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", "")
					})
				.build());
			player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_bottlexp_success_msg", String.valueOf(amount), ((amount > 1) ? "s" : "")));
		}else{
			this.helpInfo(sender);
		}
	}

	@Override
	public void helpInfo(CommandSender player) {
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_bottlexp_help", ""));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		return Arrays.asList();
	}

}
