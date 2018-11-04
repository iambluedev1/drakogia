package fr.drakogia.core.i18n;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.drakogia.api.i18n.ITranslator;
import fr.drakogia.api.i18n.Lang;
import lombok.Getter;

@Getter
public class Translator implements ITranslator<Message> {

	private Map<Lang, Message> cache;

	public Translator(Lang... supportedLang) {
		this.cache = new HashMap<Lang, Message>();
		for(Lang lang : supportedLang){
			this.cache.put(lang, new Message(lang));
		}
	}
	
	public String translate(Lang lang, String message_key, String... toReplace) {
		if(this.cache.containsKey(lang)){
			String msg = this.cache.get(lang).getTranslatedMessage(message_key);
			for(int i = 0 ; i < toReplace.length; i++){
				msg = msg.replace("{" + i + "}", toReplace[i]);
			}
			return msg.replace("&", "§");
		}else{
			return message_key;
		}
	}

	public String translate(Lang lang, String message_key, Boolean useCache, String... toReplace) {
		if(this.cache.containsKey(lang)){
			String msg = this.cache.get(lang).getTranslatedMessage(message_key, useCache);
			for(int i = 0 ; i < toReplace.length; i++){
				msg = msg.replace("{" + i + "}", toReplace[i]);
			}
			return msg.replace("&", "§");
		}else{
			return message_key;
		}

	}

	public String translateKey(String message) {
		String regex = "\\{\\{[a-zA-Z0-9-.]+\\}\\}";
		Pattern pattern = Pattern.compile(regex);
		for(String m : message.split(" ")){
			if(m.startsWith("{{") && m.endsWith("}}")){
				Matcher matcher = pattern.matcher(m);
				String message_key = m.replace(matcher.replaceAll(""), "").replace("{", "").replace("}", "");
				if(message_key != ""){
					message = message.replace("{{" + message_key + "}}", this.translate(Lang.FRENCH, message_key, ""));
				}
			}
		}
		return message;
	}

	public void clearCache() {
		for(Entry<Lang, Message> message : this.cache.entrySet()){
			message.getValue().clearCache();
		}
	}
}
