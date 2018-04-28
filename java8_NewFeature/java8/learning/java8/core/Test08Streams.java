package java8.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class Test08Streams {
	/**
	 * A java.util.Stream represents a sequence of elements on which one or more operations can be performed. 
	 * Stream operations are either intermediate / 中间操作 or terminal / 结束操作.
	 * 
	 * While terminal operations return a result of a certain type, 
	 * 	intermediate operations return the stream itself so you can chain multiple method calls in a row.
	 * 
	 * Streams are created on a source, e.g. a java.util.Collection like lists or sets (maps are not supported). 
	 * 
	 * Stream operations can either be executed sequential or parallel.
	 * 
	 */
	
	List<String> stringCollection = new ArrayList<>();
	
	@Before
	public void init() {
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		
	}
	
	/**
	 * Filter - Intermediate operation
	 * 
	 * Filter accepts a predicate to filter all elements of the stream. 
	 * This operation is intermediate which enables us to call another stream operation (forEach) on the result. 
	 * ForEach accepts a consumer to be executed for each element in the filtered stream. 
	 * ForEach is a terminal operation. It's void, so we cannot call another stream operation.
	 */
	@Test
	public void testStreamFilter() {
		stringCollection
			.stream()
			.filter((s) -> s.startsWith("a"))
			.forEach(System.out::println);
		
		// "aaa2", "aaa1"
	}
	
	
	
	/**
	 * Sorted - Intermediate operation
	 * 
	 * Sorted is an intermediate operation which returns a sorted view of the stream. 
	 * The elements are sorted in natural order unless you pass a custom Comparator.
	 * 
	 */
	@Test
	public void testSorted() {
		stringCollection
			.stream()
			.sorted()
			.filter((s) -> s.startsWith("a"))
			.forEach(System.out::println);
		
		// "aaa1", "aaa2"
		
		/** 
		 * Keep in mind that sorted does only create a sorted view of the stream without manipulating the ordering of the backed collection. 
		 * The ordering of stringCollection is untouched:
		 */
		System.out.println(stringCollection);
		// ddd2, aaa2, bbb1, aaa1, bbb3, ccc, bbb2, ddd1
	}
	
	
	
	/**
	 * Map - Intermediate operation
	 * 		The intermediate operation map converts each element into another object via the given function.
	 */
	@Test
	public void testMap() {
		// The following example converts each string into an upper-cased string. 
		stringCollection
			.stream()
			.map(String::toUpperCase)
			.sorted((a,b) -> b.compareTo(a))
			.forEach(System.out::println);
		
		// "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"
	}
	
	
	
	
	
	
	
	/**
	 * Match - Terminal operation
	 * 		Various matching operations can be used to check whether a certain predicate matches the stream.
	 * 		All of those operations are terminal and return a boolean result.
	 */
	@Test
	public void testMatch() {
		boolean anyStartWithA = 
				stringCollection
					.stream()
					.anyMatch((s) -> s.startsWith("a"));
		
		System.out.println(anyStartWithA);	// true
		
		
		boolean allStartWithA =
				stringCollection
				.stream()
				.allMatch((s) -> s.startsWith("a"));
		
		System.out.println(allStartWithA);	// false
		
		
		
		boolean nonStartWithZ =
				stringCollection
				.stream()
				.noneMatch((s) -> s.startsWith("z"));
		
		System.out.println(nonStartWithZ);	// true
	}
	
	
	
	/**
	 * Count - Terminal operation
	 * 		Count is a terminal operation returning the number of elements in the stream as a long.	
	 */
	@Test
	public void testCount() {
		long startsWithB =
				stringCollection
					.stream()
					.filter((s) -> s.startsWith("b"))
					.count();
		
		System.out.println(startsWithB);	// 3
	}
	
	
	/**
	 * Reduce - Terminal operation
	 * 		This terminal operation performs a reduction on the elements of the stream with the given function.
	 */
	@Test
	public void testReduce() {
		// The result is an Optional holding the reduced value
		Optional<String> reduced =
				stringCollection
					.stream()
					.sorted()
					.reduce((prev, next) -> prev + "#" + next);
		
		reduced.ifPresent(System.out::println);
	}
	
	
	@Test
	public void testMapReduce() {
		// The result is an Optional holding the reduced value
		Optional<String> reduced =
				stringCollection
				.stream()
				.map((s) -> s.substring(0,  1))
				.distinct()
				.sorted()
				.reduce((prev, next) -> prev + "-" + next);
		
		reduced.ifPresent(System.out::println);
	}
	
	
}
