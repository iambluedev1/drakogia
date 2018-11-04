package fr.drakogia.gifts.table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.iambluedev.pine.api.field.IField;
import fr.iambluedev.pine.api.field.IFields;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

public class IgnoreModel extends Table {

	public IgnoreModel() {
		super("dons_ignores");
	}
	
	public Map<Integer, Integer> getIgnores(){
		Map<Integer, Integer> ignores = new HashMap<Integer, Integer>();
		
		List<IFields> datas = this.selectAll();
		
		for(IFields tmp : datas){
			List<IField> fields = tmp.getFields();
			
			Integer ignorer = 0;
			Integer ignored = 0;
			
			for (IField tmp2 : fields) {
				Field field = (Field) tmp2;
				
				if (field.getName().equals("ignorer_id")) {
					ignorer = (Integer) field.getValue();
				}else if (field.getName().equals("ignored_id")) {
					ignored = (Integer) field.getValue();
				}
			}
			
			ignores.put(ignorer, ignored);
		}
		
		return ignores;
	}
	
	public void add(Integer ignorer, Integer ignored){
		Long now = (System.currentTimeMillis()) / 1000;
		
		this.insert(Arrays.asList(
			new Field("ignorer_id", ignorer),
			new Field("ignored_id", ignored),
			new Field("ignore_at", now)
		));
	}
	
	public void remove(Integer ignorer, Integer ignored){
		this.getRequestHandler().executeUpdate("DELETE FROM dons_ignores WHERE ignorer_id = " + ignorer + " AND ignored_id = " + ignored + ";");
	}
}
