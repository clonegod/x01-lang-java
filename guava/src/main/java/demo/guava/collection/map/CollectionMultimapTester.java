package demo.guava.collection.map;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * An extension to Map interface so that its keys can be mapped to multiple values at a time.
 * 
 * 在内部维护了一个集合来存储key相同的元素
 * map.put("key1", 1)
 * map.put("key1", 2)
 * map.put("key1", 3)
 * 
 * 类似
 * map.put("key1", Arrays.asList(1,2,3))
 *
 *
 */
public class CollectionMultimapTester {
	
	public static void main(String[] args) {
		CollectionMultimapTester tester = new CollectionMultimapTester();
		
		Multimap<String, String> multimap = tester.getMultimap();
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

	private Multimap<String, String> getMultimap() {
		// Map<String, List<String>>
		// lower: a, b, c, d, e
		// upper: A, B, C, D
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
