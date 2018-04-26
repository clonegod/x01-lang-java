package clonegod.model;

import java.io.Serializable;

public class SuperUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6946289258912177618L;
	
	private int age;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "SuperUser [age=" + age + "]";
	}
	
}
