package jvm.classloader3;

public class ChangeableClass {
	
	public static void doit(int version) {
		System.out.println(Thread.currentThread().getName() + "=>Version_" + version);
	}
	
}
