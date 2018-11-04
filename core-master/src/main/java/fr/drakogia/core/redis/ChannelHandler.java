package fr.drakogia.core.redis;

import org.bukkit.Bukkit;

import fr.drakogia.api.events.RedisReceivedMessageEvent;
import redis.clients.jedis.JedisPubSub;

public class ChannelHandler extends JedisPubSub {

	@Override
	public void onMessage(String channel, String message) {
		Bukkit.getServer().getPluginManager().callEvent(new RedisReceivedMessageEvent(channel, message));
	}
	
}
