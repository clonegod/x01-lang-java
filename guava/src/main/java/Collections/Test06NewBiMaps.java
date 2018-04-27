package Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;

/**
 * Bimaps, which are maps where we can navigate from values to keys as well as the traditional key-to-value navigation
 * 
 * A BiMap<K, V> is a Map<K, V> that
 * 		allows you to view the "inverse" BiMap<V, K> with inverse()
 * 		ensures that values are unique, making values() a Set
 * 
 * An extension to Map interface to support inverse operations. 
 * 
 * 双向map映射
 * 	key -> value
 * 	inverse: value -> key
 * 
 * 限制：1个value只能被同一个key所关联，如果被不同的key所关联，则报参数异常：value already present：XXX
 */
public class Test06NewBiMaps {
	
	@Test
	public void test1() {
		BiMap<String, Integer> userId = HashBiMap.create();
		
		userId.put("alice", 1);
		userId.put("bob", 2);
		
		String userId1 = userId.inverse().get(1);
		System.out.println(userId1);
		
		String userId2 = userId.inverse().get(2);
		System.out.println(userId2);
	}
	
	@Test
	public void test2() {
		BiMap<String, Integer> userId = HashBiMap.create();
		
		userId.put("alice", 1);
		try {
			userId.put("bob", 1);
		} catch (Exception e) {
			System.err.println("throw an IllegalArgumentException if you attempt to map a key to an already-present value");
		}
		
		userId.forcePut("bob", 1);
		
		String userId1 = userId.inverse().get(1);
		System.out.println(userId1);
	}
	
	enum CITY {
		GZ, SZ
	}
	
	@Test
	public void test03() {
		EnumHashBiMap<CITY, String> enumBiMap = EnumHashBiMap.create(CITY.class);
		enumBiMap.put(CITY.GZ, "4401");
		enumBiMap.put(CITY.SZ, "4403");

		System.out.println(enumBiMap);
		
		assertEquals(enumBiMap.get(CITY.GZ), "4401");
		
		assertTrue(enumBiMap.inverse().get("4401") == CITY.GZ);
	}
	
	public static void main(String[] args) {
		BiMap<Integer, String> empIDNameMap = HashBiMap.create();
		empIDNameMap.put(new Integer(101), "Mahesh");
		empIDNameMap.put(new Integer(102), "Sohan");
		empIDNameMap.put(new Integer(103), "Ramesh");
		
//		 empIDNameMap.put(new Integer(104), "Mahesh"); // Mahesh 不能关联到不同的key上
		
		//Emp Id of Employee "Mahesh"
		System.out.println(empIDNameMap.inverse().get("Mahesh"));
	}
}
