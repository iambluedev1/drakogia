package fr.drakogia.game.core;

import fr.drakogia.game.api.client.IClient;
import fr.drakogia.game.blocks.BlockAmethyste;
import fr.drakogia.game.blocks.BlockDragonite;
import fr.drakogia.game.blocks.BlockDrakium;
import fr.drakogia.game.blocks.BlockRegistry;
import fr.drakogia.game.blocks.BlockTourmaline;
import fr.drakogia.game.blocks.BlockUranite;
import fr.drakogia.game.blocks.ores.OreAmethyste;
import fr.drakogia.game.blocks.ores.OreDragonite;
import fr.drakogia.game.blocks.ores.OreTourmaline;
import fr.drakogia.game.blocks.ores.OreUranite;
import fr.drakogia.game.crafts.CraftRegistry;
import fr.drakogia.game.items.ItemAmethyste;
import fr.drakogia.game.items.ItemDragonite;
import fr.drakogia.game.items.ItemDrakium;
import fr.drakogia.game.items.ItemRegistry;
import fr.drakogia.game.items.ItemTourmaline;
import fr.drakogia.game.items.ItemUranite;
import fr.drakogia.game.managers.BlockManager;
import fr.drakogia.game.managers.CraftManager;
import fr.drakogia.game.managers.ItemManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.util.registry.RegistryNamespacedDefaultedByKey;

public class Client implements IClient{

	private static Client instance;
	private BlockManager blockManager;
	private ItemManager itemManager;
	private CraftManager craftManager;
	private int lastUsedId = 460;
	
	private Client(){
		this.blockManager = new BlockManager();
		this.itemManager = new ItemManager();
		this.craftManager = new CraftManager();
	}
	
	public static Client get() {
		if(instance == null){
			instance = new Client();
		}
		
		return instance;
	}

	@Override
	public void onLoad() {}
	
	@Override
	public void onEnable() {}

	@Override
	public void onDisable() {}

	@Override
	public void run() {}

	@Override
	public void onBlockRegister(RegistryNamespacedDefaultedByKey<ResourceLocation, Block> registry) {
		new BlockRegistry().init(this.blockManager);
		this.blockManager.setup(registry);
	}
	
	@Override
	public void onItemRegister(RegistryNamespaced<ResourceLocation, Item> instance) {
		new ItemRegistry().init(this.itemManager);
		this.itemManager.setup(instance);
	}
	
	@Override
	public void onCraftsInit(){
		new CraftRegistry().init(this.craftManager);
	}
	
	@Override
	public void onFurnaceInit() {
		FurnaceRecipes.instance().addSmeltingRecipeForBlock(this.blockManager.getBlock("amethyste_ore"), new ItemStack(this.itemManager.getItem("amethyste")), 1.0F);
		FurnaceRecipes.instance().addSmeltingRecipeForBlock(this.blockManager.getBlock("dragonite_ore"), new ItemStack(this.itemManager.getItem("dragonite")), 1.0F);
		FurnaceRecipes.instance().addSmeltingRecipeForBlock(this.blockManager.getBlock("tourmaline_ore"), new ItemStack(this.itemManager.getItem("tourmaline")), 1.0F);
		FurnaceRecipes.instance().addSmeltingRecipeForBlock(this.blockManager.getBlock("uranite_ore"), new ItemStack(this.itemManager.getItem("uranite")), 1.0F);
	}
	
	public BlockManager getBlockManager(){
		return this.blockManager;
	}
	
	public ItemManager getItemManager(){
		return this.itemManager;
	}

	public CraftManager getCraftManager(){
		return this.craftManager;
	}
	
	@Override
	public int getFreeId() {
		lastUsedId++;
		return lastUsedId;
	}

	@Override
	public void setLastUsedId(int id) {
		this.lastUsedId = id;
	}

}
