package fr.drakogia.core.i18n;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.drakogia.api.i18n.IMessage;
import fr.drakogia.api.i18n.Lang;
import fr.iambluedev.pine.api.field.IField;
import lombok.Getter;
import xyz.anana.pine.core.database.Database;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

@Getter
public class Message implements IMessage {

	private Lang lang;
	private Map<String, String> cache;
	
	public Message(Lang lang){
		this.lang = lang;
		this.cache = new HashMap<String, String>();
	}

	public String getTranslatedMessage(String message_key) {
		if(this.getCache().containsKey(message_key)){
			return this.getCache().get(message_key);
		}else{
			this.getCache().put(message_key, this.getMessage(message_key));
			return this.getCache().get(message_key);
		}
	}
	
	public String getTranslatedMessage(String message_key, Boolean useCache) {
		if(useCache){
			return this.getTranslatedMessage(message_key);
		}else{
			return this.getMessage(message_key);
		}
	}
	
	public String getMessage(String message_key) {
		Database database = Database.getInstance();
		Table i18n = (Table) database.getTable("i18n");
		
		String msg = message_key;
		
		List<IField> where = new ArrayList<IField>(Arrays.asList(
				new Field("message_key", message_key),
				new Field("lang_id", this.lang.getId())
		));
		
		if(i18n.exist("message_key", where)) {
			msg = (String) i18n.select("message_value", where);
		}
			
		return msg;
	}

	public void clearCache() {
		this.cache.clear();
	}
}
