package fr.drakogia.rank.command;

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
import fr.drakogia.rank.manager.RankManager;
import fr.drakogia.rank.object.Permission;
import fr.drakogia.rank.object.Rank;
import fr.drakogia.rank.table.Players;

public class ManuAddCommand extends AbstractCommand {

	public ManuAddCommand() {
		super("rankset", Arrays.asList("rankset"), "Set Player's Rank");
		this.permission = "drakogia.rank.set";
	}

	/**
	 * @author rovergames
	 */
	@Override
	public void handle(CommandSender sender, List<String> args) {
		if(sender instanceof Player && !sender.hasPermission("drakogia.rank.set")) {
			sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_allowed", ""));
			return;
		}
		
		Player player = (Player) sender;
		
		if (args.size() < 2 || args.size() > 3){
			this.helpInfo(player);
			return;
		}
		
        Player toPlayer = Bukkit.getPlayerExact(args.get(0));
        String rankName = args.get(1);
        long duration = -1;
        
        if (toPlayer == null) {
            sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_not_online", ""));
            return;
        }
        
        if (args.size() == 3) {
            try {
                duration = Long.parseLong(args.get(2));
            } catch (NumberFormatException e) {
            	this.helpInfo((Player) sender);
            	return;
            }
        }
        
        Rank rank = RankManager.getInstance().get(rankName);
        DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(toPlayer);
        
        if(rank != null){
        	if(duration == -1){
        		Players.getInstance().setRank(toPlayer.getName(), rank.getId());
        	}else{
        		Players.getInstance().setRank(toPlayer.getName(), rank.getId(), (System.currentTimeMillis() / 1000) + (duration * 24 * 60 * 60));
        	}
        	drakogiaPlayer.updateData("rank", rank);
        	drakogiaPlayer.getTablist().setPlayerTablistCustomName(rank.getTablist() + " ");
        	drakogiaPlayer.clearPerms();
    		for(Permission permission : rank.getPermissions()){
    			drakogiaPlayer.addPerm(permission.getPermission());
    		}
    		sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_rank_set_success", new String[]{ drakogiaPlayer.getName(), rank.getPrefix() }));
    		toPlayer.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_rank_setted_success", new String[]{ rank.getPrefix() }));
    		System.out.println("[RANK] " + drakogiaPlayer.getName() + " put into the rank " + rank.getName());
        }else{
        	sender.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_rank_invalid_name", ""));
        }
	}

	@Override
	public void helpInfo(CommandSender player) {
		player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_rank_help_set", ""));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, List<String> args) {
		if (args.size() == 2) {
			List<String> ranks = new ArrayList<String>();
			for(Rank rank : RankManager.getInstance().getRanks()){
				ranks.add(rank.getName());
			}
			return ranks;
		}
		return null;
	}

}
