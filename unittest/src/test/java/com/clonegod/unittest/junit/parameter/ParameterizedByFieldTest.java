package com.clonegod.unittest.junit.parameter;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Using @Parameter for Field injection instead of Constructor
 */
@RunWith(Parameterized.class)
public class ParameterizedByFieldTest {
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 0, 0 }, { 1, 1 }, { 2, 1 }, { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 } });
	}

	@Parameter // first data value (0) is default
	public /* NOT private */ int fInput;

	@Parameter(1)
	public /* NOT private */ int fExpected;

	@Test
	public void test() {
		assertEquals(fExpected, Fibonacci.compute(fInput));
	}
    
    static class Fibonacci {
    	public static int compute(int n) {
    		int result = 0;
    		
    		if (n <= 1) { 
    			result = n; 
    		} else { 
    			result = compute(n - 1) + compute(n - 2); 
    		}
    		
    		return result;
    	}
    }
}
