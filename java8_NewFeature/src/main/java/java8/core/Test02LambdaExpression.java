package java8.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Lambda expression facilitates functional programming
 * Lambda 表达式便于实现函数编程
 * 
 * Syntax：
 * 	parameter -> expression body
 * 
 * Characteristics：
 * 	Optional type declaration				可选类型声明
 *  Optional parenthesis around parameter 	可选括号：仅有1个参数的情况
 *  Optional curly braces					可选的花括号：仅有1个statement时
 *  Optional return keyword					可选的return：无花括号且仅有一个statement，有花括号则必须显示return
 */
public class Test02LambdaExpression {
	
	@Test
	public void createFunctional() {
//		functional interfaces can be created with lambda expressions, method references, or constructor references. 
		
		Runnable r1 = () -> System.out.println("怎么回事？");
		new Thread(r1).start();
		
		List<String> list = Arrays.asList("abcdef".split(""));
		list.forEach(System.out::println);
		
	}
	
	@Test
	public void testRunnable() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("我是一个线程，我的名字叫:" + Thread.currentThread().getName());
			}
		}).start();
		
		new Thread(() -> {
			System.out.println("我是一个线程，我的名字叫:" + Thread.currentThread().getName());
		}).start();
	}
	
	@Test
	public void testComparator() {
		List<String> lists = Lists.newArrayList("Java", "SprintBoot", "Guava", "Jdk8");
		Collections.sort(lists, (s1, s2) -> s1.compareTo(s2));
		lists.forEach(System.out::println);
	}
	
	@Test
	public void testFuntional() {
		MathOperation addOper = (int x, int y) -> x + y;
		MathOperation substractOper = (x, y) -> x - y;
		MathOperation multipleOper = (int x, int y) -> { return x * y; };
		MathOperation divisionOper = (x, y) -> x / y;
		
		LogService.info("2+1="+operate(2, 1, addOper));
		LogService.info("2-1="+operate(2, 1, substractOper));
		
		new LogService(){}.error("2*1="+operate(2, 1, multipleOper));
		new LogService(){}.error("2/1="+operate(2, 1, divisionOper));
		
		
		GreetingService greetService1 = new GreetingService() {
			@Override
			public void sayMessage(String message) {
				System.out.println("Hello " + message);
			}
		};
		greetService1.sayMessage("你好");
		
	    GreetingService greetService2 = (message) -> System.out.println("Bye " + message);
	    greetService2.sayMessage("hello");
	    
	    GreetingService greetService3 = message -> System.out.println("Bye Bye " + message);
	    greetService3.sayMessage("bye");
	    
	}
	
	/** 将函数传入到方法中，函数可以理解为一段可复用的算法逻辑 */
	private int operate(int a, int b, MathOperation mathOperation) {
		return mathOperation.operation(a, b);
	}
	
	@FunctionalInterface
	interface MathOperation {
		int operation(int a, int b);
	}
	
	@FunctionalInterface
	interface GreetingService {
		void sayMessage(String message);
	}
	
	// 接口中的默认方法
	interface LogService {
		// 接口中的静态方法
		static void info(Object object) {
			System.out.println(object);
		}
		
		/**接口中声明默认方法，简化了以前只能在AbstractXXX子类中提供的空方法或默认方法实现*/
		default void error(Object object) {
			System.err.println(object);
		}
	}
}
