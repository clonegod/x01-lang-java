package com.asynclife.encrypt;

import org.junit.Assert;
import org.junit.Test;

public class TestMD5 {
	
	/**
	 * 验证MD5对于同一内容加密是否一致  
	 */
	@Test
	public void test() throws Exception {
		String text = "一段文字";
		String str1 = HashAlgorithm.encryptMD5(text);
		String str2 = HashAlgorithm.encryptMD5(text);
		
		Assert.assertEquals(str1, str2);
		
		System.out.println(str1);
		System.out.println(str2);
		
		
		String md5Str = HashAlgorithm.getMD5(text);
		System.out.println(md5Str);
	}
	
}
