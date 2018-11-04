package fr.drakogia.economy.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.drakogia.api.events.PlayerPostJoinEvent;
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import xyz.anana.pine.core.field.Field;

public class PlayerPostJoin implements Listener{

	@EventHandler
	public void onPlayerPostJoin(PlayerPostJoinEvent event){
		DrakogiaPlayer player = PlayerManager.getInstance().get(event.getPlayer());
		
		if(player == null){
			 event.getPlayer().kickPlayer("Error when connecting...");
			 return;
		}
		
		Integer money = (Integer) PlayerManager.getInstance().getModel().select("user_money", new Field("user_login", player.getName()));
		money = (money < 0) ? 0 : money;
		
		player.addData("money", money);
	}
	
}
