package fr.drakogia.shop.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.drakogia.shop.inventory.InventoryPage;
import fr.drakogia.shop.inventory.InventoryType;
import fr.drakogia.shop.inventory.ItemRegistry;
import lombok.Getter;

@Getter
public class InventoryManager {

	private static InventoryManager instance;
	private Map<InventoryType, List<InventoryPage>> pages;
	
	private InventoryManager(){
		this.pages = new HashMap<InventoryType, List<InventoryPage>>();
	}
	
	public static InventoryManager getInstance(){
		if(instance == null){
			instance = new InventoryManager();
		}
		
		return instance;
	}
	
	public InventoryPage getPage(InventoryType type, Integer page){
		if(this.pages.get(type).size() >= page){
			return this.pages.get(type).get(page - 1);
		}
		
		return null;
	}
	
	public List<InventoryPage> getPages(InventoryType type){
		return this.pages.get(type);
	}
	
	public Integer countPages(InventoryType type){
		return (this.pages.get(type).size() == 0) ? 1 : this.pages.get(type).size();
	}
	
	public void addPage(InventoryType type, InventoryPage page){
		this.pages.get(type).add(page);
	}
	
	public void format(InventoryType type, List<ItemRegistry> items){
		List<List<ItemRegistry>> map = this.chopped(items, 9 * 5 + 1);
		int amount = 0;
		
		for(List<ItemRegistry> a : map){
			if(!this.pages.containsKey(type)){
				this.pages.put(type, new ArrayList<InventoryPage>());
			}
			
			this.pages.get(type).add(new InventoryPage(a));
			amount++;
		}
		
		System.out.println("Added " + amount + " pages for the type " + type.toString());
	}
	
	private <T> List<List<T>> chopped(List<T> list, final int L) {
	    List<List<T>> parts = new ArrayList<List<T>>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<T>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	}
	
	public void clear(){
		this.pages = new HashMap<InventoryType, List<InventoryPage>>();
	}
}
