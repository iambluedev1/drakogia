package fr.drakogia.api.redis;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class AbstractPublisher {
	
	@Setter
	private String channel, message;
	
	private UUID id;
	private AbstractSubscriber redisSubscriber;

	public AbstractPublisher(){
		this.id = UUID.randomUUID();
	}
	
	public AbstractPublisher setRedisSubscriber(AbstractSubscriber redisSubscriber) {
		this.redisSubscriber = redisSubscriber;
		return this;
	}

	
	public abstract AbstractPublisher send();
	
	public abstract String getJson();

}
