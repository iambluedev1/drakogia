package fr.drakogia.core.heads;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import fr.drakogia.api.heads.IHeadBuilder;
import lombok.Getter;

@Getter
public class HeadBuilder implements IHeadBuilder<HeadBuilder> {

	private String displayName;
	private String name;
	private Integer amount;
	private List<String> lores;
	private Map<Enchantment, Integer> enchantments;

	public HeadBuilder(String name) {
		this.name = name;
		this.amount = 1;
	}

	public HeadBuilder(String name, Integer amount) {
		this.name = name;
		this.amount = amount;
	}

	public HeadBuilder withDisplayName(String displayName) {
		this.displayName = displayName;
		return this;
	}

	public HeadBuilder withAmount(Integer amount) {
		this.amount = amount;
		return this;
	}

	public HeadBuilder withLore(String... lore) {
		this.lores = Arrays.asList(lore);
		return this;
	}
	
	public HeadBuilder withLore(List<String> lore) {
		this.lores = lore;
		return this;
	}

	public HeadBuilder withEnchant(Enchantment enchant, Integer level) {
		if (this.enchantments == null) {
			this.enchantments = new HashMap<Enchantment, Integer>();
		}
		this.enchantments.put(enchant, level);
		return this;
	}

	@SuppressWarnings("deprecation")
	public ItemStack build() {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, this.amount, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setLore(this.lores);
		meta.setOwner(this.name);
		if (this.name != null) {
			meta.setDisplayName(this.displayName);
		}
		if (this.enchantments != null) {
			for (Map.Entry<Enchantment, Integer> enchant : this.enchantments.entrySet()) {
				meta.addEnchant(enchant.getKey(), enchant.getValue(), true);
			}
		}

		item.setItemMeta(meta);
		return item;
	}

}
