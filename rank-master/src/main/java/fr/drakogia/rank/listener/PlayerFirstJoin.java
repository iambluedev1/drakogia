package fr.drakogia.rank.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.drakogia.api.events.PlayerFirstJoinEvent;
import fr.drakogia.rank.DrakogiaRank;
import fr.drakogia.rank.object.Rank;
import fr.drakogia.rank.table.Players;

public class PlayerFirstJoin implements Listener {

	@EventHandler
	public void onPlayerFirstJoin(PlayerFirstJoinEvent event){
		Rank defaultRank = DrakogiaRank.getInstance().getRankManager().getDefault();
		Players.getInstance().setRank(event.getPlayer().getName(), defaultRank.getId());
	}
	
}
