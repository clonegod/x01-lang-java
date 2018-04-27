package guava.strings;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

public class JoinerTest {
	
	String[] array = {"Hello", "Wolrd", "Hi", "There", null};
	
	// 忽略null元素
	@Test
	public void test1() {
		Joiner joiner = Joiner.on("|").skipNulls();
		System.out.println(joiner.join(array));
	}
	
	// 为null元素设置默认值
	@Test
	public void test2() {
		Joiner joiner = Joiner.on("|").useForNull("@");
		System.out.println(joiner.join(array));
	}
	
	// Joiner是不可变对象，构造之后不能再修改
	@Test(expected=UnsupportedOperationException.class)
	public void test3() {
		Joiner stringJoiner = Joiner.on("|").skipNulls();
		// Once created, a Joiner class is immutable
		stringJoiner.useForNull("missing");
	}
	
	// 将转化后的结果存入StringBuilder
	@Test
	public void test4() {
		StringBuilder stringBuilder = new StringBuilder();
		Joiner joiner = Joiner.on("|").skipNulls();
		//returns the StringBuilder instance with the values foo,bar,baz appeneded with "|" delimiters
		joiner.appendTo(stringBuilder,"foo","bar","baz");
		System.out.println(stringBuilder.toString());
	}
	
	// 将转化后的字符串写入文件
	@Test
	public void test5() throws Exception {
		FileWriter fileWriter = new FileWriter(new File("tmp/out.txt"), false);
		List<Date> dateList = getDates();
		Joiner joiner = Joiner.on("#\n").useForNull(" ");
		//returns the FileWriter instance with the values appended into it
		joiner.appendTo(fileWriter,dateList);
		fileWriter.close();
	}

	
	@Test
	public void testMapJoiner() {
		//Using LinkedHashMap so that the original order is preserved
		String expectedString = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
		Map<String,String> testMap = Maps.newLinkedHashMap();
		testMap.put("Washington D.C","Redskins");
		testMap.put("New York City","Giants");
		testMap.put("Philadelphia","Eagles");
		testMap.put("Dallas","Cowboys");
		
		String returnedString = Joiner.on("#").withKeyValueSeparator("=").join(testMap);
		System.out.println(returnedString);
		
		assertThat(returnedString, is(expectedString));
	}
	
	private List<Date> getDates() {
		return Arrays.asList(new Date(), null, new Date());
	}
	
	
}
