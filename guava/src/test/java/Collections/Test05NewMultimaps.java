package Collections;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

/**
 * Multimap - mapping from keys to multiple values
 * 	implemented a Map<K, List<V>> or Map<K, Set<V>>
 * 	An extension to Map interface so that its keys can be mapped to multiple values at a time.
 * 
 * 在内部维护了一个集合来存储key相同的元素
 * map.put("key1", 1)
 * map.put("key1", 2)
 * map.put("key1", 3)
 * 
 * 类似
 * map.put("key1", Arrays.asList(1,2,3))
 *
 * Multimaps, which allow us to have more than one value associated with a unique key
 */
public class Test05NewMultimaps {

	@Test
	public void test() {
		// creates a ListMultimap with tree keys and array list values
		ListMultimap<String, Integer> treeListMultimap =
		    MultimapBuilder.treeKeys().arrayListValues().build();

		treeListMultimap.put("a", 1);
		treeListMultimap.put("a", 2);
		treeListMultimap.put("b", 3);
		treeListMultimap.put("c", 4);
		
		Collection<Integer> aList = treeListMultimap.asMap().get("a");
		System.out.println("value: " + aList);
		
		System.out.println(treeListMultimap.keys().size());
		System.out.println(treeListMultimap.entries().size());
		System.out.println(treeListMultimap.keySet().size()); // the number of distinct keys
	}
	
	public static void main(String[] args) {
		Multimap<String, String> multimap = getMultimap();
		List<String> lowerList = (List<String>) multimap.get("lower");
		System.out.println("\nInitial lower case list");
		System.out.println(lowerList.toString());
		lowerList.add("f");
		System.out.println("Modified lower case list");
		System.out.println(lowerList.toString());
		
		List<String> upperList = (List<String>) multimap.get("upper");
		System.out.println("\nInitial upper case list");
		System.out.println(upperList.toString());
		upperList.remove("D");
		System.out.println("Modified upper case list");
		System.out.println(upperList.toString());
		
		Map<String, Collection<String>> map = multimap.asMap();
		System.out.println("\nMultimap as a map");
		for (Map.Entry<String, Collection<String>> entry : map.entrySet()) {
			String key = entry.getKey();
			Collection<String> value = multimap.get(key);
			System.out.println(key + ":" + value);
		}
		
		System.out.println("\nKeys of Multimap");
		Set<String> keys = multimap.keySet();
		for (String key : keys) {
			System.out.println(key);
		}
		
		System.out.println("\nValues of Multimap");
		Collection<String> values = multimap.values();
		System.out.println(values);
	}

	private static Multimap<String, String> getMultimap() {
		// Map<String, List<String>>
		// lower -> a, b, c, d, e
		// upper -> A, B, C, D
		Multimap<String, String> multimap = ArrayListMultimap.create();
		multimap.put("lower", "a");
		multimap.put("lower", "b");
		multimap.put("lower", "c");
		multimap.put("lower", "d");
		multimap.put("lower", "e");
		multimap.put("upper", "A");
		multimap.put("upper", "B");
		multimap.put("upper", "C");
		multimap.put("upper", "D");
		return multimap;
	}

}
