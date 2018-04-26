package com.clonegod.unittest.junit;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Matcher对断言提供更优雅的写法，断言的写法更灵活，而且提供了更具可读性的测试错误描述。
 * 
 * Matcher Combinations: 
 *	any matcher statement s can be negated (not(s)), 
 *	combined (either(s).or(t)), 
 *	mapped to a collection (each(s)), 
 *	or used in custom combinations (afterFiveSeconds(s))
 */
public class MatcherTest {
	
	//  assertThat([value], [matcher statement]) 断言风格
	@Test
	public void test() {
		int x = 3;
		assertThat(x, is(3));
		assertThat(x, is(not(4)));
		
		String responseString = "the color is blue";
		assertThat(responseString, either(containsString("color")).or(containsString("colour")));
		
		List<String> myList = Arrays.asList(new String[]{"1", "2", "3"});
		assertThat(myList, hasItem("3"));
	}
	
	@Test
	public void testJsonAssert() {
		assertThat(
				"{\"age\":43, \"friend_ids\":[16, 52, 23]}",
				sameJSONAs("{\"friend_ids\":[52, 23, 16]}")
					.allowingExtraUnexpectedFields()
					.allowingAnyArrayOrdering());
	}
	
	
}
