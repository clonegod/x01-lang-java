package guava.strings;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class StringUtilitiesTester {
	
	/**
	 * 给定分隔符，拼接字符串
	 */
	@Test
	public void testJoiner(){
		System.out.println(Joiner.on(",")
		.skipNulls()
		.join(Arrays.asList(1,2,3,4,5,null,6)));
	}
	
	/**
	 * 给定分隔符，切割字符串
	 */
	@Test
	public void testSplitter(){
		System.out.println(Splitter.on(',')
		.trimResults()
		.omitEmptyStrings()
		.split("the ,quick, , brown , fox, jumps, over, the, lazy, little dog."));
	}
	
	
	@Test
	public void testCharMatcher(){
		System.out.println(CharMatcher.WHITESPACE.trimAndCollapseFrom("  Mahesh  Parashar  ", '-'));
		// trim whitespace at ends, and replace/collapse whitespace into single spaces

		System.out.println(CharMatcher.DIGIT.retainFrom("mahesh123")); // only the digits
		
		System.out.println(CharMatcher.JAVA_DIGIT.replaceFrom("mahesh123", "*")); // star out all digits
		
		System.out.println(CharMatcher.JAVA_DIGIT.or(CharMatcher.JAVA_LOWER_CASE).retainFrom("mahesh123？"));
		// eliminate all characters that aren't digits or lowercase
	}
	
	
	@Test
	public void testCaseFormat(){
		String data = "test_data";
		System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, data));
		System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, data));
		System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, data));
	}
}
