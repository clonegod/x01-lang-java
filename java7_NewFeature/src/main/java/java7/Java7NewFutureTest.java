package java7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class Java7NewFutureTest {
	
	/**
	 * 新特性1
	 * switch支持的类型:
	 * 	byte,short,int,char,string,enum
	 * */
	@Test
	public void testSwitch() {
		String dayOfWeek = "Monday";
		switch(dayOfWeek) {
			case "Saturday": 
			case "Sunday": System.out.println("休息"); break;
			default:
				System.out.println("上班"); break;
		}
	}
	
	/**
	 * 新特性2
	 * 数字与二进制的改进表达方式
	 */
	@Test
	public void testNumber() {
		long num = 100_000_000;
		
		int bitPattern = 0b11110000;
		
		System.out.println(num);
		
		System.out.println(bitPattern);
	}
	
	/**
	 * 新特性3
	 * 对捕获异常进行分组归类
	 */
	@Test
	public void testCatchGroupException() {
		getConfig("abc.txt");
	}
	public Configuration getConfig(String fileName) {
		Configuration cfg = null;
		
		try {
			String fileText = getFile(fileName);
			cfg = verifyConfig(parseConfig(fileText));
		} catch (FileNotFoundException | ParseException | ConfigurationException  e) { 
			// 将同类异常归类到一起进行处理
			System.err.println("Config file '" + fileName + "' is missing or malformed.");
		} catch (IOException e) {
			System.err.println("Error while processing file '" + fileName + "'");
		}
		
		return cfg;
	}
	
	/**
	 * 新特性4
	 * try-with-resource
	 * 帮助开发人员正确关闭IO资源，TWR特性依靠一个新定义的接口AutoCloseable来实现
	 */
	@Test
	public void testTryWithResource() throws MalformedURLException {
		File saveTo = new File("target/out.txt");
		URL url = new URL("http://www.xyz.com");
		try (OutputStream out = new FileOutputStream(saveTo);
				InputStream in = url.openStream()) {
			byte[] buf = new byte[4096];
			int len = 0;
			while((len = in.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新特性5
	 * 泛型类型推断-简化类型声明
	 */
	@Test
	public void testTypeInfer() {
		Map<Integer, Map<String,String>> userLists = new HashMap<>(); // 编译器自动推断右边的泛型类型
		System.out.println(userLists);
	}
	
	/**
	 * 新特性6
	 * @SafeVarargs
	 */
	@Test
	public void testSafeVarargs() {
		doit(1, 2, 3);
	}
	
	@SafeVarargs
	public final <T> Collection<T> doit(T...entries) {
		return null;
		
	}
	
	
	private Configuration verifyConfig(Object parseConfig) throws ConfigurationException {
		throw new ConfigurationException();
	}

	private Configuration parseConfig(String fileText) throws ParseException {
		throw new ParseException("", 0);
	}

	private String getFile(String fileName) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		String content = new String(Files.readAllBytes(file.toPath()));
		return content;
	}

	class ConfigurationException extends Exception {}
	
	class Configuration {}
}
