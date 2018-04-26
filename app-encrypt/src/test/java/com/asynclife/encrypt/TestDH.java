package com.asynclife.encrypt;

import java.util.Map;

import org.junit.Test;

public class TestDH {
	
	@Test
	public void test() throws Exception {
		// 路人甲
		Map<String,Object> aKeyMap = DH.initKey();
		String aPubKey = DH.getPublicKeyStr(aKeyMap);
		String aPriKey = DH.getPrivateKeyStr(aKeyMap);
		
		System.out.println("甲方公钥："+aPubKey);
		System.out.println("甲方私钥："+aPriKey);
		
		// 路人乙
		Map<String,Object> bKeyMap = DH.initKey(aPubKey);
		String bPubKey = DH.getPublicKeyStr(bKeyMap);
		String bPriKey = DH.getPrivateKeyStr(bKeyMap);
		
		System.out.println("乙方公钥："+bPubKey);
		System.out.println("乙方私钥："+bPriKey);
		
		// 测试开始
		String textForA = "乙方加密 ---> 甲方解密";
		System.out.println("原文："+textForA);
		
		// 乙方加密
		byte[] encodedByteFromB = DH.encrypt(textForA.getBytes(), aPubKey, bPriKey);
		// 甲方解密
		byte[] decodedByteFromA = DH.decrypt(encodedByteFromB, bPubKey, aPriKey);
		String decodedStrFromA = new String(decodedByteFromA);
		System.out.println("解密："+decodedStrFromA);
		
		
		String textForB = "甲方加密 ---> 乙方解密";
		System.out.println("原文："+textForB);
		
		// 甲方加密
		byte[] encodedByteFromA = DH.encrypt(textForB.getBytes(), bPubKey, aPriKey);
		// 乙方解密
		byte[] decodedByteFromB = DH.decrypt(encodedByteFromA, aPubKey, bPriKey);
		String decodedStrFromB = new String(decodedByteFromB);
		System.out.println("解密："+decodedStrFromB);
		
		
		
		
		
	}
	
}
