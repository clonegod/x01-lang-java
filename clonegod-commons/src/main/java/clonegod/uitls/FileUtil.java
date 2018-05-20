package clonegod.uitls;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.commons.io.IOUtils;

public class FileUtil 
{
    public static InputStream getInputStreamFromClasspath(String filename) {
    	
    	return FileUtil.class.getClassLoader().getResourceAsStream(filename);
    }
    
    public static String toString(InputStream is) throws IOException {
    	return IOUtils.toString(is, "UTF-8");
    }
    
    public static void copyByCharset(InputStream fis, String inCharset, 
    		OutputStream fos, String outCharset) throws IOException {
		// 输入流的字符编码表
		InputStreamReader isr = new InputStreamReader(fis, inCharset);
		
		// 输出流的字符编码表
		OutputStreamWriter osw = new OutputStreamWriter(fos, outCharset);
		
		IOUtils.copy(isr, osw);
		osw.flush();
		
		IOUtils.closeQuietly(isr);
		IOUtils.closeQuietly(osw);
    }
    
    public static URI getFileURI(String fileName) {
    	if(fileName == null)
    		throw new IllegalArgumentException("file name cann't be null");
    	URI uri = null;
    	try {
    		uri = FileUtil.class.getClassLoader().getResource(fileName).toURI();
		} catch (Exception e) {
			throw new RuntimeException("occur error: ", e);
		}
    	return uri;
    }
    
    public static InputStream loadFileAsInputStream(String fileName) {
    	if(fileName == null)
    		throw new IllegalArgumentException("file name cann't be null");
    	InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
    	return is;
    }
    
    /**
     * Properties文件内的中文直接读取时乱码的解决办法：
     * 	先用ISO8859-1得到原始的字节数组
     * 	再用UTF-8进行字符编码即可 
     */
    public static Object toChineseCharacter(Object value) {
    	if(value == null)
    		throw new IllegalArgumentException("property value cann't be null");
    	
    	String newValue = null;
		try {
			byte[] bytes = value.toString().getBytes("iso8859-1");
			newValue = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("deconding error: ", e);
		}
		return newValue;
    }
    
    public static void main(String[] args) {
		String fileName = "conf.properties";
		System.out.println(getFileURI(fileName));
	}
}
