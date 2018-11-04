package fr.drakogia.chat.config;

import fr.drakogia.api.config.YamlConfig;
import fr.drakogia.core.DrakogiaApi;

public class ChatConfig extends YamlConfig {

	public ChatConfig(DrakogiaApi api) {
		super("chat", api.getDataFolder());
	}

	public void setupConfig() {
		if (!this.isCreated()) {
			this.getFileConfig().set("chat.spam.waitTime", 3);
			this.getFileConfig().set("mentions.actionbar.enable", Boolean.valueOf(true));
			this.getFileConfig().set("mentions.sound.enable", Boolean.valueOf(true));
			this.getFileConfig().set("mentions.sound.type", "BLOCK_NOTE_PLING");
			this.getFileConfig().set("mentions.hightlight.enable", Boolean.valueOf(true));
			this.save();
		}
	}
}
