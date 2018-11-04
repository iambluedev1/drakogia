package fr.drakogia.game.crafts;

import fr.drakogia.game.api.client.IRegistry;
import fr.drakogia.game.core.Client;
import fr.drakogia.game.managers.CraftManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;

public class CraftRegistry implements IRegistry<CraftManager> {

	@Override
	public void init(CraftManager manager) {
		
		String[] minerals = new String[]{ "amethyste", "dragonite", "drakium", "tourmaline", "uranite"};
		
		for(int i = 0; i < minerals.length; i++){
			String mineral = minerals[i];
			
			// ITEM
			manager.add("craft_" + mineral, new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X" })
				.addItem("X", getBlock(mineral + "_block"))
				.setOutput(getItem(mineral, 9))
				.build()
			);
			
			//BLOCK
			manager.add("craft_" + mineral + "_block", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X", "X", "X" })
				.addLine(new String[] { "X", "X", "X" })
				.addLine(new String[] { "X", "X", "X" })
				.addItem("X", getItem(mineral))
				.setOutput(getBlock(mineral + "_block"))
				.build()
			);
			
			//HELMET
			manager.add("craft_" + mineral + "_helmet", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X", "X", "X" })
				.addLine(new String[] { "X", "", "X" })
				.addItem("X", getItem(mineral))
				.setOutput(getItem(mineral + "_helmet"))
				.build()
			);
			
			//CHESTPLATE
			manager.add("craft_" + mineral + "_chestplate", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X", "", "X" })
				.addLine(new String[] { "X", "X", "X" })
				.addLine(new String[] { "X", "X", "X" })
				.addItem("X", getItem(mineral))
				.setOutput(getItem(mineral + "_chestplate"))
				.build()
			);
			
			//LEGGINGS
			manager.add("craft_" + mineral + "_leggings", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X", "X", "X" })
				.addLine(new String[] { "X", "", "X" })
				.addLine(new String[] { "X", "", "X" })
				.addItem("X", getItem(mineral))
				.setOutput(getItem(mineral + "_leggings"))
				.build()
			);
			
			//BOOTS
			manager.add("craft_" + mineral + "_boots", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X", "", "X" })
				.addLine(new String[] { "X", "", "X" })
				.addItem("X", getItem(mineral))
				.setOutput(getItem(mineral + "_boots"))
				.build()
			);
			
			//SWORD
			manager.add("craft_" + mineral + "_sword", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X" })
				.addLine(new String[] { "X" })
				.addLine(new String[] { "Y" })
				.addItem("X", getItem(mineral))
				.addItem("Y", new ItemStack(Items.STICK))
				.setOutput(getItem(mineral + "_sword"))
				.build()
			);
			
			//HOE
			manager.add("craft_" + mineral + "_hoe", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X", "X" })
				.addLine(new String[] { "", "Y" })
				.addLine(new String[] { "", "Y" })
				.addItem("X", getItem(mineral))
				.addItem("Y", new ItemStack(Items.STICK))
				.setOutput(getItem(mineral + "_hoe"))
				.build()
			);
			
			//AXE
			manager.add("craft_" + mineral + "_axe", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X", "X" })
				.addLine(new String[] { "X", "Y" })
				.addLine(new String[] { "", "Y" })
				.addItem("X", getItem(mineral))
				.addItem("Y", new ItemStack(Items.STICK))
				.setOutput(getItem(mineral + "_axe"))
				.build()
			);
			
			//SHOVEL
			manager.add("craft_" + mineral + "_shovel", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X" })
				.addLine(new String[] { "Y" })
				.addLine(new String[] { "Y" })
				.addItem("X", getItem(mineral))
				.addItem("Y", new ItemStack(Items.STICK))
				.setOutput(getItem(mineral + "_shovel"))
				.build()
			);
			
			//PICKAXE
			manager.add("craft_" + mineral + "_pickaxe", new CustomCraft<ShapedRecipes>(ShapedRecipes.class)
				.addLine(new String[] { "X", "X", "X" })
				.addLine(new String[] { "", "Y", "" })
				.addLine(new String[] { "", "Y", "" })
				.addItem("X", getItem(mineral))
				.addItem("Y", new ItemStack(Items.STICK))
				.setOutput(getItem(mineral + "_pickaxe"))
				.build()
			);
		}
	}

	private ItemStack getItem(String name){
		return new ItemStack(Client.get().getItemManager().getItem(name));
	}
	
	private ItemStack getItem(String name, Integer quantity){
		return new ItemStack(Client.get().getItemManager().getItem(name), quantity);
	}
	
	private ItemStack getBlock(String name){
		return new ItemStack(Client.get().getBlockManager().getBlock(name));
	}
	
	private ItemStack getBlock(String name, Integer quantity){
		return new ItemStack(Client.get().getBlockManager().getBlock(name), quantity);
	}
}
