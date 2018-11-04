package fr.drakogia.rank;

import org.bukkit.plugin.java.JavaPlugin;

import fr.drakogia.core.command.CommandManager;
import fr.drakogia.rank.command.ManuAddCommand;
import fr.drakogia.rank.command.ReloadCommand;
import fr.drakogia.rank.manager.ListenerManager;
import fr.drakogia.rank.manager.PermissionManager;
import fr.drakogia.rank.manager.RankManager;
import lombok.Getter;

@Getter
public class DrakogiaRank extends JavaPlugin{

	@Getter
	private static DrakogiaRank instance;
	
	private PermissionManager permissionManager;
	private RankManager rankManager;
	
	@Override
	public void onEnable(){
		instance = this;
		
		System.out.println("Load Listeners");
		new ListenerManager().setupManager();
		
		System.out.println("Load Permission Manager");
		this.permissionManager = PermissionManager.getInstance();
		
		System.out.println("Load Rank Manager");
		this.rankManager = RankManager.getInstance();
		
		System.out.println("Init commands");
		CommandManager.getInstance().register(new ReloadCommand());
		CommandManager.getInstance().register(new ManuAddCommand());
	}
	
}
