package com.hqh.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class AppUtil 
{
    public static URI getFileURI(String fileName) {
    	if(fileName == null)
    		throw new IllegalArgumentException("file name cann't be null");
    	URI uri = null;
    	try {
    		uri = AppUtil.class.getClassLoader().getResource(fileName).toURI();
		} catch (Exception e) {
			throw new RuntimeException("occur error: ", e);
		}
    	return uri;
    }
    
    public static InputStream loadFileAsInputStream(String fileName) {
    	if(fileName == null)
    		throw new IllegalArgumentException("file name cann't be null");
    	InputStream is = AppUtil.class.getClassLoader().getResourceAsStream(fileName);
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
    
    private static final String PATTERN_NYRSFM = "YYYY-MM-dd HH:mm:ss.SSS";
    private static final String PATTERN_NYR_SHORT = "YYYYMMdd";
    
	public static String parseCurrentDateTime(Date otherDate) {
		return new SimpleDateFormat(PATTERN_NYRSFM).format(otherDate);
	}
	
	public static String parseCurrentDateTime() {
		return parseCurrentDateTime(new Date());
	}
	
	public static String parseCurrentDate(Date otherDate) {
		return new SimpleDateFormat(PATTERN_NYR_SHORT).format(otherDate);
	}
	
	public static String parseCurrentDate() {
		return parseCurrentDate(new Date());
	}
    
	public static String formatCurrency(double amt) {
		return NumberFormat.getCurrencyInstance().format(amt);
	}
	
    public static void main(String[] args) {
		String fileName = "conf.properties";
		System.out.println(getFileURI(fileName));
		
		
	}
}
