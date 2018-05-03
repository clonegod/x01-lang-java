package java8.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class TestStrings {

	/**
	 * join
	 * 	优化：底层使用了StringBuilder
	 */
	@Test
	public void testJoin() {
		String s1 = "ThreadName: " + Thread.currentThread().getName();
		String s2 = String.join(": ", "ThreadName", Thread.currentThread().getName());
		
		assertEquals(s1, s2);
		
		System.out.println(String.join("", "解析", "5", "月账单失败，异常信息："));
	}
	
	/**
	 * chars
	 * 	splitting strings into streams for each character 
	 * 	creates a stream for all characters of the string, use stream operations upon those characters
	 *  将字符串转为字符流，使用stream api进行处理
	 */
	@Test
	public void testChars() {
		String result =
			"foobar:foo:bar"
			    .chars()
			    .distinct()
			    .mapToObj(c -> String.valueOf((char)c))
			    .sorted()
			    .collect(Collectors.joining());
		 
		System.out.println(result);
	}
	
	// 福建 浙江
	
	/**
	 * pattern.splitAsStream
	 *  split strings for any pattern and create a stream to work
	 */
	@Test 
	public void testPatternAsStream() {
		String result = 
			Pattern.compile(":")
			    .splitAsStream("foobar:foo:bar")
			    .filter(s -> s.contains("bar"))
			    .sorted()
			    .collect(Collectors.joining(":"));
		
		System.out.println(result);
		assertEquals(result, "bar:foobar");
	// => bar:foobar
	}
	
	
	
	/**
	 * regex patterns can be converted into predicates. 
	 * Those predicates can be used to filter a stream of strings:
	 */
	@Test
	public void testPatternToPredicate() {
		Pattern pattern = Pattern.compile(".*@gmail\\.com");
		
		long count = 
		Stream.of("bob@gmail.com", "alice@hotmail.com")
		    .filter(pattern.asPredicate())
		    .count();
		assertTrue(count == 1);
		// => 1
	}
	
}
