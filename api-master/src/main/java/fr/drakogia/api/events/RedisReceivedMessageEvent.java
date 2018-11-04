package fr.drakogia.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RedisReceivedMessageEvent  extends Event{
	
	private static final HandlerList handlers = new HandlerList();
	
	private String message;
	private String channel;
	
	public RedisReceivedMessageEvent(String channel, String message){
		this.message = message;
		this.channel = channel;
	}
	
	public String getMessage() {
		return this.message;
	}

	public String getChannel() {
		return this.channel;
	}

	public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
