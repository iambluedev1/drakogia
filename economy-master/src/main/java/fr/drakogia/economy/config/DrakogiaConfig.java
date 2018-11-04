package fr.drakogia.economy.config;

import fr.drakogia.api.config.YamlConfig;
import fr.drakogia.core.DrakogiaApi;

public class DrakogiaConfig extends YamlConfig {

	public DrakogiaConfig(DrakogiaApi api) {
		super("economy", api.getDataFolder());
	}
	
	public void setupConfig() {
		if(!this.isCreated()){
			this.getFileConfig().set("starter.amount", 1000);
			this.getFileConfig().set("baltop.perPage", 10);
			this.getFileConfig().set("transaction.alert", 100000);
			this.save();
		}
	}
}
