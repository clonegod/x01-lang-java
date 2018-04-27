package demo.guava.collection.ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Ordering;

public class OrderingTester {
	public static void main(String[] args) {
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(new Integer(5));
		numbers.add(new Integer(2));
		numbers.add(new Integer(15));
		numbers.add(new Integer(51));
		numbers.add(new Integer(53));
		numbers.add(new Integer(35));
		numbers.add(new Integer(45));
		numbers.add(new Integer(32));
		numbers.add(new Integer(43));
		numbers.add(new Integer(16));
		
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
		List<String> names = new ArrayList<String>();
		names.add("Ram");
		names.add("Shyam");
		names.add("Mohan");
		names.add("Sohan");
		names.add("Ramesh");
		names.add("Suresh");
		names.add("Naresh");
		names.add("Mahesh");
		names.add(null);
		names.add("Vikas");
		names.add("Deepak");
		System.out.println("Another List: ");
		System.out.println(names);
		Collections.sort(names, ordering2.nullsFirst());
		System.out.println("Null first sorted list: ");
		System.out.println(names);
		Collections.sort(names, ordering2.nullsFirst().reverse());
		System.out.println("Null first then reverse sorted list: ");
		System.out.println(names);
	}
}
