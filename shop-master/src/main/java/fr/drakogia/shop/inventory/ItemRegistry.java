package fr.drakogia.shop.inventory;

import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRegistry {

	private Integer position;
	private ItemStack item;
	private Integer price;
	
}
