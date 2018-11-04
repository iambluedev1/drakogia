package fr.drakogia.core.inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import fr.drakogia.api.inventories.IInventoryBuilder;
import lombok.Getter;

@Getter
public class InventoryBuilder implements IInventoryBuilder<InventoryBuilder> {

	private String name;
	private List<String[]> lines;
	private Map<String, ItemStack> items;

	public InventoryBuilder(String name){
		this.name = name;
		this.lines = new ArrayList<String[]>();
		this.items =  new HashMap<String, ItemStack>();
	}

	public InventoryBuilder addLine(String[] line) {
		this.lines.add(line);
		return this;
	}

	public InventoryBuilder addItem(String code, ItemStack itemStack) {
		this.items.put(code, itemStack);
		return this;
	}

	public Inventory build() {
		int size = this.lines.size() * 9;
		int id = 0;
		
		Inventory inv = Bukkit.createInventory(null, size, this.name);
		
		for(String[] line : this.lines){
			for(String l : line){
				inv.setItem(id, this.items.get(l));
				id++;
			}
		}
		return inv;
	}
	
	public Inventory build(Player player) {
		int id = 0;
		
		for(String[] line : this.lines){
			for(String l : line){
				player.getInventory().setItem(id, this.items.get(l));
				id++;
			}
		}

		return player.getInventory();
	}

	public Inventory buildWithPlayerInv(Player player) {
		int size = this.lines.size() * 9;
		int id = 0;
		int idP = 9;
		
		Inventory inv = Bukkit.createInventory(null, size, this.name);
		
		for(String[] line : this.lines){
			for(String l : line){
				if(l.equals("INV_PLAYER")){
					inv.setItem(id, player.getInventory().getItem(idP));
					if(idP == 35){
						idP = 0;
					}else{
						idP++;
					}
				}else{
					inv.setItem(id, this.items.get(l));
				}
				id++;
			}
		}
		return inv;
	}

	public InventoryView updateInventoryWithPlayerInv(Player player, Player target) {
		int id = 0;
		int idP = 9;
		
		InventoryView inv = player.getOpenInventory();
		for(String[] line : this.lines){
			for(String l : line){
				if(l.equals("INV_PLAYER")){
					inv.setItem(id, target.getInventory().getItem(idP));
					if(idP == 35){
						idP = 0;
					}else{
						idP++;
					}
				}else{
					inv.setItem(id, this.items.get(l));
				}
				id++;
			}
		}
		
		player.updateInventory();
		
		return inv;
	}

	public InventoryView updateInventoryWithPlayerInv(Player player, Player target, Boolean enderchest) {
		int id = 0;
		int idP = 0;
		
		InventoryView inv = player.getOpenInventory();
		for(String[] line : this.lines){
			for(String l : line){
				if(l.equals("INV_PLAYER")){
					if(enderchest){
						inv.setItem(id, target.getEnderChest().getItem(idP));
						idP++;
					}else{
						inv.setItem(id, target.getInventory().getItem(idP));
						idP++;
					}
				}else{
					inv.setItem(id, this.items.get(l));
				}
				id++;
			}
		}
		
		player.updateInventory();
		
		return inv;
	}

}
