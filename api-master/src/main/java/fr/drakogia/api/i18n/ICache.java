package fr.drakogia.api.i18n;

import java.util.Map;

public interface ICache<T, V> {

	Map<T, V> getCache();
	
	void clearCache();
	
}
