package fr.drakogia.game.api.client;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;

public interface IClient {

	void onLoad();
	
	void onEnable();
	
	void onDisable();
	
	void run();
	
	void onBlockRegister(RegistryNamespacedDefaultedByKey<ResourceLocation, Block> instance);
	
	void onItemRegister(RegistryNamespaced<ResourceLocation, Item> instance);
	
	void onCraftsInit();
	
	void onFurnaceInit();
	
	int getFreeId();
	
	void setLastUsedId(int id);
}
