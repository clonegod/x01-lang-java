package clonegod.framework.test.rediscluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.WebApplicationContext;

import clonegod.framework.test.WebBaseTest;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.JedisClusterCRC16;

@Slf4j
public class RedisClusterTest extends WebBaseTest {
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Test
	public void testAutowire() {
		log.info("{}", wac);
		log.info("{}", redisTemplate);
		log.info("application annotation config success");
	}
	
	@Test
	public  void testKeySlot() throws IOException{
	    String key = "1417";
	    Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
	    jedisClusterNodes.add(new HostAndPort("192.168.1.101", 7000));   
	    JedisCluster jc = new JedisCluster(jedisClusterNodes);

	    jc.setnx(key, "bar");
	    String value = jc.get(key);
	    System.out.println("key-"+key+" slot-"+JedisClusterCRC16.getSlot(key)+" value-"+value);

	    String key2 = "288";
	    jc.setnx(key2, "bar2");
	    String value2 = jc.get(key);
	    System.out.println("key-"+key2+" slot-"+JedisClusterCRC16.getSlot(key2)+" value-"+value2);
	    
	    jc.close();
	}

}
