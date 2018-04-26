package clonegod.java.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test01Group {

	public static void main(String[] args) {
		// 按指定模式在字符串查找
		final String line = "This order was placed for QT3000! OK?";

		final String pattern01 = "(.*)(\\d+)(.*)";
		final String pattern02 = "(.*?)(\\d+)(.*)"; // 规定第1个组采用非贪婪模式进行匹配，这样第2组就能获取到全部的数字

		doMatch(line, pattern01);
		
		doMatch(line, pattern02);

	}

	private static void doMatch(final String line, final String pattern) {
		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);

		// 现在创建 matcher 对象
		Matcher m = r.matcher(line);

		System.out.println(m.groupCount());
		
		if (m.find()) {
			System.out.println("Found value: " + m.group(0));
			System.out.println("Found value: " + m.group(1));
			System.out.println("Found value: " + m.group(2));
		} else {
			System.out.println("NO MATCH");
		}
	}

}
