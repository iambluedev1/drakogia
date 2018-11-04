package fr.drakogia.rank.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.rank.object.Permission;
import fr.drakogia.rank.object.Rank;
import fr.drakogia.rank.table.Players;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		DrakogiaPlayer player = PlayerManager.getInstance().get(event.getPlayer());
		
		if(player == null){
			 event.getPlayer().kickPlayer("Error when connecting...");
			 return;
		}
		
		player.clearPerms();
		Rank rank = Players.getInstance().getRank(player.getName());
		player.addData("rank", rank);
		player.getTablist().setPlayerTablistCustomName(rank.getTablist() + " ");
		for(Permission permission : rank.getPermissions()){
			player.addPerm(permission.getPermission());
		}
		System.out.println("[RANK] " + player.getName() + " put into the rank " + rank.getName());
	}
	
}
