package fr.drakogia.game.api.manager;

import java.util.ArrayList;

import net.minecraft.block.Block;

public interface IElementManager<T> {

	void add(String name, T block);
	
	ArrayList<String> getNames();
}
