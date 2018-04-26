package prototype3;

import java.io.Serializable;

class Person implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -2694068503475688170L;
	
	public String name;
	public Address address;

	public Person(String name, Address address) {
		super();
		this.name = name;
		this.address = address;
	}

	public Person cloneMe() throws CloneNotSupportedException {
		return (Person) super.clone();
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", address=" + address + "]";
	}
}