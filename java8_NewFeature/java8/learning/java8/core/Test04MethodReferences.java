package java8.core;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class Test04MethodReferences {
	
	/**
	 * Static methods
	 * Instance methods
	 * Constructors using new operator (TreeSet::new)
	 */
	@Test
	public void test() {
		List<String> lists = Lists.newArrayList("abc", "123");
		
		lists.forEach(System.out::println); // Instance methods
		
		lists.forEach(Helper::log); // Static methods
		
	}
	
	static class Helper {
		static void log(String data) {
			System.err.println("=>"+data);
		}
	}
	
}
