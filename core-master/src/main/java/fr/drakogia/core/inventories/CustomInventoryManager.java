package fr.drakogia.core.inventories;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import fr.drakogia.api.inventories.IInventoryManager;
import fr.drakogia.core.DrakogiaApi;

public class CustomInventoryManager implements IInventoryManager<CustomInventory> {

	public Map<Integer, CustomInventory> registerInventorys;
	private Map<UUID, Integer> openedInv;
	private Random rand;

	private static CustomInventoryManager instance;
	
	private CustomInventoryManager() {
		this.registerInventorys = new HashMap<Integer, CustomInventory>();
		this.openedInv = new HashMap<UUID, Integer>();

		this.rand = new Random();
		instance = this;
	}
	
	public static CustomInventoryManager getInstance(){
		if(instance == null){
			instance = new CustomInventoryManager();
			Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(), DrakogiaApi.getInstance());
		}
		
		return instance;
	}

	public Integer addMenu(CustomInventory inv) {
		int id = this.getId();
		this.registerInventorys.put(id, inv);
		return id;
	}

	public void open(Integer id, Player player) {
		if(!this.registerInventorys.containsKey(id)) return;
		CustomInventory inv = this.registerInventorys.get(id);
		inv.open(player);
		this.openedInv.put(player.getUniqueId(), id);
	}
	
	public void open(Integer id, Player player, Object... params) {
		if(!this.registerInventorys.containsKey(id)) return;
		CustomInventory inv = this.registerInventorys.get(id);
		inv.open(player, params);
		this.openedInv.put(player.getUniqueId(), id);
	}
	
	public void open(CustomInventory inv, Player player){
		for(Entry<Integer, CustomInventory> tmp : this.registerInventorys.entrySet()){
			if(tmp.getValue().name().equals(inv.name())){
				this.open(tmp.getKey(), player);
				return;
			}
		}
		
		System.out.println("Can't Open Inventory " + inv.name());
	}
	
	public void open(CustomInventory inv, Player player, Object... params){
		for(Entry<Integer, CustomInventory> tmp : this.registerInventorys.entrySet()){
			if(tmp.getValue().name().equals(inv.name())){
				this.open(tmp.getKey(), player, params);
				return;
			}
		}
		
		System.out.println("Can't Open Inventory " + inv.name());
	}

	public Integer getInventoryID(UUID uuid) {
		return this.openedInv.getOrDefault(uuid, -1);
	}

	public CustomInventory getInventory(Integer id) {
		return this.registerInventorys.getOrDefault(id, null);
	}

	public void closeInventory(int id, InventoryCloseEvent event) {
		CustomInventory inv = this.getInventory(id);
		if (inv == null) return;

		Player player = (Player) event.getPlayer();
		this.openedInv.remove(player.getUniqueId());
		inv.onClose(player, event.getInventory(), event);
	}
	
	public Integer getId() {
		int id;
		do {
			id = this.rand.nextInt(10000);
		} while (this.registerInventorys.containsKey(id));
		return id;
	}

}
