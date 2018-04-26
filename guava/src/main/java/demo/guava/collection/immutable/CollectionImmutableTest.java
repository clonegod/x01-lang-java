package demo.guava.collection.immutable;

import org.junit.Test;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class CollectionImmutableTest {
	
	@Test
	public void testNewImmutableCollectionUseOf() {
		Multimap<String, Integer> map = 
				ImmutableListMultimap.of("Foo", 1, "Foo", 2, "Bar", 3);
		
		System.out.println(map);
	}
	
	@Test
	public void testNewImmutableCollectionUseBuilder() {
		Multimap<String, Integer> map = 
				new ImmutableListMultimap.Builder<String, Integer>()
				.put("Foo", 1)
				.putAll("Bar", 2, 3, 4)
				.putAll("Yaa", Sets.newHashSet(7, 8, 9))
				.build(); // 返回不可变的MutilMap
		
		System.out.println(map);
	}
	
}
