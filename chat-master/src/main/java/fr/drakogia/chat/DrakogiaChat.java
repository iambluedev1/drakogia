package fr.drakogia.chat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.drakogia.chat.commands.ClearChatCommand;
import fr.drakogia.chat.commands.LockChatCommand;
import fr.drakogia.chat.config.ChatConfig;
import fr.drakogia.chat.manager.ListenerManager;
import fr.drakogia.chat.table.Badwords;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.command.CommandManager;
import lombok.Getter;

@Getter
public class DrakogiaChat extends JavaPlugin {

	@Getter
	public static DrakogiaChat instance;

	private boolean chatLocked;
	private ChatConfig chatConfig;

	private Map<UUID, Long> speaks;

	@Override
	public void onEnable() {
		instance = this;

		System.out.println("Load config");
		this.chatConfig = new ChatConfig(DrakogiaApi.getInstance());
		this.chatConfig.setupConfig();

		System.out.println("Load listeners");
		new ListenerManager().setupManager();
		
		System.out.println("Load Badwords");
		Badwords.getInstance().loadBadWords();

		System.out.println("Register Commands");
		CommandManager.getInstance().register(new LockChatCommand());
		CommandManager.getInstance().register(new ClearChatCommand());

		System.out.println("Sarting Spam Protection");
		this.speaks = new HashMap<UUID, Long>();
	}

	public void toggleChat() {
		this.chatLocked = !this.chatLocked;
	}

	public boolean canSpeak(Player player) {
		if (!this.speaks.containsKey(player.getUniqueId())) return true;

		long time = this.speaks.get(player.getUniqueId());
		long waitTime = this.chatConfig.getFileConfig().getInt("chat.spam.waitTime") * 1000;

		if (time == -1) return true;
		if (waitTime == 0) return true;

		return (time + waitTime) < System.currentTimeMillis();
	}

	public void hadSpeak(Player player) {
		if (!this.speaks.containsKey(player.getUniqueId())) {
			this.speaks.put(player.getUniqueId(), System.currentTimeMillis());
		} else {
			this.speaks.replace(player.getUniqueId(), System.currentTimeMillis());
		}
	}

}
