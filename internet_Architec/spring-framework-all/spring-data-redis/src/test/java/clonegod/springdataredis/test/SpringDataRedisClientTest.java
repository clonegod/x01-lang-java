package clonegod.springdataredis.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 2.基于spring-data-redis 基于jedis来实现对五种数据类型操作，每种数据类型实现两个操作，包括事务
 * 
 * @author clonegod
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springapp.xml")
public class SpringDataRedisClientTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Before
	public void setUp() {
		// 清空redis数据库
		redisTemplate.getConnectionFactory().getConnection().flushAll();
		
		// 开启事务
		redisTemplate.setEnableTransactionSupport(true);
		
		// 指定序列化机制:解决存入reids后key前面增加额外前缀，如\xac\xed\x00\x05t\x00\x03
		redisTemplate.setEnableDefaultSerializer(false);
		
		RedisSerializer serializer = new StringRedisSerializer();
		//RedisSerializer serializer = new Jackson2JsonRedisSerializer<>(String.class);
		redisTemplate.setKeySerializer(serializer);
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setHashKeySerializer(serializer);
		redisTemplate.setHashValueSerializer(serializer);
		
	}
	
	/**
	 * string
	 * 简单的key-value存储
	 */
	@Test
	public void testValueOperations() {
		
		// multiGet
		redisTemplate.multi();
		
		redisTemplate.opsForValue().set("name", "zhangsan");
		redisTemplate.opsForValue().set("age", "20");
		
		redisTemplate.exec();
		
		Set<String> keys = new HashSet<>();
		keys.add("name");
		keys.add("age");
		
		List<String> values = redisTemplate.opsForValue().multiGet(keys);
		assertTrue(values.contains("zhangsan"));
		assertTrue(values.contains("20"));
		
		// increment
		redisTemplate.multi();
		
		redisTemplate.opsForValue().set("id", "1");
		redisTemplate.opsForValue().increment("id", 1L);
		
		redisTemplate.exec();
		
		Object value = redisTemplate.opsForValue().get("id");
		assertEquals("2", value);
		
	}
	
	/**
	 * list
	 * 队列	lpush rpop / rpush lpop
	 * 堆栈	lpush lpop / rpush rpop
	 */
	@Test
	public void testListOperations() {
		ListOperations listOps = redisTemplate.opsForList();
		
		String[] values = {"1","2","3","4"};
		
		// 队列-先进先出
		listOps.leftPushAll("key1", values);
		
		List<String> retRanged = listOps.range("key1", 0, 3);
		assertEquals("[4, 3, 2, 1]", Arrays.toString(retRanged.toArray()));
		
		assertEquals("1", listOps.rightPop("key1"));
		assertEquals("2", listOps.rightPop("key1"));
		assertEquals("3", listOps.rightPop("key1"));
		assertEquals("4", listOps.rightPop("key1"));
		
		
		// 堆栈-先进后出
		listOps.rightPushAll("key2", values);
		
		List<String> retRanged2 = listOps.range("key2", 0, 3);
		assertEquals("[1, 2, 3, 4]", Arrays.toString(retRanged2.toArray()));
		
		assertEquals("4", listOps.rightPop("key2"));
		assertEquals("3", listOps.rightPop("key2"));
		assertEquals("2", listOps.rightPop("key2"));
		assertEquals("1", listOps.rightPop("key2"));
		
	}
	
	/**
	 * set
	 * 排重集合
	 */
	@Test
	public void testSetOperations() {
		SetOperations<String, String> setOps = redisTemplate.opsForSet();
		
		String[] values = {"22","33","22","44","55","66"};
		setOps.add("key1", values);
		
		long size = setOps.size("key1");
		assertEquals(size,5);

		setOps.remove("key1", "22");
		assertFalse(setOps.isMember("key1", "22"));
	}
	
	/**
	 * zset
	 * 排重集合，并基于key的分数的排序。
	 * 默认升序排列，分数小的排前面。
	 */
	@Test
	public void testZSetOperations() {
		redisTemplate.multi();
		
		redisTemplate.opsForZSet().add("1", "11", 10d);
		redisTemplate.opsForZSet().add("1", "22", 9d);
		redisTemplate.opsForZSet().add("1", "33", 14d);
		redisTemplate.opsForZSet().add("1", "44", 6d);
		redisTemplate.opsForZSet().add("1", "55", 50d);
		
		redisTemplate.exec();
		
		Set<String> rangedatas = redisTemplate.opsForZSet().range("1", 0L, 1L);
		assertTrue(rangedatas.contains("44"));
		
		redisTemplate.opsForZSet().add("1", "44", 40);
		Set<String> rangedatas1 = redisTemplate.opsForZSet().range("1", 0L, 1L);
		assertFalse(rangedatas1.contains("44"));
		
		Set<String> datasbyScore = redisTemplate.opsForZSet().rangeByScore("1", 40d, 50d);
		
		assertEquals(datasbyScore.size(),2);//44和55
		assertTrue(datasbyScore.contains("44"));
		assertTrue(datasbyScore.contains("55"));
	}
	
	/**
	 * hash
	 * key-hashKey1-value1
	 * key-hashKey2-value2
	 * ...
	 */
	@Test
	public void testHashOperations() {
		Map<String,String> user = new HashMap<String,String>();
		user.put("id", "111");
		user.put("name", "liubx");
		user.put("money", "10000");
		user.put("other", "other");
		
		HashOperations hashOps = redisTemplate.opsForHash();
		hashOps.putAll("firstKey", user);
		
		Map<String,String> userget = hashOps.entries("firstKey");
		assertEquals(userget.get("name"),"liubx");
		
		
		hashOps.increment("firstKey", "money", 100);
		String money = (String) hashOps.get("firstKey", "money");
		assertEquals(Long.parseLong(money),10100L);
		
		String otherValue = (String) hashOps.get("firstKey", "other");
		assertEquals(otherValue,"other");
		
		hashOps.delete("firstKey", "other");
		String otherValue2 = (String) hashOps.get("firstKey", "other");
		assertNull(otherValue2);
	}
	
}
