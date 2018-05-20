package IdentityHashMap;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * show comparing keys (and values) performs reference-equality in place of object-equality. 
 * In an IdentityHashMap, two keys k1 and k2 are equal if and only if (k1==k2)
 *
 */
public class IdentityHashMapExample1 {
	public static void main(String args[]) {

		Map<String, String> identityHashMap = new IdentityHashMap<String, String>();

		String a1 = new String("a");
		String a2 = new String("a");
		
		System.out.println(a1 == a2); // reference-equality
		
		identityHashMap.put(a1, "audi");
		identityHashMap.put(a2, "ferrari");

		System.out.println(identityHashMap);
		
		// -------------------------------------------------//
		
		String b1 = "b";
		String b2 = "b";
		
		System.out.println(b1 == b2); // reference-equality
		
		identityHashMap.put(b1, "audi");
		identityHashMap.put(b2, "ferrari");
		
		System.out.println(identityHashMap);
		

	}

}