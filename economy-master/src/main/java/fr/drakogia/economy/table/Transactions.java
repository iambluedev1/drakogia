package fr.drakogia.economy.table;

import java.util.Arrays;

import fr.drakogia.api.economy.EnumTransactionType;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

public class Transactions extends Table{

	private static Transactions instance;
	
	public Transactions() {
		super("transactions");
	}

	
	public static Transactions getInstance() {
		if (instance == null) {
			instance = new Transactions();
		}

		return instance;
	}
	
	public void save(Integer sender_id, Integer receiver_id, Integer amount, EnumTransactionType type){
		this.insert(Arrays.asList(
			new Field("transaction_sender", sender_id),
			new Field("transaction_receiver", receiver_id),
			new Field("transaction_type", type.getType()),
			new Field("transaction_amount", amount),
			new Field("transaction_at", (System.currentTimeMillis() / 1000))
		));
	}
}
