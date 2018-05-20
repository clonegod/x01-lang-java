package IdentityHashMap;

import java.util.IdentityHashMap;
import java.util.Map;

class Emp {
	String name;

	Emp(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		System.out.println("in hashCode() will not execute");
		return (this.name == null ? 0 : this.name.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println("in equals() will not execute");
		Emp emp = (Emp) obj;
		return (emp.name == this.name || emp.name.equals(this.name));
	}

}

/**
 * show overridden equals() and hashCode() method are not called while storing object in IdentityHashMap -
 *
 */
public class IdentityHashMapExample2 {
	public static void main(String args[]) {

		Map<Emp, String> identityHashMap = new IdentityHashMap<Emp, String>();

		identityHashMap.put(new Emp("a"), "audi");
		identityHashMap.put(new Emp("a"), "bmw");

		System.out.println(">>>>>>>>>>>>>> size = " + identityHashMap.size());
		System.out.println(">>>>>>>>>>>>>> identityHashMap.get(new Emp(\"a\")) = " + identityHashMap.get(new Emp("a")));

	}

}