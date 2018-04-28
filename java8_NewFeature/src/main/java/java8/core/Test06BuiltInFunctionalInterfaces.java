package java8.core;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

public class Test06BuiltInFunctionalInterfaces {
	
	// Predicates, Functions, Suppliers, Consumers, Comparators, Optionals
	
	/** Predicates 
	 * 		Predicates are boolean-valued functions of one argument. 
	 * 		The interface contains various default methods for composing predicates to complex logical terms (and, or, negate)
	 * */
	@Test
	public void test() {
		Predicate<String> predicate = (s) -> s.length() > 0;

		predicate.test("foo");              // true
		predicate.negate().test("foo");     // false

		Predicate<Boolean> nonNull = Objects::nonNull;
		Predicate<Boolean> isNull = Objects::isNull;

		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> isNotEmpty = isEmpty.negate();
	}
	
	/** Functions 
	 * 		Functions accept one argument and produce a result. 
	 * 		Default methods can be used to chain multiple functions together (compose, andThen).
	 * */
	@Test
	public void test02() {
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);

		backToString.apply("123");     // "123"
	}
	
	/** 
	 * Suppliers 
	 * 		Suppliers produce a result of a given generic type. 
	 * 		Unlike Functions, Suppliers don't accept arguments.
	 * */
	@Test
	public void test03() {
		Supplier<Person> personSupplier = Person::new;
		personSupplier.get();   // new Person
	}
	
	/** 
	 * Consumers 
	 * 		Consumers represents operations to be performed on a single input argument.
	 * */
	@Test
	public void test04() {
		Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
		greeter.accept(new Person("Luke", "Skywalker"));
	}
	
	
	
	/** 
	 * Comparators  
	 * 		Comparators are well known from older versions of Java. Java 8 adds various default methods to the interface.
	 * */
	@Test
	public void test05() {
		Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);

		Person p1 = new Person("John", "Doe");
		Person p2 = new Person("Alice", "Wonderland");

		comparator.compare(p1, p2);             // > 0
		comparator.reversed().compare(p1, p2);  // < 0
	}
	
	
	
	/** Runnable */
	@Test
	public void test06() {
		Runnable r = () -> System.out.println("Hello,World");
		
		new Thread(r).start();
	}
	
	
	/** Callable */
	
	@Test
	public void test07() {
		Callable<String> c = () -> "Hello,World";
		
		Future<String> future = Executors.newSingleThreadExecutor().submit(c);
		
		try {
			System.out.println(future.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	
	static class Person {
		String firstName;
		String lastName;
		
		public Person() {
			super();
		}

		public Person(String firstName, String lastName) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
		}
		
		
	}
	
}
