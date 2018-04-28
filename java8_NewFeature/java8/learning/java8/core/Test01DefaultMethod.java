package java8.core;

import java.util.Locale;

import org.junit.Test;

public class Test01DefaultMethod {
	
	@Test
	public void test() {
		Action action = (name, locale) -> {
			if(locale == Locale.CHINESE) {
				System.out.println("你好:" + name);
			} else {
				System.out.println("Hello:" + name);
			}
		};
		
		action.sayHello("tomcat", Locale.CANADA);
		action.sayHello("tomcat");
		Action.sayHello();
		
	}
	
}


@FunctionalInterface
interface Action {
	void sayHello(String name, Locale local);
	
	// FunctionalInterface中可以定义默认方法
	default void sayHello(String name) {
		System.out.println("你好：" + name);
	}
	
	static void sayHello() {
		System.out.println("你好");
	}
}
