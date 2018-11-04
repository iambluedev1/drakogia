package fr.drakogia.api.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Nms {

	public static String nms_protocol;

	public static Object getNMSPlayer(Player player) {
		try {

			Object handle = getCBClass("entity.CraftPlayer").getMethod("getHandle").invoke(player);
			return handle;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getNMSPlayerConnection(Player player) {
		try {
			Object entityPlayer = getNMSPlayer(player);
			Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

			return playerConnection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static Class<?> getCBClass(String string) {
		if (nms_protocol == null) {
			nms_protocol = Nms.getVersion();
		}

		return getSpecifiedClass("org.bukkit.craftbukkit." + nms_protocol + "." + string);
	}

	public static Class<?> getNMSClass(String string) {
		if (nms_protocol == null) {
			nms_protocol = Nms.getVersion();
		}

		return getSpecifiedClass("net.minecraft.server." + nms_protocol + "." + string);
	}

	public static Class<?> getSpecifiedClass(String string) {
		Class<?> class_ = null;
		try {
			class_ = Class.forName(string);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return class_;
	}

	public static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	}
}
