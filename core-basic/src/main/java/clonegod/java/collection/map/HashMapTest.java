package clonegod.java.collection.map;

import java.util.HashMap;
/**
 * hashmap底层实现原理：
 * 	内部采用1个数组进行entry的存储
 * 		---> 数组：transient Node<K,V>[] table;
 * 
 * 	put(key,value)
 * 	每次进行put时，会根据key计算其hashcode，然后用hashcode换算出1个值作为数组的角标
 * 	接着，判断该角标上是否已经有元素存在：	
 * 		如果没有，则直接存入；
 * 		如果已有，则判断key是否相同（调用复写后的equals方法）
 * 			如果key相同，则直接覆盖；
 * 			否则，会把新值赋值给已存在的那个Node的next属性
 * 
 *  get(key)
 * 	首先，计算key的hashcode，转换得到数组的角标
 * 		如果数组中该角标有值，判断hashcode是否相同&&key是否相同
 * 			如果相同，则返回
 * 			否则，通过当前结点的next属性，继续遍历后续的结点，直到找到符合条件的结点并返回。
 * 			如果next遍历都最后都没有，则返回空。
 * 		
 * =====================================================================
 * 
 * 某个对象若要作为HashMap的key，必须同时重写hashcode与equals方法。
 * 因为，HashMap底层会通过hashcode计算数组角标，而且会通过equals方法判断key是否相同！
 * 
 * @author Administrator
 *
 */
public class HashMapTest {
	
	public static void main(String[] args) {
		test01(); // 测试key的hashcode相同且equals返回true
		
		test02(); // 测试key的hashcode相同且equals返回false
	}

	/**
	 *  模拟key的hashcode重复，且equals返回true的情况
	 */
	private static void test01() {
		HashMap map = new HashMap();
		
		T t1 = new T("t1");
		T t2 = new T("t2");
		
		Object o1 = map.put(t1, "t1111");
		Object o2 = map.put(t2, "t2222");
		
		System.out.println(map.get(t1));
		System.out.println(map.get(t2));
	}
	
	/**
	 *  模拟key的hashcode重复，且equals返回false的情况
	 */
	private static void test02() {
		HashMap map = new HashMap();
		
		Q q1 = new Q("q1");
		Q q2 = new Q("q2");
		
		Object o1 = map.put(q1, "q1111");
		Object o2 = map.put(q2, "q2222");
		
		System.out.println(map.get(q1));
		System.out.println(map.get(q2));
	}
}


class T {
	
	String name;
	
	public T(String name) {
		this.name = name;
	}
	
	// 返回固定hashcode
	@Override
	public int hashCode() {
		return 1;
	}

	// 返回true
	@Override
	public boolean equals(Object obj) {
		return true;
	}
	
}


class Q {
	
	String name;
	
	public Q(String name) {
		this.name = name;
	}
	
	// hashcode固定返回
	@Override
	public int hashCode() {
		return 1;
	}

	// equals按默认方式进行比较
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Q other = (Q) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
