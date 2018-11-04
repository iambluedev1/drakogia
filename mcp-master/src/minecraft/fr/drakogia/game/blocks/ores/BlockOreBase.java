package fr.drakogia.game.blocks.ores;

import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;

public class BlockOreBase extends BlockOre{

	public BlockOreBase(float hardness, float resistance, String name){
		this.setHardness(hardness);
		this.setResistance(resistance);
		this.setSoundType(SoundType.STONE);
		this.setUnlocalizedName(name);
	}
	
}
