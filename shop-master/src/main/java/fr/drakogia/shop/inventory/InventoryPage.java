package fr.drakogia.shop.inventory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryPage {

	private List<ItemRegistry> items;
	
}
