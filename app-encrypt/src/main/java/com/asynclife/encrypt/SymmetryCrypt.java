package com.asynclife.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SymmetryCrypt {

	public static final String KEY_TRIPLE_DES = "DESede"; // 3 DES
	public static final String KEY_AES = "AES";

	/**
	 * keysize: must be equal to 112 or 168 seed: 指定1个seed，确保每次加密、解密使用的都是同一个key
	 * 
	 * @param seed
	 * @return
	 */
	public static byte[] genMyTripleDESKey(int keySize, byte[] seed) {
		if (keySize != 112 && keySize != 168) {
			throw new RuntimeException(
					"3DES keysize: must be equal to 112 or 168");
		}
		try {
			SecureRandom secureRandom = null;
			if (seed != null) {
				secureRandom = new SecureRandom(seed);
			} else {
				secureRandom = new SecureRandom();
			}
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_TRIPLE_DES);
			kgen.init(keySize, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			return keyBytes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * AES keysize: must be equal to 128, 192 or 256（仅keysize=128测试成功，192和256报错）
	 * seed: 指定1个seed，确保每次加密、解密使用的都是同一个key
	 * 
	 * @param seed
	 * @return
	 */
	public static byte[] genMyAESKey(int keySize, byte[] seed) {
		if (keySize != 128 && keySize != 192 && keySize != 256) {
			throw new RuntimeException(
					"AES keysize: must be equal to 128, 192 or 256");
		}
		try {
			SecureRandom secureRandom = null;
			if (seed != null) {
				secureRandom = new SecureRandom(seed);
			} else {
				secureRandom = new SecureRandom();
			}
			KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
			kgen.init(keySize, secureRandom);
			SecretKey secretKey = kgen.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			return keyBytes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] encrypt3DES(String algorithm, byte[] keybyte,
			byte[] src) {
		return symmetryEncrypt(algorithm, keybyte, src);
	}

	public static byte[] decrypt3DES(String algorithm, byte[] keybyte,
			byte[] src) {
		return symmetryDecrypt(algorithm, keybyte, src);
	}

	public static byte[] encryptAES(String algorithm, byte[] keybyte, byte[] src) {
		return symmetryEncrypt(algorithm, keybyte, src);
	}

	public static byte[] decryptAES(String algorithm, byte[] keybyte, byte[] src) {
		return symmetryDecrypt(algorithm, keybyte, src);
	}

	private static byte[] symmetryEncrypt(String algorithm, byte[] keybyte,
			byte[] src) {
		try {
			SecretKey desKey = new SecretKeySpec(keybyte, algorithm);
			Cipher ci = Cipher.getInstance(algorithm);
			ci.init(Cipher.ENCRYPT_MODE, desKey);
			return ci.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] symmetryDecrypt(String algorithm, byte[] keybyte,
			byte[] src) {
		try {
			SecretKey desKey = new SecretKeySpec(keybyte, algorithm);
			Cipher ci = Cipher.getInstance(algorithm);
			ci.init(Cipher.DECRYPT_MODE, desKey);
			return ci.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	public static void main(String...args) throws Exception {
		String text = "一段文字";
		
		sop("加密前："+text);

		byte[] keyBytes = genMyAESKey(128, "my simple seed for aes".getBytes());
		
		byte[] srcBytes = text.getBytes("UTF-8");
		byte[] encodedBytes = encryptAES(KEY_AES, keyBytes, srcBytes);
		String hexStr = CommonUtil.encodeHexString(encodedBytes);
		sop("加密后的字符串："+hexStr);
		
		byte[] hex2Bytes = CommonUtil.decodeHexString(hexStr.toCharArray());
		byte[] decodedBytes = decryptAES(KEY_AES, keyBytes, hex2Bytes);
		String decodeStr = new String(decodedBytes, "UTF-8");
		sop("解密后："+decodeStr);
		//3516587e767912edcb20f08a2d85a199
	}

	private static void sop(String string) {
		System.out.println(string);
	}

}
