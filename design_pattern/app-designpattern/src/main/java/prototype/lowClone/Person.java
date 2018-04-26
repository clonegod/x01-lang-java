package prototype.lowClone;

/**
 * 浅复制Person
 *
 */
public class Person implements Cloneable {
	String name;
	int age;
	Address address; // 浅复制，直接复制此引用，会造成不安全隐患
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", address=" + address
				+ "]";
	}
	
	// 浅复制-引用的其它对象不会被重新复制一份，而是直接用引用填充
	// 拿到克隆对象后，如果对这些引用内部属性进行了修改，则会影响到原型对象
	public Person cloneMe() {
		Person cloned = null;
		try {
			 cloned = (Person) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return cloned;
	}
	
}
