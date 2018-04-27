package guava.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Sets;

/**
 * Immutable Collections
 * 
	hread-safe: can be used by many threads with no risk of race conditions.
	defensive programming, 
	constant collections, 
	and improved efficiency. (All immutable collection implementations are more memory-efficient than their mutable siblings. )
 *	
 */
/*
The JDK provides Collections.unmodifiableXXX methods, but in our opinion, these can be

unwieldy and verbose: unpleasant to use everywhere you want to make defensive copies
unsafe: the returned collections are only truly immutable if nobody holds a reference to the original collection
inefficient: the data structures still have all the overhead of mutable collections, including concurrent modification checks, extra space in hash tables, etc.
 * */
public class Test03ImmutableCollections {

	// constant collections
	public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of("red", "orange", "yellow", "green", "blue", "purple");

	/**
	 * 不可变集合的好处：
	 * 	1、不可修改，因此线程安全
	 * 	2、更高效
	 */
	@Test
	public void testCreateImmutable() {
		ImmutableList<Integer> list = ImmutableList.copyOf(Arrays.asList(1,3,2,4));
		list.forEach(System.out::println);
		
		ImmutableSet<String> set = ImmutableSet.of("x", "y", "c", "a", "d", "b");
		System.out.println("set index 0 : " + set.asList().get(0));
		
		ImmutableSet<String> sortedSet = ImmutableSortedSet.of("x", "y", "c", "a", "d", "b");
		System.out.println("sortedSet index 0 : " + sortedSet.asList().get(0));
		
		ImmutableMap<String, String> map = ImmutableMap.<String,String>builder().put("Accept", "application/json").put("User-Agent", "Chrome/65.0.3325.181").build();
		map.forEach((k,v) -> System.out.println(k + ":" +v));
		
		
		// more Immutable collection 
		/*
		Interface				JDK or Guava?	Immutable Version
		Collection				JDK				ImmutableCollection
		List					JDK				ImmutableList
		Set						JDK				ImmutableSet
		SortedSet/NavigableSet	JDK				ImmutableSortedSet
		Map						JDK				ImmutableMap
		SortedMap				JDK				ImmutableSortedMap
		Multiset				Guava			ImmutableMultiset
		SortedMultiset			Guava			ImmutableSortedMultiset
		Multimap				Guava			ImmutableMultimap
		ListMultimap			Guava			ImmutableListMultimap
		SetMultimap				Guava			ImmutableSetMultimap
		BiMap					Guava			ImmutableBiMap
		ClassToInstanceMap		Guava			ImmutableClassToInstanceMap
		Table					Guava			ImmutableTable
		*/
	}
	
	
	
	class Foo {
		final ImmutableSet<Bar> bars;

		Foo(Set<Bar> bars) {
			this.bars = ImmutableSet.copyOf(bars); // defensive copy!
		}
	}

	class Bar {
		int id;
		public Bar(int id){
			this.id = id;
		}
		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this)
					.add("id", id)
					.toString();
		}
	}

	@Test
	public void test() {
		Set<Bar> bars = Sets.newHashSet(new Bar(1), new Bar(2));
		bars.forEach(bar -> System.out.println(bar));
		
		Foo foo = new Foo(bars);
		
		// modify source 
		Iterator<Bar> iter = bars.iterator();
		iter.next();
		iter.remove();
		iter.next().id = 0;
		
		System.out.println("source bars---------");
		bars.forEach(bar -> System.out.println(bar));
		
		System.out.println("foo bars---------");
		foo.bars.forEach(bar -> System.out.println(bar));
		
	}
}
