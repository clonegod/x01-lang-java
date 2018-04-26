package com.asynclife.encrypt;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

public class HashAlgorithm {

	private static final String KEY_MD5 = "MD5";
	private static final String KEY_SHA256 = "SHA-256";


	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 */
	public static String encryptMD5(String string) {
		try {
			MessageDigest m = MessageDigest.getInstance(KEY_MD5);
			byte[] bytes = string.getBytes();
			m.update(bytes, 0, bytes.length);
			BigInteger i = new BigInteger(1, m.digest());
			return String.format("%1$032x", i);
			/**
			 * %1$032x %1$ 第1个参数的占位符 0 若内容长度不足最小宽度，则在左边用0来填充 32 字符串宽度为32位 x
			 * 数据类型：16进制整数
			 */
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用DigestUtils
	 * 
	 * @param text
	 * @return
	 */
	public static String getMD5(String text) {
		return DigestUtils.md5Hex(text);
	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 */
	public static String encryptSHA256(String string) {
		try {
			MessageDigest m = MessageDigest.getInstance(KEY_SHA256);
			byte[] bytes = string.getBytes();
			m.update(bytes, 0, bytes.length);
			BigInteger i = new BigInteger(1, m.digest());
			return String.format("%1$032x", i);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用DigestUtils
	 * 
	 * @param text
	 * @return
	 */
	public static String getSHA256(String text) {
		return DigestUtils.sha256Hex(text);
	}

	public static String getSHA512(String text) {
		return DigestUtils.sha512Hex(text);
	}

	
	
	public static void main(String[] args) {
		
	}
	
	
	
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
