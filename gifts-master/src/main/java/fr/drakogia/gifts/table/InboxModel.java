package fr.drakogia.gifts.table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.drakogia.api.inventories.InventoryToBase64;
import fr.drakogia.gifts.object.Gift;
import fr.iambluedev.pine.api.field.IField;
import fr.iambluedev.pine.api.field.IFields;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

public class InboxModel extends Table {

	public InboxModel() {
		super("dons_inbox");
	}

	public List<Gift> getGifts() {
		List<Gift> gifts = new ArrayList<Gift>();

		List<IFields> datas = this.selectAll();

		for (IFields tmp : datas) {
			List<IField> fields = tmp.getFields();

			Integer sender = 0;
			Integer recipient = 0;
			String inventory = "";
			Integer at = 0;
			Integer money = 0;

			for (IField tmp2 : fields) {
				Field field = (Field) tmp2;

				if (field.getName().equals("sender_id")) {
					sender = (Integer) field.getValue();
				} else if (field.getName().equals("recipient_id")) {
					recipient = (Integer) field.getValue();
				} else if (field.getName().equals("inbox_inventory")) {
					inventory = (String) field.getValue();
				} else if (field.getName().equals("inbox_at")) {
					at = (Integer) field.getValue();
				} else if (field.getName().equals("inbox_money")) {
					money = (Integer) field.getValue();
				}
			}

			try {
				gifts.add(new Gift(sender, recipient, at, money, InventoryToBase64.fromBase64(inventory)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return gifts;
	}
	
	public void add(Integer recipient, Gift gift){
		Long now = (System.currentTimeMillis()) / 1000;
		
		this.insert(Arrays.asList(
			new Field("sender_id", gift.getSender()),
			new Field("recipient_id", recipient),
			new Field("inbox_inventory", InventoryToBase64.toBase64(gift.getInventory())),
			new Field("inbox_money", gift.getAmount()),
			new Field("inbox_at", now)
		));
	}

	public void remove(Integer player, Gift gift){
		this.getRequestHandler().executeUpdate("DELETE FROM dons_inbox WHERE sender_id = " + player + " AND recipient_id = " + gift.getRecipient() + " AND inbox_at = '" + gift.getTime() + "' AND inbox_money = '" + gift.getAmount() + "'");
	}
}
