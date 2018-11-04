package fr.drakogia.gifts.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.drakogia.api.events.PlayerPostJoinEvent;
import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.gifts.manager.GiftsManager;

public class PlayerPostJoin implements Listener {

	@EventHandler
	public void onPlayerPostJoin(PlayerPostJoinEvent event){
		Integer count = GiftsManager.getInstance().getGifts(event.getPlayer()).size();
		
		if (count > 0) {
            event.getPlayer().sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_queue", String.valueOf(count), (count > 1 ) ? "s" : ""));
        }
	}
	
}
