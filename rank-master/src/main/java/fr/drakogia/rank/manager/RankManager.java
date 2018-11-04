package fr.drakogia.rank.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.drakogia.api.ranks.IRankManager;
import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.rank.DrakogiaRank;
import fr.drakogia.rank.object.Permission;
import fr.drakogia.rank.object.Player;
import fr.drakogia.rank.object.Rank;
import fr.drakogia.rank.table.Players;
import fr.drakogia.rank.table.Ranks;

public class RankManager implements IRankManager<Rank> {

	private static RankManager instance;
	private List<Rank> ranks;
	private Runnable task;

	private RankManager() {
		this.ranks = new ArrayList<Rank>();
		this.task = new Runnable() {
			@Override
			public void run() {
				List<Player> players = Players.getInstance().getPlayers();
				Rank defaultRank = RankManager.getInstance().getDefault();
				for(Player player : players) {
					Integer end = player.getEnd();
					if (end != -1 && end < (System.currentTimeMillis() / 1000)) {
						Players.getInstance().setRank(player.getName(), defaultRank.getId());
						DrakogiaPlayer p = PlayerManager.getInstance().get(player.getName());
						if(p != null){
							p.updateData("rank", defaultRank);
							p.clearPerms();
							for(Permission permission : defaultRank.getPermissions()){
								p.addPerm(permission.getPermission());
							}
							System.out.println("[RANK] " + player.getName() + " put into the rank " + defaultRank.getName());
						}
					}
				}
			}
		};
		Bukkit.getScheduler().runTaskTimerAsynchronously(DrakogiaRank.getInstance(), this.task, 60 * 20, 60 * 20);
	}

	public static RankManager getInstance() {
		if (instance == null) {
			instance = new RankManager();
			instance.load();
		}

		return instance;
	}

	@Override
	public List<Rank> getRanks() {
		return this.ranks;
	}

	@Override
	public Rank get(String name) {
		for (Rank rank : this.ranks) {
			if (rank.getName().equals(name)) {
				return rank;
			}
		}

		return null;
	}
	
	@Override
	public Rank get(Integer id) {
		for (Rank rank : this.ranks) {
			if (rank.getId() == id) {
				return rank;
			}
		}

		return null;
	}

	@Override
	public void load() {
		this.ranks = Ranks.getInstance().getRanks();
		for(Rank rank : this.ranks){
			System.out.println("Loaded rank : " + rank.getName());
		}
	}

	@Override
	public void update(Rank rank){
		Ranks.getInstance().updateRank(rank);
	}
	
	@Override
	public Rank getDefault() {
		for (Rank rank : this.ranks) {
			if (rank.isDefault()) {
				return rank;
			}
		}
		return null;
	}

	@Override
	public boolean remove(String name) {
		if (this.get(name) != null) {
			Ranks.getInstance().removeRank(name);
			return true;
		}
		return false;
	}

	@Override
	public boolean add(String name) {
		if (this.get(name) == null) {
			Integer id = Ranks.getInstance().addRank(name, "", "", false);
			List<Permission> permissions = new ArrayList<Permission>();
			Rank rank = new Rank(id, name, "", "", false, permissions);
			this.ranks.add(rank);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void save() {
		for(Rank rank : this.ranks){
			Ranks.getInstance().updateRank(rank);
		}
	}

}
