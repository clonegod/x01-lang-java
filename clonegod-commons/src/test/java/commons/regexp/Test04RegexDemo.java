package commons.regexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

/**
 * 正则综合练习
 *
 */
public class Test04RegexDemo {
	
	/**
	 * 替换字符与叠词的处理
	 */
	@Test
	public void test01() {
		String text = "我我我...要要....学学学学学...编..编编...编编编编...程...程";
		
		text = text.replaceAll("\\.", ""); // 替换.为空
		text = text.replaceAll("(.)\\1+", "$1"); // 叠词去重
		
		System.out.println(text);
	}
	
	
	/**
	 * IP地址排序
	 */
	@Test
	public void test02() {
		String ips = "192.168.1.254 10.23.12.012 10.10.10.10 2.2.2.2 8.100.90.20";
		
		ips = ips.replaceAll("(\\d+)", "00$1");
		System.out.println(ips);
		
		ips = ips.replaceAll("0*(\\d{3})", "$1");
		System.out.println(ips);
		
		String[] ipArray = ips.split("\\s+");
		System.out.println(Arrays.toString(ipArray));
		
		List<String> list = new ArrayList<String>();
		
		list.addAll(Arrays.asList(ipArray));
		
		Collections.sort(list);
		
		ListIterator<String> iter = list.listIterator();
		while(iter.hasNext()) {
			String ip = iter.next();
			iter.set(ip.replaceAll("0*(\\d+)", "$1"));
		}
		
		for(String ip : list) {
			System.out.println(ip);
		}
		
	}
	
	/**
	 * 校验邮箱地址
	 */
	@Test
	public void testEmail() {
		String mail = "abc@sina.com.cn";
		String regex = "[a-zA-Z0-9_]+@[a-zA-Z0-9]+(\\.[a-zA-Z]+){1,3}";
		//String regex = "\\w+@\\w+(\\.\\w+)+"; //不精确的匹配
		boolean match = mail.matches(regex);
		System.out.println(match);
	}
	
}
