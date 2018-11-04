package fr.drakogia.api.redis;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import fr.drakogia.api.utils.Callback;
import lombok.Getter;
import redis.clients.jedis.JedisPubSub;

@Getter
public abstract class AbstractSubscriber extends JedisPubSub implements Callable<String[]>{

	private AbstractRedis redis;
	private AbstractPublisher redisPublisher;
	
	public AbstractSubscriber(AbstractPublisher publisher, AbstractRedis redis){
		this.redis = redis;
		this.redisPublisher = publisher.setRedisSubscriber(this).send();
	}

	public abstract void get(Callback<String[]> callback) throws InterruptedException, ExecutionException;
}
