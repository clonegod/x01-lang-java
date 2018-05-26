package jvm.classloader;

public class SomeClass {
	
	static {
		System.out.println("SomeClass class loaded");
	}
	
	private String attr;

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	
}
