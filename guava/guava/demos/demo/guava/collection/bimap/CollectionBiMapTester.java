package demo.guava.collection.bimap;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * An extension to Map interface to support inverse operations. 
 * 
 * 双向map映射
 * 	key -> value
 * 	inverse: value -> key
 * 
 * 限制：1个value只能被同一个key所关联，如果被不同的key所关联，则报参数异常：value already present：XXX
 */
public class CollectionBiMapTester {
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
