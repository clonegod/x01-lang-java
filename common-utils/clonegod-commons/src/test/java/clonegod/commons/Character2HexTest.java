package clonegod.commons;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import clonegod.uitls.BinaryUtil;

public class Character2HexTest {
	
	@Test
	public void test11() {
		String hexStr = "0x11";
		Integer i = Integer.decode(hexStr);
		String binStr = Integer.toBinaryString(i);
		System.out.println(binStr);
		System.out.println(StringUtils.leftPad(binStr, 8, '0'));
		
		
		binStr = "00010001";
		int ii = Integer.parseInt(binStr, 2);
		System.out.println(ii);
	
		
		String temp = "ABFAB"; // without the "0x"
		String bytes = Integer.toBinaryString(Integer.parseInt(temp, 16)); 
		System.out.println(bytes);
	}
	
	@Test
	public void test022() throws Exception {
		String s = "13696084456";
		byte[] bytes = s.getBytes("GBK");
		System.out.println(Arrays.toString(bytes));
		
		// binary to hex
		System.out.println(bytesToHexString(bytes));
		
	}
	
	public static final String bytesToHexString(byte[] bArray) {
	    StringBuffer sb = new StringBuffer(bArray.length);
	    String sTemp;
	    for (int i = 0; i < bArray.length; i++) {
	     sTemp = Integer.toHexString(0xFF & bArray[i]);
	     if (sTemp.length() < 2)
	      sb.append(0);
	     sb.append(sTemp.toUpperCase());
	    }
	    return sb.toString();
	}
	

	
	private static final String ENCODEING = "GB18030";
	
	@Test
	public void test() throws Exception {
		// to hex
		String text = "13696084456";
		byte[] bytes = text.getBytes(ENCODEING);
		String hexStr = BinaryUtil.bytesToHexString(bytes);
		System.out.println(hexStr);
		
		// hex to source text
		byte[] bytes2 = BinaryUtil.hexStringToByte(hexStr);
		String text2 = new String(bytes2, ENCODEING);
		System.out.println(text2);
		
	}
	
	/**
	 * 字符串 <---> 16进制
	 */
	@Test
	public void test02() throws Exception {
		String text1 = "第1次,早期的鸟有虫吃，早起的虫被鸟吃，Thanks!1";
		byte[] bytes1 = text1.getBytes(ENCODEING);
		String hexStr = BinaryUtil.bytesToHexString(bytes1);
		System.out.println("明文--->"+text1);
		System.out.println("HEX --->"+hexStr);
		
		byte[] bytes2 = BinaryUtil.hexStringToByte(hexStr);
		String text2 = new String(bytes2, ENCODEING);
		System.out.println("还原--->"+text2);
	}
	
	
	/**
	 * 整数 <---> 16进制 
	 */
	@Test
	public void test03() {
		int number = 15;
		String hexStr = Integer.toHexString(number);
		hexStr = StringUtils.leftPad(hexStr, 2, '0');
		System.out.println("hexStr="+hexStr);

		int value = Integer.parseInt(hexStr, 16);
		System.out.println(value);
	}
	

}
