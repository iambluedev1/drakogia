package fr.drakogia.shop.inventory;

import org.bukkit.Material;

import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.items.ItemBuilder;
import lombok.Getter;

@Getter
public class InventoryItem {

	private Integer position;
	private Integer price;

	private ItemBuilder builder;

	private Integer amount;
	private byte data;
	private Material material;
	private InventoryType type;

	public InventoryItem(Integer position, Integer price, Integer amount, byte data, Material material, InventoryType type) {
		this.position = position;
		this.price = price;
		this.amount = amount;
		this.data = data;
		this.material = material;
		this.type = type;
	}

	public ItemRegistry get() {
		this.builder = new ItemBuilder(null);
		this.builder
			.withAmount(this.amount)
			.withData(this.data)
			.withType(this.material)
			.withLore(this.genLore());

		return new ItemRegistry(this.position, this.builder.build(), this.price);
	}

	private String[] genLore(){
		if(this.type == InventoryType.SELL){
			return new String[]{
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", ""),
					" ",
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_lore_price", String.valueOf(this.price)),
					" ",
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_info_sell_1", ""),
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_info_sell_2", ""),
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_info_sell_3", ""),
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_info_sell_4", ""),
					"",
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", "")
			};
		}else{
			return new String[]{
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", ""),
					" ",
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_shop_lore_price", String.valueOf(this.price)),
					" ",
					DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", "")
			};
		}
	}
}
