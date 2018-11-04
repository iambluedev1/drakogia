package fr.drakogia.api.i18n;

public interface IMessage extends ICache<String, String>, ILang {

	String getTranslatedMessage(String message_key);
	
	String getTranslatedMessage(String message_key, Boolean useCache);
	
	String getMessage(String message_key);
}
