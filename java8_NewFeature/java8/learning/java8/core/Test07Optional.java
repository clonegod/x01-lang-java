package java8.core;

import java.util.Optional;

import org.junit.Test;

import com.google.common.base.Preconditions;

/**
 * Optional is a container object to handle values as ‘available’ or ‘not  available’ instead of checking null values.
 *
 */
public class Test07Optional {
	/**
	 * Optionals are not functional interfaces, instead it's a nifty utility to prevent NullPointerException. 
	 * 
	 * Optional is a simple container for a value which may be null or non-null. 
	 * Think of a method which may return a non-null result but sometimes return nothing. 
	 * Instead of returning null you return an Optional in Java 8.
	 * 
	 */
	@Test
	public void test() {
		Optional<String> optional = Optional.of("bam");

		optional.isPresent();           // true
		optional.get();                 // "bam"
		optional.orElse("fallback");    // "bam"

		optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
	}
	
	
	public static String badFind() {
		return Math.random() > 0.5 ? "hello" : null;
	}
	
	// 如果返回值可能为null，最好使用Optional包装结果：强制调用方对结果为null的情况进行处理
	public static Optional<String> goodFind() {
		String result = Math.random() > 0.5 ? "hello" : null;
		return Optional.ofNullable(result);
	}
	
	@Test
	public void test1() {
		String value = badFind();
		System.out.println(value.toLowerCase());
	}
	
	@Test
	public void test2() {
		Optional<String> value = goodFind();
		if(value.isPresent()) {
			System.out.println(value.get().toLowerCase());
		} else {
			System.out.println("Not Fount");
		}
	}
	
	
	public static Integer sum(Optional<Integer> a, Optional<Integer> b) {
		// Optional.isPresent - checks the value is present or not
		Preconditions.checkArgument(b.isPresent(), "Optional<Integer> must contains non null value");

		System.out.println("First parameter is present: " + a.isPresent());
		System.out.println("Second parameter is present: " + b.isPresent());

		// Optional.orElse - returns the value if present otherwise returns
		// the default value passed.
		Integer value1 = a.orElse(new Integer(0));

		// Optional.get - gets the value, value should be present
		Integer value2 = b.get();
		return value1 + value2;
	}
	
	public static void main(String args[]) {
		Integer value1 = null;
		Integer value2 = new Integer(10);

		// Optional.ofNullable - allows passed parameter to be null.
		Optional<Integer> a = Optional.ofNullable(value1);

		// Optional.of - throws NullPointerException if passed parameter is null
		Optional<Integer> b = Optional.of(value2);
		
		// sum two Optional variable
		System.out.println(sum(a, b));
	}
}
