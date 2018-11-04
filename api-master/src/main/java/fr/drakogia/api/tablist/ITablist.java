package fr.drakogia.api.tablist;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public interface ITablist {

	void setPlayerTablistCustomName(String prefix);
	
	void setPlayerTablistCustomName(String prefix, String suffix);
	
	void update();
	
	void unregister();
	
	void unregisterInactive();
	
	void unregisterAll();
	
	Team getTeam();
	
	Scoreboard getBoard();
	
	Player getPlayer();
}
