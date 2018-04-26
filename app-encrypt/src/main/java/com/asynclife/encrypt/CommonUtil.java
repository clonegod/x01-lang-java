package com.asynclife.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class CommonUtil {
	
	/**
	 * 字节数组 to HEX
	 * 
	 * @param bytes
	 * @return
	 */
	public static String encodeHexString(byte[] bytes) {
		// DatatypeConverter.printHexBinary(bytes);
		return Hex.encodeHexString(bytes);
	}

	/**
	 * HEX to 字节数组
	 * 
	 * @param chars
	 * @return
	 */
	public static byte[] decodeHexString(char[] chars) {
		try {
			return Hex.decodeHex(chars);
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}
	}
}
