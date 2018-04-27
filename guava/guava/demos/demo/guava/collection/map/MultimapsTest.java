package demo.guava.collection.map;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultimap;


public class MultimapsTest {
	/**
	 * ArrayListMulitmap is a map that uses ArrayList to store the values for the given key. 
	 * 
	 * Support same key-value pair	支持重复存放相同键值对
	 */
	@Test
	public void testArrayListMultimap(){
		ArrayListMultimap<String,String> multiMap = ArrayListMultimap.create();
		multiMap.put("Bar","1");
		multiMap.put("Bar","2");
		multiMap.put("Bar","3");
		multiMap.put("Bar","3");
		multiMap.put("Bar","3");
		List<String> expected = Lists.newArrayList("1","2","3","3","3");
		assertEquals(multiMap.get("Bar"), expected);
		
		System.out.println(multiMap.size());
		System.out.println(multiMap.values());
		System.out.println(multiMap.asMap());
	}
	/**
	 * 不可变的ListMultimap
	 */
	@Test
	public void testImmutableListMultimap() {
		ImmutableMultimap<String, Integer> map1 = 
				ImmutableListMultimap.of("A", 65,"A", 65, "B", 66);
		System.out.println(map1);
		
		ImmutableMultimap<Object, Object> map2 = 
				ImmutableListMultimap.builder().put("A", 65).put("A", 65).put("B", 66).build();
		System.out.println(map2);
	}
	
	
	/**
	 * HashMultimap is based on hash tables. 
	 * 
	 * Ignore duplicated key-value pair	不支持重复存放相同键值对
	 */
	@Test
	public void testHashMultimap() {
		HashMultimap<String,String> multiMap = HashMultimap.create();
		multiMap.put("Bar","1");
		multiMap.put("Bar","2");
		
		// only distinct key-value pairs are kept.
		multiMap.put("Bar","3");
		multiMap.put("Bar","3");
		multiMap.put("Bar","3");
		
		assertEquals(multiMap.get("Bar").size(), 3);
		
		HashSet<String> expected = Sets.newHashSet("1","2","3");
		assertEquals(multiMap.get("Bar"), expected);
	}
	
	/**
	 * 不可变的SetMultimap
	 */
	@Test
	public void testImmutableSetMultimap() {
		ImmutableMultimap<String, Integer> map = 
				ImmutableSetMultimap.of("A", 65, "A", 65, "B", 66);
		System.out.println(map);
	}
	
	/**
	 * 按添加顺序输出元素，丢弃重复的key-value pair
	 */
	@Test
	public void testLinkedHashMultimap() {
		LinkedHashMultimap<String, Integer> map = 
				LinkedHashMultimap.create(3, 2);
		map.put("z", 4);
		map.put("x", 1);
		map.put("x", 1);
		map.put("x", 2);
		map.put("y", 3);
		System.out.println(map.keys());
		System.out.println(map.keySet());
		System.out.println(map);
	}
	
	/**
	 * TreeMultimap that keeps the keys and values 
	 * sorted by their natural order or the order specifed by a comparator.
	 * 
	 * Comparator: key-ordering
	 * Comparator: value-ordering
	 */
	@Test
	public void testTreeMultimap() {
		Comparator<String> keyComparator = (x, y) -> { return x.compareTo(y); };
		Comparator<Integer> valueComparator = (x, y) -> { return y.compareTo(x); };
		TreeMultimap<String, Integer> map = TreeMultimap.create(keyComparator, valueComparator);
		map.put("x", 1);
		map.put("y", 2);
		map.put("z", 3);
		map.put("x", 11);
		map.put("y", 22);
		map.put("z", 33);
		
		System.out.println(map);
	}
}	
