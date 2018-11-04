package fr.drakogia.core.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.drakogia.api.items.IItemBuilder;
import lombok.Getter;

@Getter
public class ItemBuilder implements IItemBuilder<ItemBuilder> {

	private Material material;
	private Integer amount;
	private Byte data;
	private String displayName;
	private List<String> lores;
	private Map<Enchantment, Integer> enchantments;

	public ItemBuilder(String displayName) {
		this.displayName = displayName;
		this.amount = 1;
		this.data = 0;
	}

	public ItemBuilder(String displayName, Integer amount) {
		this.displayName = displayName;
		this.amount = amount;
	}

	public ItemBuilder withType(Material material) {
		this.material = material;
		return this;
	}

	public ItemBuilder withAmount(Integer amount) {
		this.amount = amount;
		return this;
	}

	public ItemBuilder withData(Byte data) {
		this.data = data;
		return this;
	}

	public ItemBuilder withLore(String... lore) {
		this.lores = Arrays.asList(lore);
		return this;
	}

	public ItemBuilder withEnchant(Enchantment enchantment, Integer level) {
		if (this.enchantments == null) {
			this.enchantments = new HashMap<Enchantment, Integer>();
		}
		this.enchantments.put(enchantment, level);
		return this;
	}

	public ItemBuilder withEnchantments(Map<Enchantment, Integer> enchants) {
		this.enchantments = enchants;
		return this;
	}

	public ItemStack build() {
		ItemStack itemStack = new ItemStack(this.getMaterial(), this.getAmount().intValue(), (short) this.getData().byteValue());
		if (this.displayName != null || this.lores != null) {
			ItemMeta itemMeta = itemStack.getItemMeta();
			if (this.displayName != null) {
				itemMeta.setDisplayName(this.displayName);
			}
			if (this.lores != null) {
				itemMeta.setLore(this.lores);
			}
			itemStack.setItemMeta(itemMeta);
		}
		if (this.enchantments != null) {
			for (Enchantment enchantment : this.enchantments.keySet()) {
				int n = this.enchantments.get((Object) enchantment);
				itemStack.addUnsafeEnchantment(enchantment, n);
			}
		}
		return itemStack;

	}

}
