package fr.drakogia.api.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlConfig implements IConfig {

	private String fileName;
	private File file;
	private FileConfiguration fileConfig;
	private boolean created;
	
	public YamlConfig(String fileName, File folder){
		this.setFileName(fileName);
		this.setFile(new File(folder + "/" + this.getFileName() + ".yml"));
		if(!folder.exists()){
   		 	folder.mkdir();
   	 	}
		if(!this.getFile().exists()){
       	 	this.setCreated(false);
   	 		try {
   	 			this.getFile().createNewFile();
   	 		} catch (IOException e) {
   	 			e.printStackTrace();
   	 		}
        }else{
        	this.setCreated(true);
        }
		this.setFileConfig(YamlConfiguration.loadConfiguration(this.getFile()));
	}

	public boolean save() {
		try {
   		 	this.getFileConfig().save(this.getFile());
   		 	return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
	}
}
