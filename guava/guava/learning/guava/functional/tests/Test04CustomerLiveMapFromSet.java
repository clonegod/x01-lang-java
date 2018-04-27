package guava.functional.tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.google.common.base.Function;

public class Test04CustomerLiveMapFromSet {
	@Test
	public void givenStringSet_whenMapsToElementLength_thenCorrect() {
		
		Function<Integer, String> toBinaryFn = new Function<Integer, String>() {
			@Override
			public String apply(Integer from) {
				return Integer.toBinaryString(from.intValue());
			}
		};
		
	    Set<Integer> set = new TreeSet<Integer>(Arrays.asList(32, 64, 128));
//	    Map<Integer, String> customMap = Maps.asMap(set, toBinaryFn);
	    Map<Integer, String> customMap = new GuavaMapFromSet<Integer, String>(set, toBinaryFn);
	     
	    assertTrue(customMap.get(32).equals("100000")
	      && customMap.get(64).equals("1000000")
	      && customMap.get(128).equals("10000000"));
	     
	    set.add(256);
	    assertTrue(customMap.get(256).equals("100000000") && customMap.size() == 4);
	}
}
