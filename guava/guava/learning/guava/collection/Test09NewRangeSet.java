package guava.collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;

/**
 * The Range class used to represent the boundaries around a continuous set of values
 * 
 * RangeSet
 *		元素： Range<T>
 */
public class Test09NewRangeSet {
	
	@Test
	public void test1() {
		RangeSet<Integer> numberRangeSet = TreeRangeSet.create();
		 
	    numberRangeSet.add(Range.closed(0, 2));
	    numberRangeSet.add(Range.closed(3, 10));
	    numberRangeSet.add(Range.closed(15, 18));
	    
	    System.out.println(numberRangeSet);
	 
	    assertTrue(numberRangeSet.intersects(Range.closed(4, 17)));
	    
	    assertFalse(numberRangeSet.intersects(Range.closed(19, 200)));
	    
	}
	
	@Test
	public void test2() {
		ImmutableRangeSet<Integer> rangeSet = ImmutableRangeSet.<Integer>builder()
		        .add(Range.closed(1, 10)
		        		.canonical(DiscreteDomain.integers())) //merge ranges: must first preprocess ranges with Range.canonical(DiscreteDomain)
		        .add(Range.closed(11, 15))
		        .build();
		
		System.out.println(rangeSet);
		
		assertTrue(rangeSet.contains(15));
	}
	
}
