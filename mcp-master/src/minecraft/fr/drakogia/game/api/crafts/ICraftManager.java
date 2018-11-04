package fr.drakogia.game.api.crafts;

public interface ICraftManager<T> {

	void add(String name, T craft);
	
	void setup();
	
}
