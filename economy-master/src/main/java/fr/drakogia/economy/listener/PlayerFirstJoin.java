package fr.drakogia.economy.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.drakogia.api.events.PlayerFirstJoinEvent;
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.economy.DrakogiaEconomy;
import xyz.anana.pine.core.field.Field;

public class PlayerFirstJoin implements Listener {

	@EventHandler
	public void onPlayerFirstJoin(PlayerFirstJoinEvent event){
		DrakogiaPlayer player = PlayerManager.getInstance().get(event.getPlayer());
		
		if(player == null){
			 event.getPlayer().kickPlayer("Error when connecting...");
			 return;
		}
		
		System.out.println("First join");
		player.addData("money", DrakogiaEconomy.getInstance().getDrakogiaConfig().getFileConfig().getInt("starter.amount"));
		PlayerManager.getInstance().getModel().update(new Field("user_money", player.getData("money")), new Field("user_login", player.getName()));
	}
	
}
