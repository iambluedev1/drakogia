package fr.drakogia.game.items;

import fr.drakogia.game.api.client.IRegistry;
import fr.drakogia.game.managers.ItemManager;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;

public class ItemRegistry implements IRegistry<ItemManager>{

	@Override
	public void init(ItemManager manager) {
		manager.add("amethyste", new ItemAmethyste());
		manager.add("dragonite", new ItemDragonite());
		manager.add("drakium", new ItemDrakium());
		manager.add("tourmaline", new ItemTourmaline());
		manager.add("uranite", new ItemUranite());
		
		manager.add("amethyste_axe", (new ItemAxe(Item.ToolMaterial.AMETHYSTE)).setUnlocalizedName("hatchetAmethyste"));
		manager.add("dragonite_axe", (new ItemAxe(Item.ToolMaterial.DRAGONITE)).setUnlocalizedName("hatchetDragonite"));
		manager.add("drakium_axe", (new ItemAxe(Item.ToolMaterial.DRAKIUM)).setUnlocalizedName("hatchetDrakium"));
		manager.add("tourmaline_axe", (new ItemAxe(Item.ToolMaterial.TOURMALINE)).setUnlocalizedName("hatchetTourmaline"));
		manager.add("uranite_axe",(new ItemAxe(Item.ToolMaterial.URANITE)).setUnlocalizedName("hatchetUranite"));
		
		manager.add("amethyste_hoe", (new ItemHoe(Item.ToolMaterial.AMETHYSTE)).setUnlocalizedName("hoeAmethyste"));
		manager.add("dragonite_hoe", (new ItemHoe(Item.ToolMaterial.DRAGONITE)).setUnlocalizedName("hoeDragonite"));
		manager.add("drakium_hoe", (new ItemHoe(Item.ToolMaterial.DRAKIUM)).setUnlocalizedName("hoeDrakium"));
		manager.add("tourmaline_hoe", (new ItemHoe(Item.ToolMaterial.TOURMALINE)).setUnlocalizedName("hoeTourmaline"));
		manager.add("uranite_hoe", (new ItemHoe(Item.ToolMaterial.URANITE)).setUnlocalizedName("hoeUranite"));
		
		manager.add("amethyste_pickaxe", (new ItemPickaxe(Item.ToolMaterial.AMETHYSTE)).setUnlocalizedName("pickaxeAmethyste"));
		manager.add("dragonite_pickaxe", (new ItemPickaxe(Item.ToolMaterial.DRAGONITE)).setUnlocalizedName("pickaxeDragonite"));
		manager.add("drakium_pickaxe", (new ItemPickaxe(Item.ToolMaterial.DRAKIUM)).setUnlocalizedName("pickaxeDrakium"));
		manager.add("tourmaline_pickaxe", (new ItemPickaxe(Item.ToolMaterial.TOURMALINE)).setUnlocalizedName("pickaxeTourmaline"));
		manager.add("uranite_pickaxe", (new ItemPickaxe(Item.ToolMaterial.URANITE)).setUnlocalizedName("pickaxeUranite"));
		
		manager.add("amethyste_shovel", (new ItemSpade(Item.ToolMaterial.AMETHYSTE)).setUnlocalizedName("shovelAmethyste"));
		manager.add("dragonite_shovel", (new ItemSpade(Item.ToolMaterial.DRAGONITE)).setUnlocalizedName("shovelDragonite"));
		manager.add("drakium_shovel", (new ItemSpade(Item.ToolMaterial.DRAKIUM)).setUnlocalizedName("shovelDrakium"));
		manager.add("tourmaline_shovel", (new ItemSpade(Item.ToolMaterial.TOURMALINE)).setUnlocalizedName("shovelTourmaline"));
		manager.add("uranite_shovel", (new ItemSpade(Item.ToolMaterial.URANITE)).setUnlocalizedName("shovelUranite"));
		
		manager.add("amethyste_sword", (new ItemSword(Item.ToolMaterial.AMETHYSTE)).setUnlocalizedName("swordAmethyste"));
		manager.add("dragonite_sword", (new ItemSword(Item.ToolMaterial.DRAGONITE)).setUnlocalizedName("swordDragonite"));
		manager.add("drakium_sword", (new ItemSword(Item.ToolMaterial.DRAKIUM)).setUnlocalizedName("swordDrakium"));
		manager.add("tourmaline_sword", (new ItemSword(Item.ToolMaterial.TOURMALINE)).setUnlocalizedName("swordTourmaline"));
		manager.add("uranite_sword", (new ItemSword(Item.ToolMaterial.URANITE)).setUnlocalizedName("swordUranite"));
		
		manager.add("tourmaline_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.TOURMALINE, 5, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetTourmaline"));
		manager.add("drakium_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.DRAKIUM, 6, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetdDrakium"));
		manager.add("dragonite_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.DRAGONITE, 7, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetDragonite"));
		manager.add("uranite_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.URANITE, 8, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetUranite"));
		manager.add("amethyste_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.AMETHYSTE, 9, EntityEquipmentSlot.HEAD)).setUnlocalizedName("helmetAmethyste"));
		
		manager.add("tourmaline_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.TOURMALINE, 5, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateTourmaline"));
		manager.add("drakium_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.DRAKIUM, 6, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateDrakium"));
		manager.add("dragonite_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.DRAGONITE, 7, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateDragonite"));
		manager.add("uranite_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.URANITE, 8, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateUranite"));
		manager.add("amethyste_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.AMETHYSTE, 9, EntityEquipmentSlot.CHEST)).setUnlocalizedName("chestplateAmethyste"));
		
		manager.add("tourmaline_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.TOURMALINE, 5, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsTourmaline"));
		manager.add("drakium_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.DRAKIUM, 6, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsDrakium"));
		manager.add("dragonite_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.DRAGONITE, 7, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsDragonite"));
		manager.add("uranite_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.URANITE, 8, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsUranite"));
		manager.add("amethyste_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.AMETHYSTE, 9, EntityEquipmentSlot.LEGS)).setUnlocalizedName("leggingsAmethyste"));
		
		manager.add("tourmaline_boots", (new ItemArmor(ItemArmor.ArmorMaterial.TOURMALINE, 5, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsTourmaline"));
		manager.add("drakium_boots", (new ItemArmor(ItemArmor.ArmorMaterial.DRAKIUM, 6, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsDrakium"));
		manager.add("dragonite_boots", (new ItemArmor(ItemArmor.ArmorMaterial.DRAGONITE, 7, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsDragonite"));
		manager.add("uranite_boots", (new ItemArmor(ItemArmor.ArmorMaterial.URANITE, 8, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsUranite"));
		manager.add("amethyste_boots", (new ItemArmor(ItemArmor.ArmorMaterial.AMETHYSTE, 9, EntityEquipmentSlot.FEET)).setUnlocalizedName("bootsAmethyste"));
	}

}
