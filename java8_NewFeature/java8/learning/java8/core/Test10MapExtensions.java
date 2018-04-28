package java8.core;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.junit.Test;

import com.google.common.collect.Maps;

public class Test10MapExtensions {

	/**
	 * As already mentioned maps don't support streams. 
	 * 
	 * Instead maps now support various new and useful methods for doing common tasks.
	 * 
	 */
	
	
	@Test
	public void test() {
		Map<Integer, String> map = new HashMap<>();

		for (int i = 0; i < 10; i++) {
		    map.putIfAbsent(i, "val" + i);
		}

		// forEach accepts a consumer to perform operations for each value of the map.
		map.forEach((id, val) -> System.out.println(val));
		
		
		
		// learn how to compute code on the map by utilizing functions
		map.computeIfPresent(3, (num, val) -> val + num);
		map.get(3);             // val33

		map.computeIfPresent(9, (num, val) -> null);
		map.containsKey(9);     // false

		map.computeIfAbsent(23, num -> "val" + num);
		map.containsKey(23);    // true

		map.computeIfAbsent(3, num -> "bam");
		map.get(3);             // val33
		
		
		
		// learn how to remove entries for a a given key, only if it's currently mapped to a given value:
		map.remove(3, "val3");
		map.get(3);             // val33

		map.remove(3, "val33");
		map.get(3);             // null
		
		
		// Another helpful method:
		map.getOrDefault(42, "not found");  // not found
		
		
		
		// Merging entries of a map is quite easy:
		map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
		map.get(9);             // val9

		map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
		map.get(9);             // val9concat
		
	}
	
	
	/*
		 V oldValue = map.get(key);
		 V newValue = (oldValue == null) ? value :
		              remappingFunction.apply(oldValue, value);
		 if (newValue == null)
		     map.remove(key);
		 else
		     map.put(key, newValue);
	 */
	/**
	 * Merge either put the key/value into the map if no entry for the key exists, or the merging function will be called to change the existing value.
	 * 		不存在，设置给定值；
	 * 		已存在，则调用function进行值的合并。
	 */
	@Test
	public void testMapMerge() {
		Map<Integer, String> map = Maps.newConcurrentMap();
		
		// BiFunction<T, U, R>  T U R 分别表示接收的2个参数的类型，1个返回值的类型
		
		// oldValue == null , set value
		map.merge(404, "NotFound", new BiFunction<String, String, String>() {
			@Override
			public String apply(String oldValue, String newValue) {
				return null;
			}
		});
		
		System.out.println(map);
		
		// oldValue != null , invoke biFunciton , return null , so remove key
		map.merge(404, "NotFound", new BiFunction<String, String, String>() {
			@Override
			public String apply(String oldValue, String newValue) {
				return null;
			}
		});
		
		System.out.println(map);
		
		//  oldValue == null , put value
		map.merge(404, "NotFound", new BiFunction<String, String, String>() {
			@Override
			public String apply(String oldValue, String value) {
				return oldValue + "-" + value;
			}
		});
		
		System.out.println(map);
		
		// oldValue != null , invoke biFunciton , return newValue , put newValue
		map.merge(404, "页面不存在", new BiFunction<String, String, String>() {
			@Override
			public String apply(String oldValue, String value) {
				return oldValue + " - " + value;
			}
		});
		
		System.out.println(map);
		
	}
	
	
}
