package guava.collection;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

/**
 * Order is subclass of Comparator
 * 
 *	The Ordering class that gives us enhanced abilities when working with Comparators
 */
public class Test08Ordering {

	@Test
	public void testOrder() {
		List<Integer> numbers = Lists.newArrayList(23,1,4,89,2,56,10);
		
		Ordering<Integer> ordering = Ordering.natural();
		System.out.println("Input List: ");
		System.out.println(numbers);
		Collections.sort(numbers, ordering );
		System.out.println("Sorted List: ");
		System.out.println(numbers);
		System.out.println("List is sorted: " + ordering.isOrdered(numbers));
		System.out.println("Minimum: " + ordering.min(numbers));
		System.out.println("Maximum: " + ordering.max(numbers));
		System.out.println("======================");
		
		Collections.sort(numbers, ordering.reverse());
		System.out.println("Reverse: " + numbers);
		numbers.add(null);
		System.out.println("Null added to Sorted List: ");
		System.out.println(numbers);
		Collections.sort(numbers, ordering.nullsFirst());
		System.out.println("Null first Sorted List: ");
		System.out.println(numbers);
		System.out.println("======================");
		
		
		Ordering<String> ordering2 = Ordering.natural();
		List<String> names = Lists.newArrayList("Ram", "Shyam", "Mohan", "Sohan", "Ramesh", null, "Naresh", "Deepak");
		System.out.println("Another List: ");
		System.out.println(names);
		Collections.sort(names, ordering2.nullsFirst());
		System.out.println("Null first sorted list: ");
		System.out.println(names);
		Collections.sort(names, ordering2.nullsFirst().reverse());
		System.out.println("Null first then reverse sorted list: ");
		System.out.println(names);
	}
	
	
	@Test
	public void testBeanOrder() {
		Function<Person, String> byFirstNameOrdering = new Function<Person, String>() {
			@Override
			public String apply(Person p) {
				return p.firstName; // 根据某个字段进行排序
			}
		};
		
		
		Ordering<Person> ordering = Ordering.natural().nullsFirst().onResultOf(byFirstNameOrdering);
			
		Person alice = new Person("alice", "A", 100);
		Person bob = new Person("alice", null, 90);
		Person cindy = new Person("cindy", "B", 80);
		List<Person> persons = Lists.newArrayList(cindy, alice, bob);
		
		Collections.sort(persons, ordering);
		persons.forEach(System.out::println);
		
		System.out.println();
		
		Collections.sort(persons, ordering.reverse());
		persons.forEach(System.out::println);
			
	}
	
	
	class Person implements Comparable<Person> {
		private String lastName;
		private String firstName;
		private int zipCode;

		public Person(String lastName, String firstName, int zipCode) {
			this.lastName = lastName;
			this.firstName = firstName;
			this.zipCode = zipCode;
		}

		public int compareTo(Person that) {
		     return ComparisonChain.start()
		         .compare(this.lastName, that.lastName)
		         .compare(this.firstName, that.firstName)
		         .compare(this.zipCode, that.zipCode)
		         .result();
		   }
		
		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("lastName", lastName)
					.add("firstName", firstName)
					.add("zipCode", zipCode)
					.toString();
		}
	}
}
