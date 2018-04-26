package com.asynclife.encrypt;

import org.junit.Assert;
import org.junit.Test;

public class TestSHA {
	
	/**
	 * 验证SHA对于同一内容加密是否一致  
	 */
	@Test
	public void test() throws Exception {
		String text = "一段文字";
		
		String str1 = HashAlgorithm.encryptSHA256(text);
		String str2 = HashAlgorithm.getSHA256(text);
		
		Assert.assertEquals(str1, str2);
		
		System.out.println(str1);
		System.out.println(str2);
		
		String str3 = HashAlgorithm.getSHA512(text);
		System.out.println(str2.length());
		System.out.println(str3.length());
	}
	
}
