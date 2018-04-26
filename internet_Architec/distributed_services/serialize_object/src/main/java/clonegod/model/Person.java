package clonegod.model;

import java.io.Serializable;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

public class Person implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9132906942908893801L;

	@Protobuf(fieldType=FieldType.STRING, order=1)
	private String name;
	
	@Protobuf(fieldType=FieldType.INT32, order=2)
	private int age;
	
	private transient String password; // transient 不参与序列化
	
	public static String USER_TYPE = "VIP"; // 静态static 不参与序列化

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", password=" + password + "]";
	}

	
}
