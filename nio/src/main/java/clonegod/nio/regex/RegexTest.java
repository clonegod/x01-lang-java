package clonegod.nio.regex;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {
	
	@Test
	public void testEmail() {
		this.isVaildEmail("fkdsa@lj.co]");
	}
	
	public static final String VALID_EMAIL_PATTERN =
			"([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]"
					+ "{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))"
					+ "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)";

	public void isVaildEmail(String emailAddress) {
		if (emailAddress.matches(VALID_EMAIL_PATTERN)) {
			addEmailAddress(emailAddress);
		} else {
			throw new IllegalArgumentException(emailAddress);
		}
	}

	private void addEmailAddress(String emailAddress) {
		System.out.println("valid email address");
	}
	
	
	//===========================匹配字符串=============================
	
	@Test
	public void testYes() {
		boolean yes = goodAnswer("y");
		System.out.println(yes);
	}
	
	public boolean goodAnswer(String answer) {
		return (Pattern.matches("[Yy]es|[Yy]|[Tt]rue", answer));
	}
	
	
	//===========================匹配字符串=============================
	
	@Test
	public void testDot() {
		// 字符串中的.
		String str = "abc.def.hij";
		String arr[] = str.split("\\.");
		System.out.println(Arrays.toString(arr));
		
		// 正则中的.
		String reg = ".+"; // 正则表达式中的.表示任意字符
		System.out.println("abc".matches(reg));
	}
	
	//=========================切割字符串===============================
	
	@Test
	public void testSplitViaDuplicateChars() {
		String str = "fslkadj####fldsaajlf^^^^kdsja";
		
		// 使用"括号"对子表达式进行封装，作为1个组进行解析，使其具有复用性 
		// \\1 表示引用第1组的结果
		// + 表示至少出现1次
		String arr[] = str.split("(.)\\1+"); 
		System.out.println(Arrays.toString(arr));
	}
	
	//============================替换字符串============================
	
	@Test
	public void testReplace() {
		String str = "fslkadj####fldsaajlf^^^^kdsja";
		
		str = str.replaceAll("(.)\\1+", "$1"); // 对正则表达式中某个组所匹配到的结果进行引用
		System.err.println(str);
	}
	
	@Test
	public void testReplace2() {
		String telno = "13772390801";
		
		telno = telno.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		System.err.println(telno);
	}
	
	
	//============================获取字符串============================
	
	@Test
	public void testSearch() {
		String input = "abc";
		
		String reg = ".";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(input);
		
		while(m.find()) {
			System.out.println(m.group()+", start="+m.start()+", end="+m.end());
		}
		
		System.err.println(input);
	}
	
	@Test
	public void testSearch2() {
		String input = "hello world, hi java, ok";
		
		String regex = "\\b[a-z]{2}\\b";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		
		while(m.find()) {
			System.err.println(m.group());
		}
	}
	
	
	@Test
	public void test() {
		// 待排序的IP地址
		String[] ips = {"192.168.3.10", "127.0.0.1", "10.0.60.94", "215.2.3.123"};
		
		String ipsStr = Arrays.toString(ips);
		ipsStr = ipsStr.replaceAll("[\\[|\\]]", "");
		System.err.println(ipsStr);
		
		// 补0，让所有位都至少3个数字
		ipsStr = ipsStr.replaceAll("(\\d+)", "00$1");
		System.err.println(ipsStr);
		
		// 截取，让所有位都为3位数字
		ipsStr = ipsStr.replaceAll("0*(\\d{3})", "$1");
		System.err.println(ipsStr);
		
		// 排序
		TreeSet<String> set = new TreeSet<String>();
		set.addAll(Arrays.asList(ipsStr.split(",\\s*")));
		System.err.println(set);
		
		// 删除前缀0
		for(String ip : set) {
			String ipstr = ip.replaceAll("0*(\\d+)", "$1");
			System.err.println(ipstr);
		}
		
	}
}
