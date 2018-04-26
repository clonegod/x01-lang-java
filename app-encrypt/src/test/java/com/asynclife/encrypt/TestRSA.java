package com.asynclife.encrypt;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestRSA {
	
	static String publicKey;
	static String privateKey;
	
	@BeforeClass
	public static void initKeyPair() {
		
		Map<String, Object> keyPairMap = RSA.genKeyPair();
		
		publicKey = RSA.getPublicKey(keyPairMap);
		privateKey = RSA.getPrivateKey(keyPairMap);
		
		System.out.println("公钥:\n"+publicKey);
		System.out.println("私钥:\n"+privateKey);
		
	}
	
	@Test
	public void testEncodeByPublicKey() {
		String text = "公钥加密--->私钥解密";
		
		byte[] encrypted = RSA.encryptByPublicKey(publicKey, text.getBytes());
		byte[] decrypted = RSA.decryptByPrivateKey(privateKey, encrypted);
		
		String output = new String(decrypted);
		
		System.out.println("加密前："+text);
		System.out.println("解密后："+output);
	}
	
	
	@Test
	public void testEncodeByPrivateKey() {
		String text = "私钥加密--->公钥解密";
		
		byte[] encrypted = RSA.encryptByPrivateKey(privateKey, text.getBytes());
		byte[] decrypted = RSA.decryptByPublicKey(publicKey, encrypted);
		
		String output = new String(decrypted);
		
		System.err.println("加密前："+text);
		System.err.println("解密后："+output);
		
		
		System.err.println("【私钥签名——公钥验证签名】");
		
		// 传入密文、私钥，得到签名
		String base64Sign = RSA.sign(encrypted, privateKey);
		System.out.println(base64Sign);
		
		// 传入密文、签名、公钥，验证签名
		boolean result = RSA.verify(encrypted, base64Sign, publicKey);
		System.err.println("是否一致："+result);
		
	}
	
	
}
