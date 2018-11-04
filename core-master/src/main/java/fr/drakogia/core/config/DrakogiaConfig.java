package fr.drakogia.core.config;

import fr.drakogia.api.config.YamlConfig;
import fr.drakogia.core.DrakogiaApi;

public class DrakogiaConfig extends YamlConfig {

	public DrakogiaConfig(DrakogiaApi api) {
		super("mysql", api.getDataFolder());
	}

	public void setupConfig() {
		if(!this.isCreated()){
			this.getFileConfig().set("mysql.type", "mariadb");
			this.getFileConfig().set("mysql.port", 3306);
			this.getFileConfig().set("mysql.dbname", "drakogia");
			this.getFileConfig().set("mysql.username", "root");
			this.getFileConfig().set("mysql.password", "");
			this.getFileConfig().set("mysql.host", "localhost");
			
			this.getFileConfig().set("redis.port", 6379);
			this.getFileConfig().set("redis.host", "localhost");
			this.getFileConfig().set("redis.password", "");
			
			this.getFileConfig().set("env", "dev");
			this.save();
		}
	}

}
