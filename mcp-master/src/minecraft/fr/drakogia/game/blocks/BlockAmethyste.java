package fr.drakogia.game.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
 
public class BlockAmethyste extends Block {

	public BlockAmethyste() {
		super(Material.IRON, MapColor.DIAMOND);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setUnlocalizedName("blockAmethyste");
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

}
