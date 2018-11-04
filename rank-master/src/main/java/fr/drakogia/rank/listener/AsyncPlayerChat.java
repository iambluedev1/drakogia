package fr.drakogia.rank.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.rank.object.Rank;

public class AsyncPlayerChat implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		DrakogiaPlayer player = PlayerManager.getInstance().get(event.getPlayer());
		
		if(player == null){
			event.setCancelled(true);
		}
		
		Rank rank = (Rank) player.getData("rank");
		
		event.setFormat(rank.getPrefix() + " " + player.getName() + "§r >> " + event.getMessage());
	}
}
