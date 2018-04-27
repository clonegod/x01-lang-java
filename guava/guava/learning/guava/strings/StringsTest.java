package guava.strings;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;

public class StringsTest {
	
	// Using the Charsets class 常量定义，避免拼写错误
	@SuppressWarnings("unused")
	@Test
	public void testCharsets() {
		byte[] bytes1 = "some string".getBytes(Charsets.UTF_8);
		byte[] bytes2 = "some string".getBytes(StandardCharsets.UTF_8);
	}
	
	@Test
	public void testPad() {
		String s1 = Strings.padStart("123", 8, '0');
		System.out.println(s1);
		
		String s2 = Strings.padEnd("123", 8, '0');
		System.out.println(s2);
	}
	
	@Test
	public void testCommonPart() {
		String s1 = Strings.commonPrefix("Hello", "Hellen");
		System.out.println(s1);
		
		String s2 = Strings.commonSuffix("Hello", "Wello");
		System.out.println(s2);
	}
	
	@Test
	public void testNullOrEmpty() {
		String s1 = Strings.nullToEmpty(null);
		assertThat(s1, is(""));
		
		String s2 = Strings.emptyToNull("");
		assertThat(s2, nullValue());
		
		boolean b = Strings.isNullOrEmpty(null);
		assertThat(b, equalTo(true));
	}
	
}
