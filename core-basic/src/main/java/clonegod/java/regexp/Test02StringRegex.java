package clonegod.java.regexp;

import java.util.Arrays;

import org.junit.Test;

/**
 * 正则->匹配
 * 正则->切割
 * 正则->替换
 * 
 * 字符串String类中有关正则的方法
 * 	matches	返回true/false
 *  split	返回数组
 *  replaceFirst 返回替换后的新字符串
 *  replaceAll 返回替换后的新字符串
 * 
 */
public class Test02StringRegex {
	
	/**
	 * String --- matches()
	 * qq校验规则：5-12位，第一位不能为0，全为数字
	 */
	@Test
	public void testQQ() {
		String regex = "[1-9][0-9]{4,11}";
		
		String qqNo = "1234a5";
		
		boolean isValid = qqNo.matches(regex);
		
		System.out.println(qqNo+" 通过验证:" + isValid);
		
	}
	
	/**
	 * String --- matches()
	 * 手机号码匹配
	 */
	@Test
	public void testTel() {
		String phoneNo = "15011586295";
		String regex = "1[3458]\\d{9}";
		
		boolean isValid = phoneNo.matches(regex);
		
		System.out.println(phoneNo+" 通过验证:" + isValid);
	}
	
	/**
	 * String--split()
	 * 	按指定规则切割字符串，对于特殊字符的切割，需要进行转义
	 */
	@Test
	public void testSplit() {
		String text = "abc.123.go";
		String regex = "\\.";
		String arr[] = text.split(regex);
		System.out.println(Arrays.toString(arr));
		
		text = "abc|123|go";
		regex = "\\|";
		arr = text.split(regex);
		System.out.println(Arrays.toString(arr));
		
		text = "abc  123 go";
		regex = "\\s+";
		arr = text.split(regex);
		System.out.println(Arrays.toString(arr));
		
		text = "c:\\demo\\1.txt";
		regex = "\\\\";
		arr = text.split(regex);
		System.out.println(Arrays.toString(arr));
	}
	
	/**
	 * String---split()
	 * 按叠词进行切割:为了让规则的结果可以被重用，需要将规则封装到1个组中，用()来实现组的封装。
	 * 组都是有编号的，从1开始。
	 * 要使用已有的组，通过\n（n为组的编号）的方式进行引用即可。
	 */
	@Test
	public void testSplitByDuplicate() {
		String text = "fkllsaanrlgglkdfkkkjfsd";
		String regex = "(.)\\1+";
		String arr[] = text.split(regex);
		System.out.println(Arrays.toString(arr));
	}
	
	/**
	 * String---replaceAll
	 * 替换
	 */
	@Test
	public void testReplace() {
		final String text = "abcdeefghijjklmnoppqrstuuvwxyz";
		
		String regex = "(.)\\1+"; //匹配叠词
		String replacement = "#"; //替换规则：将叠词替换为#
		String newText = text.replaceFirst(regex, replacement); //只替换字符串中第一个匹配的字串
		System.out.println(newText);
		
		regex = "(.)\\1+"; // 匹配叠词
		replacement = "$1"; //替换规则： 动态引用正则表达式中的第一个组中的结果作为替换词
		newText = text.replaceAll(regex, replacement);// 对整个字符串中符合规则的字串进行替换
		System.out.println(newText);
	}
	
}
