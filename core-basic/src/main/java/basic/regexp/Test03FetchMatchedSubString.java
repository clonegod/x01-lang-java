package basic.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * 正则->获取
 * 
 * 通过正则表达式引擎获取匹配的字串
 */
public class Test03FetchMatchedSubString {
	
	
	/**
	 * String类中可以传入正则表达式的方法，其底层都是通过Pattern和Matcher来实现的！！！
	 * 只是String类将其封装到内部了，公布出简化的正则调用方式而已。
	 */
	@Test
	public void test() {
		String regex = "[1-9][0-9]{4,11}";
		String input = "1234a5";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		System.out.println(m.matches());
	}

	/**
	 * 获取匹配正则的字串
	 * 	m.find()	如果有某个字串匹配，则返回true
	 * 	m.group()	获取上一个匹配的字串
	 *  m.start()	匹配字串在字符串中的开始位
	 *  m.end()		匹配字串在字符串中的结束位
	 */
	@Test
	public void test02() {
		String text = "java is a good language, what do you think ?";
		String regex = "\\b[a-z]{4}\\b";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		
		// 类似迭代器的迭代
		while(m.find()) {
			System.out.println(m.group()+", start at:"+m.start()+", end at:"+m.end());
		}
		
	}
	
}
