package com.asynclife.encrypt;

import org.junit.Assert;
import org.junit.Test;

public class TestBase64 {
	
	/**
	 * 验证BASE64编码、解码一致性  
	 */
	@Test
	public void test() {
		String srcText = "abcd";
		String base64Str = new String(Base64Util.encodeBase64(srcText.getBytes()));// YWJjZA==
		String decryptStr = new String(Base64Util.decodeBase64(base64Str)); // abcd
		Assert.assertEquals(srcText, decryptStr);
	}
	
	/**
	 * 验证字符不足3个字节，则用0填充，输出字符使用'='
	 * 因此编码后输出的文本末尾可能会出现1或2个'='
	 */
	@Test
	public void testAdditional() {
		String srcText = "d";
		String base64Str = new String(Base64Util.encodeBase64(srcText.getBytes())); // ZA==
		String decryptStr = new String(Base64Util.decodeBase64(base64Str));
		Assert.assertTrue(srcText.equals(decryptStr));
	}
	
}
