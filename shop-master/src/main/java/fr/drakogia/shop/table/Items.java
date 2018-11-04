package fr.drakogia.shop.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import fr.drakogia.shop.inventory.InventoryItem;
import fr.drakogia.shop.inventory.InventoryType;
import fr.drakogia.shop.inventory.ItemRegistry;
import fr.iambluedev.pine.api.field.IField;
import fr.iambluedev.pine.api.field.IFields;
import xyz.anana.pine.core.field.Field;
import xyz.anana.pine.core.table.Table;

public class Items extends Table {

	private static Items instance;
	
	private Items() {
		super("shop_items");
	}
	
	public static Items getInstance(){
		if(instance == null){
			instance = new Items();
		}
		
		return instance;
	}

	public Map<InventoryType, List<ItemRegistry>> getItems(){
		Map<InventoryType, List<ItemRegistry>> main = new HashMap<InventoryType, List<ItemRegistry>>();
		List<ItemRegistry> subSell = new ArrayList<ItemRegistry>();
		List<ItemRegistry> subBuy = new ArrayList<ItemRegistry>();
		
		List<IFields> datas = this.getRequestHandler().select("SELECT * FROM shop_items ORDER BY item_position ASC;");
		
		
		for(IFields tmp : datas){
			List<IField> fields = tmp.getFields();
			
			Integer position = 0;
			Integer sell_price = 0;
			Integer buy_price = 0;
			Integer amount = 0;
			byte data = 0;
			Material material = null;
			
			for (IField tmp2 : fields) {
				Field field = (Field) tmp2;
				
				if (field.getName().equals("item_position")) {
					position =  (Integer) field.getValue();
				} else if (field.getName().equals("item_amount")) {
					amount =  (Integer) field.getValue();
				} else if (field.getName().equals("sell_price")) {
					if(field.getValue() != null){
						sell_price =  (Integer) field.getValue();
					}
				} else if (field.getName().equals("buy_price")) {
					if(field.getValue() != null){
						buy_price =  (Integer) field.getValue();
					}
				} else if (field.getName().equals("item_data")) {
					data = Byte.valueOf(String.valueOf(field.getValue()));
				} else if (field.getName().equals("item_material")) {
					System.out.println(field.getValue());
					material = Material.valueOf(String.valueOf(field.getValue()));
				}
			}
			
			if(sell_price != 0){
				subSell.add(new InventoryItem(position, sell_price, amount, data, material, InventoryType.SELL).get());
			}
			
			if(buy_price != 0){
				subBuy.add(new InventoryItem(position, buy_price, amount, data, material, InventoryType.BUY).get());
			}
		}
		
		System.out.println("Loaded " + subSell.size() + " items to sell");
		System.out.println("Loaded " + subBuy.size() + " items to buy");
		
		main.put(InventoryType.SELL, subSell);
		main.put(InventoryType.BUY, subBuy);
		
		return main;
	}
	
}
