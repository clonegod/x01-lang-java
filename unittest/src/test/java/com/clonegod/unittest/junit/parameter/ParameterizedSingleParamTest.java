package com.clonegod.unittest.junit.parameter;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests with single parameter
 */
@RunWith(Parameterized.class)
public class ParameterizedSingleParamTest {
	
	@Parameters(name = "{index}: test({0})")
	public static Iterable<? extends Object> data() {
	    return Arrays.asList("first test", "second test");
	}
	
	@Parameter
	public String input;

	@Test
	public void test() {
		assertThat("should contains 'test'", containsString("test"));
	}
    
}
