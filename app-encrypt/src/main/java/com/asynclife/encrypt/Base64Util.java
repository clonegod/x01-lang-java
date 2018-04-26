package com.asynclife.encrypt;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
	/**
	 * BASE64编码字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] encodeBase64(byte[] bytes) {
		return Base64.encodeBase64(bytes);
	}
	
	public static String encodeBase64Str(byte[] bytes) {
		return new String(encodeBase64(bytes));
	}

	/**
	 * BASE64解码字符串
	 * 
	 * @param base64Str
	 * @return
	 */
	public static byte[] decodeBase64(String base64Str) {
		return Base64.decodeBase64(base64Str);
	}
	
}
