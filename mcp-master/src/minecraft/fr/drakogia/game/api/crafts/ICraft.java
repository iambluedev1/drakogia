package fr.drakogia.game.api.crafts;

import fr.drakogia.game.crafts.CustomCraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public interface ICraft<T> {

	ICraft<T> addLine(String[] line);
	
	ICraft<T> addItem(String code, ItemStack itemStack);
	
	ICraft<T> setOutput(ItemStack output);
	
	IRecipe build();
	
}
