package fr.drakogia.game.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.drakogia.game.api.items.IItemManager;
import fr.drakogia.game.core.Client;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;

public class ItemManager implements IItemManager{

	private Map<String, Item> items;
	
	public ItemManager(){
		this.items = new HashMap<String, Item>();
	}

	@Override
	public void add(String name, Item block) {
		this.items.put(name, block);
	}

	@Override
	public ArrayList<String> getNames() {
		ArrayList<String> items = new ArrayList<String>();
		for(Entry<String, Item> item : this.items.entrySet()){
			items.add(item.getKey());
		}
		return items;
	}

	@Override
	public Item getItem(String name) {
		if(this.items.containsKey(name)){
			return this.items.get(name);
		}
		
		return null;
	}

	@Override
	public ArrayList<Item> getItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		for(Entry<String, Item> item : this.items.entrySet()){
			items.add(item.getValue());
		}
		return items;
	}

	@Override
	public void setup(RegistryNamespaced<ResourceLocation, Item> registry) {
		for(Entry<String, Item> item : this.items.entrySet()){
			int id = Client.get().getFreeId();
			System.out.println("REGISTER " + id + " " + item.getKey());
			registry.register(id, new ResourceLocation(item.getKey()), item.getValue());
		}
	}

}
