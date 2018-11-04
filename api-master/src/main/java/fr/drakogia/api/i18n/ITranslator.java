package fr.drakogia.api.i18n;

public interface ITranslator<T> extends ICache<Lang, T> {

	String translate(Lang lang, String message_key, String... toReplace);
	
	String translate(Lang lang, String message_key, Boolean useCache, String... toReplace);
	
	String translateKey(String message);
}
