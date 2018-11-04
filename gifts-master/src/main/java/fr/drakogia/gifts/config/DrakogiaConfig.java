package fr.drakogia.gifts.config;

import fr.drakogia.api.config.YamlConfig;
import fr.drakogia.core.DrakogiaApi;

public class DrakogiaConfig extends YamlConfig {

	public DrakogiaConfig(DrakogiaApi api) {
		super("gifts", api.getDataFolder());
	}
	
	public void setupConfig() {
		if(!this.isCreated()){
			this.getFileConfig().set("don.cooldown", 300);
			this.getFileConfig().set("don.auto.delete", 14);
			this.save();
		}
	}
}