package fr.drakogia.core.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import fr.drakogia.api.player.IDrakogiaPlayer;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.tablist.Tablist;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DrakogiaPlayer implements IDrakogiaPlayer {

	@Setter
	private Integer id;
	
	private Player player;
	private UUID uuid;
	private String name;
	private Tablist tablist;
	private PermissionAttachment attachment;
	private Map<String, Object> datas;
	
	public DrakogiaPlayer(Player player){
		this.player = player;
		this.uuid = player.getUniqueId();
		this.name = player.getName();
		this.tablist = new Tablist(player);
		this.attachment =  player.addAttachment(DrakogiaApi.getInstance());
		this.datas = new HashMap<String, Object>();
	}

	@Override
	public void clearPerms() {
		if (this.attachment != null) {
			for(Entry<String, Boolean> entry : this.attachment.getPermissions().entrySet()){
				this.attachment.unsetPermission(entry.getKey());
			}
		}
	}

	@Override
	public boolean addPerm(String perm) {
		this.attachment.setPermission(perm, true);
		return true;
	}

	@Override
	public boolean addPerms(List<String> perms) {
		for(String perm : perms){
			this.addPerm(perm);
		}
		
		return true;
	}

	@Override
	public void addData(String key, Object value) {
		if(!this.datas.containsKey(key)){
			this.datas.put(key, value);
		}
	}
	
	@Override
	public void updateData(String key, Object value) {
		if(this.datas.containsKey(key)){
			this.datas.replace(key, value);
		}
	}

	@Override
	public Object getData(String key) {
		if(this.datas.containsKey(key)){
			return this.datas.get(key);
		}
		
		return null;
	}
}
