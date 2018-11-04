package fr.drakogia.game.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import fr.drakogia.game.api.blocks.IBlockManager;
import fr.drakogia.game.core.Client;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;

public class BlockManager implements IBlockManager {

	private Map<String, Block> blocks;
	
	public BlockManager(){
		this.blocks = new HashMap<String, Block>();
	}
	
	@Override
	public void add(String name, Block block){
		this.blocks.put(name, block);
	}
	
	@Override
	public Block getBlock(String name){
		if(this.blocks.containsKey(name)){
			return this.blocks.get(name);
		}
		
		return null;
	}
	
	@Override
	public void setup(RegistryNamespacedDefaultedByKey<ResourceLocation, Block> registry){
		for(Entry<String, Block> block : this.blocks.entrySet()){
			int id = Client.get().getFreeId();
			System.out.println("REGISTER " + id + " " + block.getKey());
			registry.register(id, new ResourceLocation(block.getKey()), block.getValue());
		}
	}
	
	@Override
	public ArrayList<Block> getBlocks(){
		ArrayList<Block> blocks = new ArrayList<Block>();
		for(Entry<String, Block> block : this.blocks.entrySet()){
			blocks.add(block.getValue());
		}
		return blocks;
	}
	
	@Override
	public ArrayList<String> getNames(){
		ArrayList<String> blocks = new ArrayList<String>();
		for(Entry<String, Block> block : this.blocks.entrySet()){
			blocks.add(block.getKey());
		}
		return blocks;
	}
	
}
