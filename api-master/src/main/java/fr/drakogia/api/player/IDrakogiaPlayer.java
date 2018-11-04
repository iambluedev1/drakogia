package fr.drakogia.api.player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public interface IDrakogiaPlayer {

	Integer getId();
	
	String getName();
	
	Player getPlayer();
	
	UUID getUuid();
	
	void clearPerms();
	
	boolean addPerm(String perm);
	
	boolean addPerms(List<String> perms);
	
	void addData(String key, Object value);
	
	void updateData(String key, Object value);
	
	void setId(Integer id);
	
	Object getData(String key);
	
	Map<String, Object> getDatas();
}
