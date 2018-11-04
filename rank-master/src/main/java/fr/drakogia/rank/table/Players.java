package fr.drakogia.rank.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.drakogia.rank.manager.RankManager;
import fr.drakogia.rank.object.Player;
import fr.drakogia.rank.object.Rank;
import fr.iambluedev.pine.api.field.IField;
import fr.iambluedev.pine.api.field.IFields;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

public class Players extends Table {

	private static Players instance;
	
	public Players() {
		super("users");
	}
	
	public static Players getInstance() {
		if (instance == null) {
			instance = new Players();
		}

		return instance;
	}

	public Rank getRank(String user_login){
		Integer rank_id = (Integer) this.select("rank_id", new Field("user_login", user_login));
		return RankManager.getInstance().get(rank_id);
	}
	
	public void setRank(String user_login, Integer id){
		this.update(Arrays.asList(
			new Field("rank_id", id),
			new Field("rank_end", "-1")
		), new Field("user_login", user_login));
		System.out.println("[RANK] " + user_login + " added to the group " + id);
	}
	
	public void setRank(String user_login, Integer id, Long end){
		this.update(Arrays.asList(
			new Field("rank_id", id),
			new Field("rank_end", end)
		), new Field("user_login", user_login));
	}
	
	public List<Player> getPlayers(){
		List<Player> players = new ArrayList<Player>();
		for (IFields tmp1 : this.selectAll()) {
			List<IField> fields = tmp1.getFields();
			
			String name = "";
			Integer end = -1;
			Integer id = -1;
			
			for (IField tmp2 : fields) {
				Field field = (Field) tmp2;
				if (field.getName().equals("user_login")) {
					name = (String) field.getValue();
				} else if(field.getName().equals("rank_end")) {
					end = (Integer) field.getValue();
				} else if(field.getName().equals("user_id")) {
					id = (Integer) field.getValue();
				}
			}
			
			players.add(new Player(id, name, end));
		}
		return players;
	}
}
