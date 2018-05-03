package guava.collection;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.compose;
import static com.google.common.base.Predicates.in;
import static com.google.common.base.Predicates.not;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

/**
 * Introduction to Google Collections
 * 
 * 
 * This was a short introduction to Google Collection. 
 * This article demonstrated only a minor subset of what the API contains. 
 * Feel free to explore the rest of the API by yourself.
 * 
 * http://winterbe.com/posts/2009/09/14/introduction-to-google-collections/
 *
 */
public class Test01GuavaCollections {

	/**
	 * Working with Lists 
	 * 		The class Lists contains plenty of static utility methods for building and manipulating lists
	 * 
	 * 	Sets and Maps for set and map utilities
	 */
	@Test
	public void testLists() {
//		I'm using some factory method to create an array list. 
//		Then this list will be transformed to another list by applying some generic function to all lists elements.
//		The transformed list will be printed to the console by using a Joiner which let you easily build strings from collections.
		List<String> list1 = Lists.newArrayList("1", "2", "3");
		List<Double> list2 = Lists.transform(list1, new Function<String, Double>() {
		   public Double apply(String from) {
		      return Double.parseDouble(from);
		   }
		});

		System.out.println(Joiner.on(" | ").join(list2));
	}
	
	
	/**
	 * Working with Maps
	 *	Google Collections comprises very nice Map support. 
	 *	Not only does the library provide convient utility methods via the class Maps.
	 */
	@Test
	public void testBiMap() {
		BiMap<Integer,String> biMap = HashBiMap.create();
		biMap.put(Integer.valueOf(5), "Five");
		biMap.put(Integer.valueOf(1), "One");
		biMap.put(Integer.valueOf(9), "Nine");
		biMap.put(Integer.valueOf(5), "Another Five");
		biMap.put(Integer.valueOf(55), "Five");

		System.out.println(biMap);
		System.out.println(biMap.inverse());
	}
	
	/**
	 * Google Collections enables you to easily build immutable maps via builder:
	 */
	@Test
	public void testImmutableMap() {
		ImmutableMap<String,Integer> map1 =
				   new ImmutableMap.Builder<String,Integer>()
				      .put("one", 1)
				      .put("two", 2)
				      .put("three", 3)
				      .build();

		ImmutableMap<String,Integer> map2 =
		   new ImmutableMap.Builder<String,Integer>()
		      .put("five", 5)
		      .put("four", 4)
		      .put("three", 3)
		      .build();
		
		// computing the difference between two maps is quite comfortable using the utility class Maps
		MapDifference<String, Integer> difference = Maps.difference(map1, map2);
		System.out.println(difference.entriesInCommon());
		System.out.println(difference.entriesOnlyOnLeft());
		System.out.println(difference.entriesOnlyOnRight());
		
	}

	/**
	 * filter a map by some predicate
	 */
	@Test
	public void testFilterMap() {
		ImmutableMap<Integer,String> map =
				   new ImmutableMap.Builder<Integer,String>()
				      .put(10, "Ten")
				      .put(20, "Twenty")
				      .put(30, "Thirty")
				      .build();

		Map<Integer,String> filtered = Maps.filterKeys(map, Predicates.or(Predicates.equalTo(10), Predicates.equalTo(30)));
		System.out.println(filtered);
	}
	
	
	/**
	 * apply some transformations to the values of a map
	 * 
	 */
	@Test
	public void testTransformation() {
		ImmutableMap<Integer,String> map =
				   new ImmutableMap.Builder<Integer,String>()
				      .put(10, "10")
				      .put(20, "20")
				      .put(30, "30")
				      .build();

		Map<Integer,String> transformed = Maps.transformValues(map, new Function<String,String>() {
		   public String apply(String from) {
		      return "X" + from;
		   }
		});

		System.out.println(transformed);
	}
	
	
	
	
	
	/**
	 * Extensions to Iterators and Iterables - 迭代器工具类
	 * 
	 * 	Google Collections serves convient utilities for iterating over collections of elements. 
	 *  The classes Iterators and Iterables contain various helpful static methods for manipulating, combining, filtering or transforming iterable collections. 
	 */
	@Test
	public void testIterables() {
		// First a list will be constructed containing some strings and a null value. 
		List<String> list = Lists.newArrayList("A100", "B100", null, "B200");
		
		// Then this list will be filtered, we only want all the strings starting with B and the null value. 
		Iterable<String> filtered = Iterables.filter(list, new Predicate<String>() {
		   public boolean apply(String input) {
		      return input == null || input.startsWith("B");
		   }
		});

		// Finally the result will be printed to the console replacing all null values with B000. 
		System.out.println(Joiner.on("; ").useForNull("B000").join(filtered));
	}
	
	
	/**
	 * Building Predicate Logic - 判断
	 */
	@Test
	public void testPredicate() {
		List<String> list1 = Lists.newArrayList("1", "2", "3");
		List<String> list2 = Lists.newArrayList("1", "4", "5");
		List<String> list3 = Lists.newArrayList("1", "4", "6");

		boolean result = and( not( in(list1) ), in(list2), in(list3)).apply("1");

		System.out.println(result);  // false

		list1 = Lists.newArrayList("A1", "A2", "A3");
		
		// predicate(function(x))
		result = compose(in(list1), new Function<String, String>() {
		   public String apply(String from) {
		      return "A" + from;
		   }
		}).apply("1");

		System.out.println(result);  // true
	}
	
	
	/**
	 * Ordering  -  Combining and Modifying Comparators
	 * 
	 * 	As you can see its easy to combine the comparators to complex orderings. 
	 *  Besides you don't have to bother with null values. 
	 */
	@Test
	public void testOrderingWithComparator() {
		List<Person> persons = Lists.newArrayList(
				   new Person("Alfred", "Hitchcock"),
				   null,
				   new Person("Homer", "Simpson"),
				   new Person("Peter", "Fox"),
				   new Person("Bart", "Simpson"));

		Comparator<Person> lastNameComparator = new Comparator<Person>() {
		   public int compare(Person p1, Person p2) {
		      return p1.getLastName().compareTo(p2.getLastName());
		   }
		};

		Comparator<Person> firstNameComparator = new Comparator<Person>() {
		   public int compare(Person p1, Person p2) {
		      return p1.getFirstName().compareTo(p2.getFirstName());
		   }
		};
		
		// sortedCopy: use this only when the resulting list may need further modification, or may contain null

		// order by last name ascending
		Ordering<Person> ordering = Ordering.from(lastNameComparator);
		System.out.println(ordering.nullsLast().sortedCopy(persons));

		// order by last name descending, first name ascending
		ordering = ordering.reverse().compound(firstNameComparator);
		System.out.println(ordering.nullsFirst().sortedCopy(persons));
	}
	
	
	
	
	
	private static class Person {
		   private String firstName;
		   private String lastName;

		   public Person(String firstName, String lastName) {
		      this.setFirstName(firstName);
		      this.setLastName(lastName);
		   }

		   @Override
		   public String toString() {
		      return getFirstName() + " " + getLastName();
		   }

		   public void setFirstName(String firstName) {
		      this.firstName = firstName;
		   }

		   public String getFirstName() {
		      return firstName;
		   }

		   public void setLastName(String lastName) {
		      this.lastName = lastName;
		   }

		   public String getLastName() {
		      return lastName;
		   }
		}
}
