package com.asynclife.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSA extends Base64Util {
	// ---> RSA BEGIN
		public static final String KEY_RSA = "RSA";
		public static final String PUBLIC_KEY = "RSAPublicKey";
		public static final String PRIVATE_KEY = "RSAPrivateKey";
		public final static String SIGNATURE_ALGORITHM = "MD5withRSA";
		
		public static Map<String, Object> genKeyPair() {
			Map<String, Object> keyPairMap = new HashMap<String, Object>();
			
			try {
				KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_RSA);
				keyPairGen.initialize(1024);
				
				KeyPair pair = keyPairGen.generateKeyPair();
				
				RSAPublicKey publicKey = (RSAPublicKey) pair.getPublic();
				RSAPrivateKey pirvateKey = (RSAPrivateKey) pair.getPrivate();
				
				keyPairMap.put(PUBLIC_KEY, publicKey);
				keyPairMap.put(PRIVATE_KEY, pirvateKey);
				return keyPairMap;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}
		
		/**
		 * 获取RSA加密算法的公钥
		 * @param keyMap
		 * @return BASE64编码后的公钥
		 */
		public static String getPublicKey(Map<String, Object> keyMap) {
			Key key = (Key) keyMap.get(PUBLIC_KEY);  
			byte[] pubKeyBytes = key.getEncoded();
			byte[] base64 = encodeBase64(pubKeyBytes);
	        return new String(base64);
		}
		
		/**
		 * 获取RSA加密算法的私钥
		 * @param keyMap
		 * @return BASE64编码后的私钥
		 */
		public static String getPrivateKey(Map<String, Object> keyMap) {
			Key key = (Key) keyMap.get(PRIVATE_KEY);  
			byte[] privateKeyBytes = key.getEncoded();
			byte[] base64 = encodeBase64(privateKeyBytes);
	        return new String(base64);
		}
		
		/**
		 * 私钥对数据进行加密
		 * @param base64PrivateKey
		 * @param srcData
		 * @return
		 */
		public static byte[] encryptByPrivateKey(String base64PrivateKey, byte[] srcData) {
			try {
				Key privateKey = getPrivateKey(base64PrivateKey);
				
				// 对数据进行加密
				Cipher ci = Cipher.getInstance(privateKey.getAlgorithm());
				ci.init(Cipher.ENCRYPT_MODE, privateKey);
				return ci.doFinal(srcData);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * 私钥对数据进行解密
		 * @param base64PrivateKey
		 * @param srcData
		 * @return
		 */
		public static byte[] decryptByPrivateKey(String base64PrivateKey, byte[] srcData) {
			try {
				Key privateKey = getPrivateKey(base64PrivateKey);
				
				// 对数据进行加密
				Cipher ci = Cipher.getInstance(privateKey.getAlgorithm());
				ci.init(Cipher.DECRYPT_MODE, privateKey);
				return ci.doFinal(srcData);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		
		/**
		 * 公钥对数据进行加密
		 * @param base64PublicKey
		 * @param srcData
		 * @return
		 */
		public static byte[] encryptByPublicKey(String base64PublicKey, byte[] srcData) {
			try {
				Key privateKey = getPublicKey(base64PublicKey);
				
				// 对数据进行加密
				Cipher ci = Cipher.getInstance(privateKey.getAlgorithm());
				ci.init(Cipher.ENCRYPT_MODE, privateKey);
				return ci.doFinal(srcData);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * 公钥对数据进行解密
		 * @param base64PublicKey
		 * @param srcData
		 * @return
		 */
		public static byte[] decryptByPublicKey(String base64PublicKey, byte[] srcData) {
			try {
				Key privateKey = getPublicKey(base64PublicKey);
				
				// 对数据进行加密
				Cipher ci = Cipher.getInstance(privateKey.getAlgorithm());
				ci.init(Cipher.DECRYPT_MODE, privateKey);
				return ci.doFinal(srcData);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private static Key getPublicKey(String base64PublicKey)
				throws NoSuchAlgorithmException, InvalidKeySpecException {
			// BASE64解码得到公钥
			byte[] keyBytes = decodeBase64(base64PublicKey);
			
			// 获取公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);
			return publicKey;
		}
		
		private static Key getPrivateKey(String base64PrivateKey)
				throws NoSuchAlgorithmException, InvalidKeySpecException {
			// BASE64解码得到私钥
			byte[] keyBytes = decodeBase64(base64PrivateKey);
			
			// 获取私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
			return privateKey;
		}
		
		/**
		 * 用私钥对信息生成数字签名 
		 * @param base64PrivateKey 私钥的base64字符串
		 * @param data	加密后的密文
		 * @return
		 */
		public static String sign(byte[] data, String base64PrivateKey) {
			try {
				// 私钥
				PrivateKey prikey = (PrivateKey) getPrivateKey(base64PrivateKey);
				
				// 签名
				Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
				signature.initSign(prikey);
				signature.update(data);
				
				byte[] base64Bytes = encodeBase64(signature.sign());
				String base64Sign = new String(base64Bytes);
				return base64Sign;
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
		}
		
		public static boolean verify(byte[] data, String sign, String base64PublicKey) {
			
			// 公钥
			try {
				PublicKey pubKey = (PublicKey) getPublicKey(base64PublicKey);
				Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
				signature.initVerify(pubKey);
				signature.update(data);
				
				return signature.verify(decodeBase64(sign));
			} catch (Exception e) {
				throw new RuntimeException(e);
				
			}
			
		}
		
		// <--- RSA END
}
