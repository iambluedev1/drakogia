package fr.drakogia.shop;

import java.util.List;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import fr.drakogia.core.command.CommandManager;
import fr.drakogia.core.inventories.CustomInventoryManager;
import fr.drakogia.shop.command.FarmCommand;
import fr.drakogia.shop.command.ReloadCommand;
import fr.drakogia.shop.command.ShopCommand;
import fr.drakogia.shop.command.TrashCommand;
import fr.drakogia.shop.inventory.InventoryPage;
import fr.drakogia.shop.inventory.InventoryType;
import fr.drakogia.shop.inventory.ItemRegistry;
import fr.drakogia.shop.inventory.gui.BuyInventory;
import fr.drakogia.shop.inventory.gui.MainInventory;
import fr.drakogia.shop.inventory.gui.SellInventory;
import fr.drakogia.shop.inventory.gui.TrashInventory;
import fr.drakogia.shop.manager.InventoryManager;
import fr.drakogia.shop.manager.ListenerManager;
import fr.drakogia.shop.table.Items;
import lombok.Getter;

@Getter
public class DrakogiaShop extends JavaPlugin{

	private Map<Integer, InventoryPage> pages;
	
	@Getter
	private static DrakogiaShop instance;
	
	
	@Override
	public void onEnable(){
		instance = this;
		
		System.out.println("Load Listeners");
		new ListenerManager().setupManager();
		
		System.out.println("Load Commands");
		CommandManager.getInstance().register(new ShopCommand());
		CommandManager.getInstance().register(new FarmCommand());
		CommandManager.getInstance().register(new TrashCommand());
		CommandManager.getInstance().register(new ReloadCommand());
		
		System.out.println("Register Inventories");
		CustomInventoryManager.getInstance().addMenu(new MainInventory());
		CustomInventoryManager.getInstance().addMenu(new BuyInventory());
		CustomInventoryManager.getInstance().addMenu(new SellInventory());
		CustomInventoryManager.getInstance().addMenu(new TrashInventory());
		
		System.out.println("Setup Shop's Cache");
		Map<InventoryType, List<ItemRegistry>> items = Items.getInstance().getItems();
		InventoryManager.getInstance().format(InventoryType.BUY, items.get(InventoryType.BUY));
		InventoryManager.getInstance().format(InventoryType.SELL, items.get(InventoryType.SELL));
	}
	
	@Override
	public void onDisable(){
		
	}
	
}
