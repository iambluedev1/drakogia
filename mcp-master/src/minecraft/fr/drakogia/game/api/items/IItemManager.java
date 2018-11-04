package fr.drakogia.game.api.items;

import java.util.ArrayList;

import fr.drakogia.game.api.manager.IElementManager;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;

public interface IItemManager extends IElementManager<Item> {

	Item getItem(String name);
	
	ArrayList<Item> getItems();
	
	void setup(RegistryNamespaced<ResourceLocation, Item> registry);
}
