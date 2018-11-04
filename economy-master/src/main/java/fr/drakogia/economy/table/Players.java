package fr.drakogia.economy.table;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.drakogia.economy.DrakogiaEconomy;
import fr.iambluedev.pine.api.field.IField;
import fr.iambluedev.pine.api.field.IFields;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

public class Players extends Table {

	private static Players instance;
	
	public Players() {
		super("users");
	}
	
	public static Players getInstance() {
		if (instance == null) {
			instance = new Players();
		}

		return instance;
	}
	
	public Map<String, Integer> getRanking(){
		return this.list(0, DrakogiaEconomy.getInstance().getDrakogiaConfig().getFileConfig().getInt("baltop.perPage"));
	}
	
	public Map<String, Integer> getRanking(Integer page){
		return this.list(page, DrakogiaEconomy.getInstance().getDrakogiaConfig().getFileConfig().getInt("baltop.perPage"));
	}
	
	public Map<String, Integer> getRanking(Integer page, Integer perPage){
		return this.list(page, perPage);
	}
	
	private Map<String, Integer> list(Integer page, Integer perPage){
		page = page * perPage;
		perPage = page + perPage;
		List<IFields> datas = this.getRequestHandler().select("SELECT user_login, user_money FROM users ORDER BY user_money DESC LIMIT " + page + ", " + perPage + ";");
		
		TreeMap<String, Integer> baltop = new TreeMap<String, Integer>();
		
		for(IFields tmp : datas){
			List<IField> fields = tmp.getFields();
			
			String name = "";
			Integer amount = 0;
			
			for (IField tmp2 : fields) {
				Field field = (Field) tmp2;
				if (field.getName().equals("user_login")) {
					name = (String) field.getValue();
				} else if (field.getName().equals("user_money")) {
					amount = (Integer) field.getValue();
				}
			}
			
			baltop.put(name, amount);
		}
		
		return this.sortByValues(baltop);
	}
	
	/*
	 * @author rovergames
	 */
	private <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =
                (k1, k2) -> {
                    int compare = map.get(k1).compareTo(map.get(k2));
                    if (compare == 0)
                        return 1;
                    else
                        return -compare;
                };

        Map<K, V> sortedByValues =
                new TreeMap<>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

}
