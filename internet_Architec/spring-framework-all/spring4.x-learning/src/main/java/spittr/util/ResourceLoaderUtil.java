package spittr.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;

public class ResourceLoaderUtil {
	
	private static final String DEFAULT_ENCODINNG = "UTF-8";
	
	public static String loadFromClasspath(String resourcePath) throws IOException {
		return loadFromClasspath(resourcePath, DEFAULT_ENCODINNG);
	}
	
	/**
	 * 使用ClassPathResource加载类路径下的资源文件。
	 * 使用EncodedResource设置资源文件的编码，避免编码不一致出现乱码。
	 * 
	 * @param resourcePath
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String loadFromClasspath(String resourcePath, String charset) throws IOException {
		String content = null;
		
		Resource resource = new ClassPathResource(resourcePath);
		EncodedResource encRes = new EncodedResource(resource, Charset.forName(charset));
		
//		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
//		StreamUtils.copy(encRes.getInputStream(), os);
//		content = new String(os.toByteArray());
		
		content = FileCopyUtils.copyToString(encRes.getReader());
		
		return content;
	}
	
	/**
	 * 从classpath下加载文件的字节数组
	 * 
	 * @param resourcePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] loadBytesFromClasspath(String resourcePath) throws IOException {
		byte[] bytes;
		
		Resource resource = new ClassPathResource(resourcePath);
		InputStream in = resource.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream(in.available());
		StreamUtils.copy(in, out);
		
		bytes = out.toByteArray();
		return bytes;
	}
	
	public static String loadFromFileSystem(String filepath) throws IOException {
		return loadFromFileSystem(filepath, DEFAULT_ENCODINNG);
	}
	
	/**
	 * 
	 * @param filepath
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String loadFromFileSystem(String filepath, String charset) throws IOException {
		String content = null;
		
		Resource resource = new FileSystemResource(filepath); 
		EncodedResource encRes = new EncodedResource(resource, Charset.forName(charset));
		content = FileCopyUtils.copyToString(encRes.getReader());
		
		return content;
	}
	
	/**
	 * JDK提供的加载本地资源文件的工具ResourceBundle
	 * 
	 * @param path	本地properties文件相对于classpath的路径，只需要资源文件名称，不需要后缀.properties
	 * @param key
	 * @return
	 */
	public static String getValueUseResourceBundle(String path, String key) {
		if(path.endsWith(".properties")) {
			path = path.replace(".properties", "");
		}
		ResourceBundle rb = ResourceBundle.getBundle(path, Locale.CHINA);
		String value = rb.getString(key);
		return value;
	}
	
	public static void main(String[] args) throws IOException {
		
		System.out.println(loadFromClasspath("spring/data-access.properties"));
		
		System.out.println(loadFromFileSystem("E:/source/practice-java/spring4.x-learning/src/main/resources/log4j.properties"));
		
		System.out.println(getValueUseResourceBundle("spring/data-access", "jdbc.url"));
	}
}
