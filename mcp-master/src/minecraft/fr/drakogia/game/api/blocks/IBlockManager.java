package fr.drakogia.game.api.blocks;

import java.util.ArrayList;

import fr.drakogia.game.api.manager.IElementManager;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;

public interface IBlockManager extends IElementManager<Block> {

	Block getBlock(String name);
	
	void setup(RegistryNamespacedDefaultedByKey<ResourceLocation, Block> registry);
	
	ArrayList<Block> getBlocks();
}
