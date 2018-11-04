package fr.drakogia.chat.table;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import xyz.anana.pine.core.table.Table;

@Getter
public class Badwords extends Table {

	private List<String> badwords;
	private static Badwords instance;
	
	private Badwords() {
		super("badwords");
		this.badwords = new ArrayList<String>();
	}
	
	public static Badwords getInstance(){
		if(instance == null){
			instance = new Badwords();
		}
		
		return instance;
	}
	
	public void loadBadWords(){
		List<Object> results = this.selectList("badword_content");
		for(Object result : results){
			this.badwords.add((String) result);
		}
	}
	
	public boolean isBadWord(String word){
		for (String badword : this.getBadwords()) {
            if (badword.equalsIgnoreCase(word)) return true;
        }
        return false;
	}
}
