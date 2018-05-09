package net.jcip02.safety;

public class Widget {
	public synchronized void dosomething() {
		System.out.println("parent: this="+this);
	}
}
