package Collections;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

/**
 * The Range class used to represent the boundaries around a continuous set of values
 * 
 *	RangeMap
 *		key   - Range<T>
 *		value - Object
 */
public class Test10NewRangeMap {
	
	enum LEVEL {
		A,B,C,D
	}
	
	@Test
	public void test1() {
		assertTrue(getLevel(50) == LEVEL.D);
		assertTrue(getLevel(60) == LEVEL.C);
		assertTrue(getLevel(75) == LEVEL.C);
		assertTrue(getLevel(88) == LEVEL.B);
		assertTrue(getLevel(95) == LEVEL.A);
	}
	
	private LEVEL getLevel(int score) {
		Preconditions.checkArgument(score >=0 && score <=100, "invalid score, must between 0 and 100");
		if(score < 60) {
			return LEVEL.D;
		}
		if(score >=60 && score < 80) {
			return LEVEL.C;
		}
		if(score >= 80 && score < 90) {
			return LEVEL.B;
		}
		return LEVEL.A;
	}

	@Test
	public void test2() {
		RangeMap<Integer, LEVEL> rangeMap = TreeRangeMap.create();
		rangeMap.put(Range.closedOpen(0, 60), LEVEL.D);
		rangeMap.put(Range.closedOpen(60, 80), LEVEL.C);
		rangeMap.put(Range.closedOpen(80, 90), LEVEL.B);
		rangeMap.put(Range.closed(90, 100), LEVEL.A);
		
		Map<Range<Integer>, LEVEL> mapRange = rangeMap.asMapOfRanges();
		mapRange.forEach((k, v) -> System.out.println(k + ":" + v));
		
		assertTrue(rangeMap.get(59) == LEVEL.D);
		assertTrue(rangeMap.get(60) == LEVEL.C);
		assertTrue(rangeMap.get(75) == LEVEL.C);
		assertTrue(rangeMap.get(88) == LEVEL.B);
		assertTrue(rangeMap.get(95) == LEVEL.A);

	}
	
}
