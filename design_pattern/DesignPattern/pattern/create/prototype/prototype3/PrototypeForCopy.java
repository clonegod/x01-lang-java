package prototype3;

import org.junit.Test;

/**
 * 原型模式：
 * 	浅复制
 * 	深复制
 * 
 * 原型模式，该模式的思想就是将一个对象作为原型，对其进行复制、克隆，产生一个和原对象"类似"的新对象。
 * 
 */
public class PrototypeForCopy {
	/**
	 * 浅复制：将一个对象复制后，基本数据类型的变量都会重新创建，而引用类型，指向的还是原对象所指向的。
	 */
	@Test
	public void testShallowCopy() throws Exception {
		Person p = new Person("pppp", new Address("street1", "a01"));
		
		System.out.println(p.toString());
		
		Person p2 = (Person) p.cloneMe();
		p2.address.doorNo = "b---01";
		System.out.println(p.toString());
		System.out.println(p2.toString());
	}
	
	/**
	 * 深复制：将一个对象复制后，不论是基本数据类型还有引用类型，都是重新创建的。
	 * 简单来说，就是深复制进行了完全彻底的复制，而浅复制不彻底。
	 */
	@Test
	public void deepCopy() throws Exception {
		Person p = new Person("pppp", new Address("street1", "a01"));
		
		System.out.println(p.toString());
		
		Person p2 = CloneUtils.deepCopy(p);
		p2.name = "p2p2p2";
		p2.address.doorNo = "b---01";
		System.out.println(p.toString());
		System.out.println(p2.toString());
	}
	
}

