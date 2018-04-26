package com.asynclife.encrypt;

import org.junit.Test;

public class TestTripleDES {
	
	@Test
	public void test3DES() throws Exception {
         
		String text = "一段文字";
		
		sop("加密前："+text);

		byte[] keyBytes = SymmetryCrypt.genMyTripleDESKey(168, "my simple seed for 3des".getBytes());
		
		byte[] srcBytes = text.getBytes("UTF-8");
		byte[] encodedBytes = SymmetryCrypt.encrypt3DES(SymmetryCrypt.KEY_TRIPLE_DES, keyBytes, srcBytes);
		String hexStr = CommonUtil.encodeHexString(encodedBytes);
		sop("加密后的字符串："+hexStr);
		
		byte[] hex2Bytes = CommonUtil.decodeHexString(hexStr.toCharArray());
		byte[] decodedBytes = SymmetryCrypt.decrypt3DES(SymmetryCrypt.KEY_TRIPLE_DES, keyBytes, hex2Bytes);
		String decodeStr = new String(decodedBytes, "UTF-8");
		sop("解密后："+decodeStr);
		//4dfe0108a97363173416c5faa6e43e77
	}

	private void sop(String string) {
		System.out.println(string);
	}
	
}
