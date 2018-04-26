package prototype.deepClone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 深复制Person
 *	内部引用的其它对象如果不需要复制，则用transient标识，排除在复制过程外
 * 	否则，其它被引用到的对象都需要实现Serializable接口，否则无法完成整个序列化该过程
 */
public class Person implements Serializable {
	String name;
	int age;
	Address address; 
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", address=" + address
				+ "]";
	}
	
	// 神复制
	public Person cloneMeDeep() {
		try {
			// 将对象写到流里-冷藏
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(this);
			
			// 重新读回来-解冻
			ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(bi);
			return (Person) oi.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
