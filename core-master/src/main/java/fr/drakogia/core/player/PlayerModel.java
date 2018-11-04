package fr.drakogia.core.player;

import java.util.Arrays;

import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

public class PlayerModel extends Table{

	public PlayerModel() {
		super("users");
	}

	public boolean existPlayer(String name){
		return this.exist("user_id", Arrays.asList(new Field("user_login", name)));
	}
	
	public void add(String name, String ip){
		Long now = (System.currentTimeMillis()) / 1000;
		
		this.insert(Arrays.asList(
			new Field("user_login", name),
			new Field("user_ip", ip),
			new Field("user_lastconnected", now),
			new Field("registered_at", now)
		));
	}
	
	public void updateLastConnected(String name){
		this.update(Arrays.asList(
			new Field("user_lastconnected", (System.currentTimeMillis()) / 1000)
		), new Field("user_login", name));
	}
	
	public void updateConnectionDuration(String name, Long duration){
		this.update(Arrays.asList(
			new Field("user_duration", duration)
		), new Field("user_login", name));
	}
	
	public Integer getConnectionDuration(String name){
		return (Integer) this.select("user_duration", new Field("user_login", name));
	}
	
	public Integer getId(String name){
		return (Integer) this.select("user_id", new Field("user_login", name));
	}
	
	public String getName(Integer id){
		return (String) this.select("user_login", new Field("user_id", id));
	}
}
