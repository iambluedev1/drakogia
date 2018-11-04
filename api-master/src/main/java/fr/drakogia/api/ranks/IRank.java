package fr.drakogia.api.ranks;

import java.util.List;

public interface IRank<T> {

	List<T> getPermissions();
	
	String getName();
	
	String getPrefix();
	
	String getTablist();
	
	boolean isDefault();
	
	Integer getId();
	
}
