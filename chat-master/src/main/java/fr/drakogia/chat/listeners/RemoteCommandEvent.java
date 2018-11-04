package fr.drakogia.chat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.drakogia.api.events.RedisReceivedMessageEvent;

public class RemoteCommandEvent implements Listener {

	@EventHandler
	public void onRemoteCommand(RedisReceivedMessageEvent event){
		if(event.getChannel().equals("commands")){
			String msg = event.getMessage();
			
			JSONObject jsonObj = null;
			try {
				jsonObj = (JSONObject) new JSONParser().parse(msg);
			} catch (ParseException e) {}

			String cmd = (String) jsonObj.get("cmd");

			if(cmd.equals("chat:clear")){
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "/clearchat");
				System.out.println("Executing Command : /clearchat ordered by CONSOLE (web)");
			} else if(cmd.equals("chat:toggle")){
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "/chatlock");
				System.out.println("Executing Command : /chatlock ordered by CONSOLE (web)");
			}
		}
	}
	
}
