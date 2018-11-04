package fr.drakogia.core.tablist;

import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.drakogia.api.tablist.ITablist;
import lombok.Getter;

@Getter
public class Tablist implements ITablist {

	private Player player;
	private Scoreboard board;
	private Team team;

	private final Pattern pattern = Pattern.compile("(&.)");   
	
	public Tablist(Player player){
		this.player = player;
		this.board = Bukkit.getScoreboardManager().getMainScoreboard();
		this.team = this.board.getTeam(player.getName());
		
		if(this.team == null){
			this.team = this.board.registerNewTeam(player.getName());
			System.out.println("team registered");
		}
		
		this.team.addEntry(
				player.getName()
		);
	}
	
	public void setPlayerTablistCustomName(String prefix) {
		if(prefix.length() < 32){
			if(this.team.getPrefix() == null){
				this.unregister();
			}
			String tP = prefix.replace(" ", "") + "_" + this.player.getName();
			if(tP.length() > 16){
				tP = tP.substring(0, 16);
			}
			Team team = this.board.getTeam(tP);
			if(team == null){
				this.team = this.board.registerNewTeam(tP);
			}
			this.team.addEntry(player.getName());
			String[] prefixes = prefix.split("(?<=\\G.{16})");
			if(prefixes.length > 0){
				this.team.setPrefix(prefixes[0]);
			}else{
				this.team.setPrefix(prefix);
			}
			System.out.println("team prefix setted");
			this.unregisterInactive();
			this.update();
		}else{
			return;
		}

	}

	public void setPlayerTablistCustomName(String prefix, String suffix) {
		if(prefix.length() < 32 && suffix.length() < 32){
			if(this.team.getPrefix() == null || this.team.getSuffix() == null){
				this.unregister();
			}
			this.team.setPrefix(prefix);
			this.team.setSuffix(suffix);
			this.update();
			return;
		}else{
			return;
		}

	}

	public void update() {
		for(Player p : Bukkit.getOnlinePlayers()){
	    	p.setScoreboard(this.board);
	    }
	}

	public void unregister() {
		System.out.println("team unregistered");
		this.team.unregister();
	}

	public void unregisterInactive() {
		for(Team team : this.board.getTeams()){
			if(team.getEntries().size() == 0){
				team.unregister();
			}
		}
	}

	public void unregisterAll() {
		for(Team team : this.board.getTeams()){
			team.unregister();
		}
	}
}
