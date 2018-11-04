package fr.drakogia.api.ranks;

import java.util.List;

import fr.drakogia.api.manager.IManager;

public interface IRankManager<T> extends IManager {

	T get(String name);
	
	T get(Integer id);
	
	void load();
	
	void update(T rank);
	
	T getDefault();
	
	List<T> getRanks();
	
	boolean remove(String name);
	
	boolean add(String name);
	
	void save();
}
