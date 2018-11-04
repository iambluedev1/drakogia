package fr.drakogia.game.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.drakogia.game.api.crafts.ICraftManager;
import fr.drakogia.game.crafts.CustomCraft;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class CraftManager implements ICraftManager<IRecipe> {

	private Map<String, IRecipe> crafts;
	
	public CraftManager(){
		this.crafts = new HashMap<String, IRecipe>();
	}

	@Override
	public void add(String name, IRecipe craft) {
		this.crafts.put(name, craft);
	}

	@Override
	public void setup() {
		for(Entry<String, IRecipe> craft : this.crafts.entrySet()){
			CraftingManager.field_193380_a.register(CraftingManager.field_193381_c++, new ResourceLocation(craft.getKey()), craft.getValue());
		}
	}
	
}
