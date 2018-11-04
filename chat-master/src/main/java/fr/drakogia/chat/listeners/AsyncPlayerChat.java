package fr.drakogia.chat.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.drakogia.api.i18n.Lang;
import fr.drakogia.chat.DrakogiaChat;
import fr.drakogia.chat.table.Badwords;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.core.utils.ActionBar;
import fr.drakogia.rank.object.Rank;

public class AsyncPlayerChat implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (!DrakogiaChat.getInstance().isChatLocked()
				|| (DrakogiaChat.getInstance().isChatLocked() && player.hasPermission("drakogia.chat.bypass"))) {
			if (DrakogiaChat.getInstance().canSpeak(player) || player.hasPermission("drakogia.chat.spam.bypass")) {
				boolean usingBadWord = false;
				List<String> words = Arrays.asList(event.getMessage().split(" "));

				for (String word : words) {
					if (Badwords.getInstance().isBadWord(word)) {
						words.set(words.indexOf(word), "****");
						usingBadWord = true;
					}
				}

				String msg = "";
				for (String word : words) {
					msg += word + " ";
				}

				msg = msg.substring(0, msg.length() - 1);
				msg = msg.substring(0, 1).toUpperCase() + msg.substring(1);
				event.setMessage(msg);

				if (usingBadWord)
					player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_chat_badwords", ""));

			} else {
				player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_chat_spam", ""));
				event.setCancelled(true);
			}
		} else {
			player.sendMessage(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_chat_can_not_speak", ""));
			event.setCancelled(true);
		}

		if (!event.isCancelled()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!(p.getName().equals(player.getName())) && (event.getMessage().contains(p.getName()))){
					
					if (DrakogiaChat.getInstance().getChatConfig().getFileConfig().getBoolean("mentions.hightlight.enable")) {
						
						DrakogiaPlayer drakogiaPlayer = PlayerManager.getInstance().get(event.getPlayer());
						
						if(drakogiaPlayer == null){
							return;
						}
						
						Rank rank = (Rank) drakogiaPlayer.getData("rank");
						
						p.sendMessage(rank.getPrefix() + " " + player.getName() + "§r >> " + event.getMessage().replace(p.getName(), DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia.chat.mentions.mentioned.format", p.getName())));		
						event.getRecipients().remove(p);
					}

					if (DrakogiaChat.getInstance().getChatConfig().getFileConfig() .getBoolean("mentions.actionbar.enable")) 
						new ActionBar(DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia.chat.mentions.mentioned.alert", event.getPlayer().getName())).sendActionBar(p);
					
					if (DrakogiaChat.getInstance().getChatConfig().getFileConfig().getBoolean("mentions.sound.enable"))
						p.playSound(p.getLocation(), Sound.valueOf(DrakogiaChat.getInstance().getChatConfig() .getFileConfig().getString("mentions.sound.type")), 10.0F, 1.0F);
					
				}
			}
		}
	}
}
