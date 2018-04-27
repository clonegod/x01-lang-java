package guava.functional.tests;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class Test03FunctionalUsage {

	/**
	 * 将Set转化为Map - 遍历Set中的元素，对每个元素应用Function，将得到的结果作为Map的value
	 */
	@Test
	public void test1() {
		Function<Integer, String> toBinaryFn = new Function<Integer, String>() {
			@Override
			public String apply(Integer from) {
				return Integer.toBinaryString(from.intValue());
			}
		};
		
		Set<Integer> keys = new TreeSet<>(Arrays.asList(32, 64, 128));
		
		// Returns an immutable map whose keys are the distinct elements of keys and whose value for each key was computed by valueFunction.
		Map<Integer,String> immutableMap = Maps.toMap(keys, toBinaryFn); // toMap - 不可变
		System.out.println(immutableMap);
		
		keys.add(256);
		System.out.println(immutableMap);
		
	}
	
	@Test
	public void test2() {
		Function<Integer, String> toBinaryFn = new Function<Integer, String>() {
			@Override
			public String apply(Integer from) {
				return Integer.toBinaryString(from.intValue());
			}
		};
		
		Set<Integer> keys = new TreeSet<>(Arrays.asList(32, 64, 128));
		
		// the changes to the originating Set will be reflected in the map as well:
		Map<Integer,String> liveMap = Maps.asMap(keys, toBinaryFn); // asMap
		System.out.println(liveMap);
		
		keys.add(256);
		System.out.println(liveMap);
		
	}
	
}
