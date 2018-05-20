package EnumMap;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * EnumMap
 *	使用场景：当Map的key是固定由某些字符串组成时，可通过枚举作为key，避免put和get的时候使用字符串作为key了。
 */
public class EnumMapBasicUseExample {
	
	public enum Days {
		Monday, Tuesday;
	}

	public static void main(String args[]) {

		System.out.println("\n--------1. Create daysEnumMap -----------");
		Map<Days, String> daysEnumMap = new EnumMap<Days, String>(Days.class);

		/**
		 * All of the keys in an enumMap must come from a single enum type that is
		 * specified at declaration
		 */
		System.out.println("\n--------2. Put key-value in daysEnumMap ");
		daysEnumMap.put(Days.Monday, "Day1");
		daysEnumMap.put(Days.Tuesday, "Day2");

		System.out.println("\n--------3. get method of daysEnumMap -----------");
		System.out.println("daysEnumMap.get(Days.Monday) : " + daysEnumMap.get(Days.Monday));

		System.out.println("\n--------4. contains method of daysEnumMap -----------");
		System.out.println("daysEnumMap.containsKey(Days.Monday) : " + daysEnumMap.containsKey(Days.Monday));

		System.out.println("\n--------5. Print daysEnumMap -----------");
		System.out.println(daysEnumMap);

		System.out.println("\n--------6. Print size of daysEnumMap -----------");
		System.out.println("Size of daysEnumMap : " + daysEnumMap.size());

		System.out.println("\n--------7. Iterate over keys in daysEnumMap -----------");
		Iterator<Days> keySet = daysEnumMap.keySet().iterator();
		while (keySet.hasNext()) {
			Days key = keySet.next();
			System.out.println(key);
		}

		System.out.println("\n--------8. Iterate over values in daysEnumMap -----------");
		Iterator<String> values = daysEnumMap.values().iterator();
		while (values.hasNext()) {
			System.out.println(values.next());
		}

		System.out.println("\n--------9. Iterate over entry in daysEnumMap -----------");
		Iterator<Entry<Days, String>> entrySet = daysEnumMap.entrySet().iterator();
		while (entrySet.hasNext()) {
			System.out.println(entrySet.next());
		}

		System.out.println("\n--------10. remove method of daysEnumMap -----------");
		// remove method removes entry with specified key from daysEnumMap
		// and returns value corresponding to specified key.
		System.out.println("daysEnumMap.remove(Days.Monday) : " + daysEnumMap.remove(Days.Monday));

		System.out.println("\n--------11. synchronizing daysEnumMap -----------");
		Map<Days, String> map = Collections.synchronizedMap(new EnumMap<Days, String>(daysEnumMap));
		System.out.println(map);
	}

}