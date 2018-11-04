package fr.drakogia.gifts;

import org.bukkit.plugin.java.JavaPlugin;

import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.command.CommandManager;
import fr.drakogia.core.inventories.CustomInventoryManager;
import fr.drakogia.gifts.command.IgnoreCommand;
import fr.drakogia.gifts.command.SendCommand;
import fr.drakogia.gifts.command.ViewCommand;
import fr.drakogia.gifts.config.DrakogiaConfig;
import fr.drakogia.gifts.inventory.BoxInventory;
import fr.drakogia.gifts.inventory.MoneyInventory;
import fr.drakogia.gifts.inventory.SendInventory;
import fr.drakogia.gifts.inventory.ViewInventory;
import fr.drakogia.gifts.manager.GiftsManager;
import fr.drakogia.gifts.manager.ListenerManager;
import lombok.Getter;

/*
 * Plugin adapted from rovergames's plugin
 */

@Getter
public class DrakogiaGifts extends JavaPlugin {

	@Getter
	private static DrakogiaGifts instance;
	
	private DrakogiaConfig drakogiaConfig;
	
	@Override
	public void onEnable() {
		instance = this;
		
		System.out.println("Load Configs");
		this.drakogiaConfig = new DrakogiaConfig(DrakogiaApi.getInstance());
		this.drakogiaConfig.setupConfig();
		
		System.out.println("Setup Listeners");
		new ListenerManager().setupManager();
		
		System.out.println("Load Commands");
		CommandManager.getInstance().register(new IgnoreCommand());
		CommandManager.getInstance().register(new ViewCommand());
		CommandManager.getInstance().register(new SendCommand());
		
		System.out.println("Load Gifts Manager");
		GiftsManager.getInstance().load();
		
		System.out.println("Load Inventories");
		CustomInventoryManager.getInstance().addMenu(new BoxInventory());
		CustomInventoryManager.getInstance().addMenu(new SendInventory());
		CustomInventoryManager.getInstance().addMenu(new MoneyInventory());
		CustomInventoryManager.getInstance().addMenu(new ViewInventory());
	}
	
	@Override
	public void onDisable() {}
}
