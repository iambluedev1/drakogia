package fr.drakogia.api.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import fr.drakogia.api.utils.Callback;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Getter
public class AbstractRedis {

	private String host;
	private Integer port;
	private String password;
	private JedisPool jedis;

	public AbstractRedis(String host, String password, Integer port){
		this.host = host;
		this.port = port;
		this.password = password;
		this.jedis = new JedisPool(new GenericObjectPoolConfig(), this.host, this.port, 500, this.password);
	}
	
	public AbstractRedis(String host, Integer port){
		this.host = host;
		this.port = port;
		this.jedis = new JedisPool(new GenericObjectPoolConfig(), this.host, this.port, 500);
	}

	public void get(Callback<Jedis> callback){
		try {
            Jedis jedis = this.jedis.getResource();
            callback.call(jedis);
            jedis.close();
        }catch (JedisConnectionException e) {
            e.printStackTrace();
        }
	}

	public void close(){
		this.jedis.destroy();
	}
	
}
