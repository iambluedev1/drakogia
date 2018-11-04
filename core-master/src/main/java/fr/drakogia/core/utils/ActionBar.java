package fr.drakogia.core.utils;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import fr.drakogia.api.nms.Nms;
import fr.drakogia.api.utils.IActionBar;
import lombok.Getter;

@Getter
public class ActionBar implements IActionBar {

	private JSONObject json;

	public ActionBar(String text) {
		this.json = this.convert(text);
	}
	
	public ActionBar(JSONObject json){
		this.json = json;
	}

	@Override
	public void sendActionBar(Player player) {
		try {
			Class<?> clsChatBaseComponent = Nms.getNMSClass("IChatBaseComponent");
			Class<?> clsChatMessageType = Nms.getNMSClass("ChatMessageType");

			Object chatBaseComponent = Nms.getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, this.json.toString());
			Object chatMessageType = clsChatMessageType.getMethod("valueOf", String.class).invoke(null, "GAME_INFO");
			Object packetPlayOutChat = Nms.getNMSClass("PacketPlayOutChat").getConstructor(clsChatBaseComponent, clsChatMessageType).newInstance(chatBaseComponent, chatMessageType);
			Object playerConnection = Nms.getNMSPlayerConnection(player);
			
			playerConnection.getClass().getMethod("sendPacket", Nms.getNMSClass("Packet")).invoke(playerConnection, packetPlayOutChat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private JSONObject convert(String text) {
		JSONObject json = new JSONObject();
		json.put("text", text);
		return json;
	}
}
