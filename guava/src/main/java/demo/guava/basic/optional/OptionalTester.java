package demo.guava.basic.optional;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

/**
 * Optional - 避免NullPointerexception
 *
 */
public class OptionalTester {
	public static void main(String[] args) {
		
		OptionalTester guavaTester = new OptionalTester();
		
		Integer value1 = null;
		Integer value2 = new Integer(10);
		
		Optional<Integer> a = Optional.fromNullable(value1);
		Optional<Integer> b = Optional.of(value2);
		
		System.out.println(guavaTester.sum(a, b));
		
	}

	private int sum(Optional<Integer> a, Optional<Integer> b) {
		System.out.println("First paramter is present: " + a.isPresent());
		System.out.println("Second paramter is present: " + b.isPresent());
		
		Integer value1 = a.or(new Integer(0));
		Integer value2 = b.get();
		
		return value1 + value2;
	}
	
	@Test
	public void isPresent() {
		Optional<Integer> op1 = Optional.absent();
		System.out.println(op1.isPresent());
	}
	
	/**
	 * 赋值
	 */
	@Test
	public void of() {
		Optional<Integer> value1 = Optional.absent();
		System.out.printf("present=%s, value=%s\n", value1.isPresent(), value1.orNull());
		
		Optional<Integer> value2 = Optional.fromNullable(null);
		System.out.printf("present=%s, value=%s\n", value2.isPresent(), value2.orNull());
		
		Optional<Integer> value3 = Optional.of(new Integer(1));
		System.out.printf("present=%s, value=%s\n", value3.isPresent(), value3.orNull());
	}
	
	/**
	 * 取值
	 */
	@Test
	public void or() {
		Optional<Integer> op1 = Optional.absent();
		
		Integer value1 = op1.orNull();
		System.out.println(value1);
		
		Integer value2 = op1.or(new Integer(1));
		System.out.println(value2);
		
		Optional<Integer> value3 = op1.or(Optional.of(new Integer(2)));
		System.out.println(value3.get());
		
		Integer value4 = op1.or(new Supplier<Integer>() {
			@Override
			public Integer get() {
				return new Integer(3);
			}
		});
		System.out.println(value4);
	}
	
	@Test
	public void asSet() {
		Optional<Integer> op1 = Optional.absent();
		System.out.println(op1.asSet());
		
		Optional<Integer> op2 = op1.or(Optional.of(1));
		
		System.out.println(op2.asSet());
	}
	
	
}
