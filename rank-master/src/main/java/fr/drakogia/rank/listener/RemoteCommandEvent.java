package fr.drakogia.rank.listener;

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
			String content = (String) jsonObj.get("content");


			if(cmd.equals("rank:set")){
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "/ranset " + content);
				System.out.println("Executing Command : /ranset " + content + " ordered by CONSOLE (web)");
			} else if(cmd.equals("rank:reload")){
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "/rankreload");
				System.out.println("Executing Command : /rankreload ordered by CONSOLE (web)");
			}
		}
	}
	
}
