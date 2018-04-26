package clonegod.serialize01.jdk.clone;

import java.io.Serializable;

public class Teacher implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8105489734000073906L;
	private String name;
	
	public Teacher() {
		super();
	}

	public Teacher(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Teacher [name=" + name + "]";
	}
	
	
	
}
