package fr.drakogia.game.blocks;

import fr.drakogia.game.api.client.IRegistry;
import fr.drakogia.game.blocks.ores.OreAmethyste;
import fr.drakogia.game.blocks.ores.OreDragonite;
import fr.drakogia.game.blocks.ores.OreTourmaline;
import fr.drakogia.game.blocks.ores.OreUranite;
import fr.drakogia.game.managers.BlockManager;

public class BlockRegistry implements IRegistry<BlockManager>{

	@Override
	public void init(BlockManager manager) {
		manager.add("amethyste_ore", new OreAmethyste());
		manager.add("dragonite_ore", new OreDragonite());
		manager.add("tourmaline_ore", new OreTourmaline());
		manager.add("uranite_ore", new OreUranite());
		
		manager.add("amethyste_block", new BlockAmethyste());
		manager.add("dragonite_block", new BlockDragonite());
		manager.add("drakium_block", new BlockDrakium());
		manager.add("tourmaline_block", new BlockTourmaline());
		manager.add("uranite_block", new BlockUranite());
	}

}
