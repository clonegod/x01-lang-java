package Collections;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.collect.TreeMultimap;
import com.google.common.primitives.Ints;

/**
 * Classes with useful static methods for working with lists, maps, and sets
 *
 */
public class Test01CollectionUtilities {
	
	/**
	 *  a "collection" that isn't actually stored in main memory, 
	 *  but is being gathered from a database, or from another data center, 
	 *  and can't support operations like size() without actually grabbing all of the elements.
	 *  
	 *  Methods that themselves return Iterables return lazily computed views, rather than explicitly constructing a collection in memory. - 延迟计算
	 *  
	 *  Iterables is supplemented by the FluentIterable class, which wraps an Iterable and provides a "fluent" syntax for many of these operations.
	 */
	@Test
	public void testIterables() {
		final List<String> tokens =
			    Lists.newArrayList(" some ", null, "stuff\t", "", " \nhere");
	
		final Collection<String> filtered =
		    Collections2.filter(
		    	Collections2.transform(tokens, new Function<String, String>(){
		            @Override
		            public String apply(final String input){
		                return input == null ? "" : input.trim();
		            }
		        }), 
		    	new Predicate<String>(){
		            @Override
		            public boolean apply(final String input){
		                return !Strings.isNullOrEmpty(input);
		            }

		        });
		
		System.out.println(filtered);
		
		
		// use FluentIterable
		final Collection<String> filtered2 = FluentIterable
			    .from(tokens)
			    .transform(new Function<String, String>() {
			       @Override
			       public String apply(final String input) {
			         return input == null ? "" : input.trim();
			       }
			     })
			    .filter(new Predicate<String>() {
			       @Override
			       public boolean apply(final String input) {
			         return !Strings.isNullOrEmpty(input);
			       }
			     })
			    .toList();
		
		System.out.println(filtered2);
		
		
		// use java8
		final Collection<String> filtered3 = tokens.stream()
													.map(value -> value == null? "" : value.trim())
													.filter(value -> ! Strings.isNullOrEmpty(value))
													.collect(Collectors.toList());
		System.out.println(filtered3);
	}
	
	
	@Test
	public void testLists() {
		List<String> theseElements = Lists.newArrayList("alpha", "beta", "gamma");
		
		List<Object> exactly100 = Lists.newArrayListWithCapacity(100);
		List<Object> approx100 = Lists.newArrayListWithExpectedSize(100);
		
		
	}
	
	@Test
	public void testSets() {
//		Set<Object> approx100Set = Sets.newHashSetWithExpectedSize(100);
		
		Set<Person> s1 = Sets.newHashSet(new Person(1, "alcie"), new Person(2, "bob"), new Person(3, "cindy"));
		Set<Person> s2 = Sets.newHashSet(new Person(1, "alcie"), new Person(2, "doug"), new Person(3, "cindy"));
		
		// 交集
		Sets.SetView<Person> setView = Sets.intersection(s1, s1);
		System.out.println(setView);
		
		// 并集
		setView = Sets.union(s1, s2);
		System.out.println(setView);
		
		// 差集
		setView = Sets.difference(s1,s2);
		System.out.println(setView);
		
		setView = Sets.difference(s2,s1);
		System.out.println(setView);
		
		setView = Sets.symmetricDifference(s2,s1);
		System.out.println(setView);
		
	}
	
	@Test
	public void testMaps() {
		Map<String, Object> map = Maps.newLinkedHashMap();
	}
	
	@Test
	public void testMultiSets() {
		Multiset<String> multiset = HashMultiset.create();
	}
	
	@Test
	public void testMultiMaps1() {
		ImmutableSet<String> digits = ImmutableSet.of(
			    "zero", "one", "two", "three", "four",
			    "five", "six", "seven", "eight", "nine");
		
		Function<String, Integer> lengthFunction = new Function<String, Integer>() {
		  public Integer apply(String string) {
		    return string.length();
		  }
		};
		
		// 按长度进行分组，将set转化为map
		ImmutableListMultimap<Integer, String> digitsByLength = Multimaps.index(digits, lengthFunction);
		System.out.println(digitsByLength);
		/*
		 * digitsByLength maps:
		 *  3 => {"one", "two", "six"}
		 *  4 => {"zero", "four", "five", "nine"}
		 *  5 => {"three", "seven", "eight"}
		 */
	}
	
	
	@Test
	public void testMultiMaps2() {
		Multimap<String, Integer> multimap = ArrayListMultimap.create();
		multimap.putAll("b", Ints.asList(2, 4, 6));
		multimap.putAll("a", Ints.asList(4, 2, 1));
		multimap.putAll("c", Ints.asList(2, 5, 3));

		// TreeMultimap<Integer, String> inverse = Multimaps.invertFrom(multimap, TreeMultimap.<String, Integer> create()); // ---> TreeMultimap.<Integer, String> create() 
		TreeMultimap<Integer, String> inverse = Multimaps.invertFrom(multimap, TreeMultimap.<Integer, String> create());
		System.out.println(inverse);
		// note that we choose the implementation, so if we use a TreeMultimap, we get results in order
		/*
		 * inverse maps:
		 *  1 => {"a"}
		 *  2 => {"a", "b", "c"}
		 *  3 => {"c"}
		 *  4 => {"a", "b"}
		 *  5 => {"c"}
		 *  6 => {"b"}
		 */
	}
	
	@Test
	public void testMultiMaps3() {
		Map<String, Integer> map = ImmutableMap.of("a", 1, "b", 1, "c", 2);
		
		// 将普通map转化为multimap
		SetMultimap<String, Integer> multimap = Multimaps.forMap(map);
		// multimap maps ["a" => {1}, "b" => {1}, "c" => {2}]
		
		// 利用mutilmaps中的工具方法，进行key-value反转
		Multimap<Integer, String> inverse = Multimaps.invertFrom(multimap, HashMultimap.<Integer, String> create());
		// inverse maps [1 => {"a", "b"}, 2 => {"c"}]
		
		System.out.println(inverse);
	}
	
	
	
	@Test
	public void testTables() {
		// Tables.newCustomTable(Map, Supplier<Map>)  allows you to specify a Table implementation using whatever row or column map you like.
		Table<String, Character, Integer> table = Tables.newCustomTable(
		  // use LinkedHashMaps instead of HashMaps
		  Maps.<String, Map<Character, Integer>>newLinkedHashMap(),
		  new Supplier<Map<Character, Integer>> () {
		    public Map<Character, Integer> get() {
		    	// use LinkedHashMaps instead of HashMaps
		      return Maps.newLinkedHashMap();
		    }
		  });
		
		table.put("rowkey1", 'A', 65);
		table.put("rowkey1", 'B', 66);
		table.put("rowkey1", 'C', 67);
		table.put("rowkey2", 'a', 97);
		table.put("rowkey3", 'b', 98);
		
		System.out.println(table.row("rowkey1"));
		System.out.println(table.row("rowkey1").get('A'));
	}
	
	@Test
	public void testQueues() {
		
	}
	
	@Test
	public void testCollections2() {
		
	}
	
	
	/**
	 * @author Administrator
	 *
	 */
	private static class Person {
		int id;
		String name;
		
		public Person(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(id, name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Person other = (Person) obj;
			if (id != other.id)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("id", id)
					.add("name", name)
					.toString();
		}
		
		
		
	}
}
