package fr.drakogia.gifts.object;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.Inventory;

import fr.drakogia.api.i18n.Lang;
import fr.drakogia.core.DrakogiaApi;
import fr.drakogia.core.inventories.InventoryUtil;
import fr.drakogia.gifts.DrakogiaGifts;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Gift {

	private Integer sender;
	private Integer recipient;
	private Integer time;
	
	@Setter
	private Integer amount;
	private Inventory inventory;
	
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM à HH'h'mm");
	
	public Gift(Integer sender, Integer recipient, Integer time, Integer amount, Inventory inventory) {
		this.sender = sender;
		this.recipient = recipient;
		this.time = time;
		this.amount = amount;
		
		if(inventory != null)
			this.inventory = InventoryUtil.copyInventory(inventory, "Don de " + sender);
	}

	public Gift(Integer sender, Integer recipient, Integer time, Inventory inventory) {
		this(sender, recipient, time, 0, inventory);
	}
	
	public void setInventory(Inventory inv){
		this.inventory = InventoryUtil.copyInventory(inv, "Don de " + this.sender);
	}
	
	public List<String> getLore(){
		return Arrays.asList(
			DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", ""),
			" ",
			DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_gui_box_date", Instant.ofEpochSecond(this.time).atZone(ZoneId.of("GMT+2")).format(formatter)),
			DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_dons_gui_box_expire", Instant.ofEpochSecond(this.time + (DrakogiaGifts.getInstance().getDrakogiaConfig().getFileConfig().getInt("don.auto.delete") * 24 * 60 * 60)).atZone(ZoneId.of("GMT+2")).format(formatter)),
			" ", 
			DrakogiaApi.getInstance().getTranslator().translate(Lang.FRENCH, "drakogia_global_line_small", ""));
	}
}
