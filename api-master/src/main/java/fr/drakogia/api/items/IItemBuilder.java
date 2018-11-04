package fr.drakogia.api.items;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.drakogia.api.builder.IBuilder;

public interface IItemBuilder<V> extends IBuilder<ItemStack> {

	String getDisplayName();
	
	Integer getAmount();
	
	List<String> getLores();
	
	Map<Enchantment, Integer> getEnchantments();
	
	Material getMaterial();
	
	Byte getData();
	
	V withType(Material material);
	
	V withAmount(Integer amount);
	
	V withData(Byte data);
	
	V withLore(String ... lore);
	
	V withEnchant(Enchantment enchantment, Integer level);
	
	V withEnchantments(Map<Enchantment, Integer> enchants);
}
