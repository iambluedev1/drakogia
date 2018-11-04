package fr.drakogia.core.utils;

import org.bukkit.ChatColor;

public class ColorizeUtils {

	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

}
