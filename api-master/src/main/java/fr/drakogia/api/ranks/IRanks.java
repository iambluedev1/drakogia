package fr.drakogia.api.ranks;

import java.util.List;

public interface IRanks<T> {

	List<T> getRanks();
	
	Integer addRank(String name, String prefix, String tablist, boolean isDefault);
	
	void removeRank(String name);
	
	void updateRank(T rank);
}
