package com.asynclife.encrypt;

import java.util.Map;

import org.junit.Test;

public class TestDSA {
	
	@Test
	public void test() throws Exception {
		String text = "一段需要数字签名的文字";
		byte[] data = text.getBytes();
		
		// 初始化密钥对
		Map<String, Object> keyMap = DSA.initKey();
		
		// 获取公钥、私钥
		String pubKey = DSA.getPublicKey(keyMap);
		String priKey = DSA.getPrivateKey(keyMap);
		
		// 签名
		String signedStr = DSA.sign(data, priKey);
		
		System.out.println("签名："+signedStr);
		
		// 验证
		boolean status = DSA.verify(data, pubKey, signedStr);
		System.out.println("签名是否一致：" + status);
		
		
		// 中途篡改数据
		data[0] = 'A';
		status = DSA.verify(data, pubKey, signedStr);
		System.out.println("篡改数据后，签名是否一致：" + status);
		
		
	}
	
	
}
