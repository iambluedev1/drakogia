package fr.drakogia.api.heads;

import java.util.List;
import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.drakogia.api.builder.IBuilder;

public interface IHeadBuilder<V> extends IBuilder<ItemStack> {

	String getDisplayName();
	
	String getName();
	
	Integer getAmount();
	
	List<String> getLores();
	
	Map<Enchantment, Integer> getEnchantments(); 
	
	V withDisplayName(String displayName);
	
	V withAmount(Integer amount);
	
	V withLore(String... lore);
	
	V withEnchant(Enchantment enchant, Integer level);
	
}
