package demo.guava.collection.map;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Maps.EntryTransformer;
import com.google.common.collect.Sets;

import demo.guava.basic.object.Book;
import demo.guava.basic.object.Person;

public class MapsTest {
	
	/**
	 * Iterator(List/Set) -> Map
	 * 
	 * Maps.uniqueIndex method uses Function to generate keys from the given values
	 */
	@Test
	public void testUniqueIndex() {
		List<Book> books = getBooks();
		
		Function<Book, String> isbnAsKey = new Function<Book, String>(){
			@Override
			public String apply(Book input) {
				return input.getIsbn();
			}
		};
			
		Function<Book, String> titleAsKey = new Function<Book, String>(){
			@Override
			public String apply(Book input) {
				return input.getTitle();
			}
		};
		
		Map<String,Book> bookMap = Maps.uniqueIndex(books.iterator(), isbnAsKey);
		System.out.println(bookMap);
		
		// we could easily change the algorithm for generating the key
		Map<String,Book> bookMap2 = Maps.uniqueIndex(books.iterator(), titleAsKey);
		System.out.println(bookMap2);
	}
	
	/**
	 * Set -> Map
	 * 
	 * Maps.asMap method takes a set of objects to be used as keys, 
	 * and Function is applied to each key object to generate the value for entry into a map instance. 
	 */
	@Test
	public void testAsMap() {
		Set<String> set = Sets.newHashSet("A", "B", "C");
		Map<String, Integer> newMap = 
		Maps.asMap(set, new Function<String, Integer>() {
			@Override
			public Integer apply(String input) {
				return (int)input.charAt(0);
			}
		});
		System.out.println(newMap);
		
		newMap.clear();
		
		System.out.println(set);
	}
	
	/**
	 * Set -> ImmutableMap
	 * 
	 * with the difference being ImmutableMap is returned instead of a view of the map
	 */
	@Test(expected=UnsupportedOperationException.class)
	public void testToMap() {
		Set<String> set = Sets.newHashSet("A", "B", "C");
		Map<String, Integer> newMap = 
		Maps.toMap(set, new Function<String, Integer>() {
			@Override
			public Integer apply(String input) {
				return (int)input.charAt(0);
			}
		});
		System.out.println(newMap);
		
		// ImmutableMap cann't be modify
		newMap.clear();
	}
	
	/**
	 * Map Entries -> Map
	 * 根据原始Map的entry来返回新值
	 */
	@Test
	public void testTransfromEntries() {
		Map<String, Boolean> options = ImmutableMap.of("verbose", true, "sort", false);
		EntryTransformer<String, Boolean, String> flagPrefixer = 
			new EntryTransformer<String, Boolean, String>() {
				public String transformEntry(String key, Boolean value) {
					return value ? key : "no-" + key;
				}
			};
		Map<String, String> transformed = 
				Maps.transformEntries(options, flagPrefixer);
		System.out.println(transformed);
	}
	
	/**
	 * Map Values -> Map
	 * 根据原始Map的value来返回新值
	 */
	@Test
	public void testTransfromValues() {
		Map<String, Integer> map = ImmutableMap.of("a", 4, "b", 9);
		Function<Integer, Double> sqrt = new Function<Integer, Double>() {
			public Double apply(Integer in) {
				return Math.sqrt((int) in);
			}
		};
		Map<String, Double> transformed = Maps.transformValues(map, sqrt);
		System.out.println(transformed);
	}

	
	private List<Book> getBooks() {
		List<Book> books = 
			Lists.newArrayList(
					new Book(new Person("Alice", 20), "java", "Oracle", "11111", 88.11),
					new Book(new Person("Alice", 20), "guava", null, "22222", 88.12),
					new Book(new Person("Bobbb", 20), "nio", "Oracle", "3333", 88.13),
					new Book(new Person("Bobbb", 18), "concurrent", "Oracle", "4444", 88.13)
					);
		return books;
	}
	
}
