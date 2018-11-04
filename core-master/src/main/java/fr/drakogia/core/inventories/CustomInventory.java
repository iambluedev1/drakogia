package fr.drakogia.core.inventories;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.drakogia.api.inventories.AbstractInventory;
import fr.drakogia.core.DrakogiaApi;

public abstract class CustomInventory extends AbstractInventory {

	protected List<Object> objects;
	
	public void open(final Player player){
		player.openInventory(this.contents(player));
		if(isDynamic()){
			if(player.getOpenInventory() != null){
				BukkitRunnable tasks = new BukkitRunnable(){
					public void run() {
						if(!player.isOnline()) cancel();
						
						if(player.getOpenInventory().getTitle().equals(name())){
							dynamicContent(player);
						}else{
							cancel();
						}
					}
				};
				tasks.runTaskTimer(DrakogiaApi.getInstance(), 0, 25);
			}
		}
	}
	
	public void open(Player player, Object... params){
		this.objects = Arrays.asList(params);
		this.open(player);
	}
}
