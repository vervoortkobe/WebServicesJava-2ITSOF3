package oef1.fullproject.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

	@Autowired
	private StringRedisTemplate stringTemplate;
	
	@SuppressWarnings("null")
	private RedisConnection getConnection() {
		return this.stringTemplate.getConnectionFactory().getConnection();
	}

	public void setKey(String key, String value) {
		this.stringTemplate.opsForValue().set(key, value);
	}

	public String getKey(String key) {
 		return this.stringTemplate.opsForValue().get(key);
	}

	public boolean hasKey(String key) {
		return this.stringTemplate.hasKey(key);
	}

	public Set<String> keys(String pattern) {
		return this.stringTemplate.keys(pattern);
	}

	public void hset(String key, Map<String, String> values) {
		this.stringTemplate.opsForHash().putAll(key, values);
	}

	public Map<Object, Object> hgetAll(String key) {
		 return stringTemplate.opsForHash().entries(key);
	}

	public Long rpush(String key, String value)  {
		return this.stringTemplate.opsForList().rightPush(key, value);
	}

	public Long lpush(String key, String value)  {
		return this.stringTemplate.opsForList().leftPush(key, value);
	}
	
	public List<String> getList(String key) {
		return this.stringTemplate.opsForList().range(key, 0, -1);
	}
	
	public void sendMessage(String channel, String message) {
		this.stringTemplate.convertAndSend(channel, message);
	}

	public void delete(String key) {
		this.stringTemplate.delete(key);
	}

	public void expire(String key, long seconds) {
		Duration d = Duration.ofSeconds(seconds);
		this.stringTemplate.expire(key, d);
	}

	// methods without template interface
	/*public Long incr(String key) {
		return this.getConnection().serverCommands().incr(key.getBytes());
	}

	public Boolean setBit(String key, int offset, boolean value) {
		return this.getConnection().serverCommands().setBit(key.getBytes(), offset, value);
	}
	
	public Boolean getBit(String key, int offset) {
		return this.getConnection().serverCommands().getBit(key.getBytes(), offset);
	}
	
	public Long bitCount(String key) {
		return this.getConnection().serverCommands().bitCount(key.getBytes());
	}*/
	
	public void flushDb() {
		this.getConnection().serverCommands().flushDb();
	}
}