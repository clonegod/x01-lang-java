package demo.guava.basic.string;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileReader;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

public class SplitterTest {
	
	// split后对元素进行trim
	@Test
	public void test1() {
		String testString = " a, b ,c ";
		Splitter splitter = Splitter.on(",").trimResults(); // .trimResults(CharMatcher.WHITESPACE);
		Iterable<String> parts = splitter.split(testString);
		System.out.println(parts);
		
	}
	
	// split后对元素进行自定义trim
	@Test
	public void test11() {
		String testString = "_a ,_b_ ,c__";
		Splitter splitter = Splitter.on(",").trimResults(CharMatcher.is('_'));
		Iterable<String> parts = splitter.split(testString);
		System.out.println(parts);
		
	}
	
	// 单字符分割
	@Test
	public void test2() {
		Splitter splitter = Splitter.on('|');
		Iterable<String> parts = splitter.split("foo|bar|baz"); 
		System.out.println(parts);
	}
	
	// 字符串分割
	@Test
	public void test3() {
		Splitter splitter = Splitter.on(", ");
		Iterable<String> parts = splitter.split("foo, bar,baz"); 
		System.out.println(parts);
	}
	
	// 正则分割
	@Test
	public void test4() {
		Splitter splitter = Splitter.on(Pattern.compile("\\d+"));
		Iterable<String> parts = splitter.split("123abc456efgxyz"); 
		System.out.println(parts);
	}
	
	// 行分割
	@Test
	public void test5() throws Exception {
		try (FileReader fileReader = new FileReader(new File("tmp/out.txt"))) {
			CharBuffer charBuffer = CharBuffer.allocate(1024);
			while((fileReader.read(charBuffer)) != -1) {
				fileReader.read(charBuffer);
				if(! charBuffer.hasRemaining()) {
					break;
				}
			}
			charBuffer.flip();
			Splitter splitter = Splitter.on(Pattern.compile("\r?\n"));
			Iterable<String> parts = splitter.split(charBuffer.toString()); 
			System.out.println(parts);
		} finally {
			
		}
		
	}
	
	
	
	@Test
	public void testMapSplitter() {
		String startString = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
		Map<String,String> testMap = Maps.newLinkedHashMap();
		testMap.put("Washington D.C","Redskins");
		testMap.put("New York City","Giants");
		testMap.put("Philadelphia","Eagles");
		testMap.put("Dallas","Cowboys");
		
		Splitter.MapSplitter mapSplitter = Splitter.on("#").withKeyValueSeparator("=");
		Map<String,String> splitMap = mapSplitter.split(startString);
		assertThat(testMap, is(splitMap));
	}
}
