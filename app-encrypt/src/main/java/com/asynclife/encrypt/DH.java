package com.asynclife.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/**
 * DH加密算法
 * 	Diffie-Hellman算法(D-H算法)，密钥一致协议。是由公开密钥密码体制的奠基人Diffie和Hellman所提出的一种思想。
 * 简单的说就是允许两名用户在公开媒体上交换信息以生成"一致"的、可以共享的密钥。
 * 换句话说，就是由甲方产出一对密钥（公钥、私钥），乙方依照甲方公钥产生乙方密钥对（公钥、私钥）。
 * 以此为基线，作为数据传输保密基础，同时双方使用同一种对称加密算法构建本地密钥（SecretKey）对数据加密。
 * 这样，在互通了本地密钥（SecretKey）算法后，甲乙双方公开自己的公钥，使用对方的公钥和刚才产生的私钥加密数据，同时可以使用对方的公钥和自己的私钥对数据解密。
 * 不单单是甲乙双方两方，可以扩展为多方共享数据通讯，这样就完成了网络交互数据的安全通讯！该算法源于中国的同余定理——中国馀数定理。
 * @author Administrator
 *
 */
public class DH {
	
	public static final String ALGORITHM = "DH";  
	private static final int KEY_SIZE_DES = 1024;  
	
	//DH加密下需要一种对称加密算法对数据加密 
	public static final String SECRET_ALGORITHM_DES = "DES";  
	
    private static final String PUBLIC_KEY = "DHPublicKey";  
    private static final String PRIVATE_KEY = "DHPrivateKey";  
	

    /**
     * 甲方
     * 初始化密钥
     * @return 
     * @throws Exception 
     */
    public static Map<String, Object> initKey() throws Exception {
    	KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
    	
    	generator.initialize(KEY_SIZE_DES);
    	
    	KeyPair keyPair = generator.generateKeyPair();
    	
    	DHPublicKey dhPubKey = (DHPublicKey) keyPair.getPublic();
    	DHPrivateKey dhPriKey = (DHPrivateKey) keyPair.getPrivate();
    	
    	Map<String, Object> keyMap = new HashMap<String, Object>();
    	keyMap.put(PUBLIC_KEY, dhPubKey);
    	keyMap.put(PRIVATE_KEY, dhPriKey);
    	
    	return keyMap;
    }
    
    /**
     * 乙方
     * 根据甲方的公钥生成乙方的密钥对
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey(String otherPubKey) throws Exception {
    	
    	//解析甲方公钥
    	byte[] encodedKey = Base64Util.decodeBase64(otherPubKey);
    	KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
    	X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(encodedKey);
    	PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
    	
    	//构建乙方密钥对
    	DHParameterSpec dhParaSpec = ((DHPublicKey)pubKey).getParams();
    	KeyPairGenerator generator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
    	generator.initialize(dhParaSpec);
    	
    	KeyPair keyPair = generator.generateKeyPair();
    	DHPublicKey dhPubKey = (DHPublicKey) keyPair.getPublic();
    	DHPrivateKey dhPriKey = (DHPrivateKey) keyPair.getPrivate();
    	
    	Map<String, Object> keyMap = new HashMap<String, Object>();
    	keyMap.put(PUBLIC_KEY, dhPubKey);
    	keyMap.put(PRIVATE_KEY, dhPriKey);
    	
    	return keyMap;
    }
	
	/**
	 * 
	 * @param data
	 * @param otherPubKey 对方的公钥
	 * @param priKey	自己的私钥
	 * @return
	 * @throws Exception 
	 */
    public static byte[] encrypt(byte[] data, String otherPubKey, String priKey) throws Exception {
    	// 本地密钥
    	SecretKey secretKey = getSecretKey(otherPubKey, priKey);
    	
    	// 数据加密
    	Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
    	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    	
    	return cipher.doFinal(data);
    }
    
    /**
     * 
     * @param data
     * @param otherPubKey
     * @param priKey
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String otherPubKey, String priKey) throws Exception {
    	// 本地密钥
    	SecretKey secretKey = getSecretKey(otherPubKey, priKey);
    	
    	// 数据解密
    	Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
    	cipher.init(Cipher.DECRYPT_MODE, secretKey);
    	
    	return cipher.doFinal(data);
    }

    /**
     * 由对方的公钥和自己的私钥，构建本地密钥对
     * @param otherPubKeyStr
     * @param priKeyStr
     * @return
     * @throws Exception
     */
	private static SecretKey getSecretKey(String otherPubKeyStr, String priKeyStr) throws Exception {

		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		
		// 公钥
		byte[] pubKeyBytes = Base64Util.decodeBase64(otherPubKeyStr);
    	X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKeyBytes);
    	PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		
		// 私钥
		byte[] priKeyBytes = Base64Util.decodeBase64(priKeyStr);
		PKCS8EncodedKeySpec pkcs8Spec = new PKCS8EncodedKeySpec(priKeyBytes);
		Key priKey = keyFactory.generatePrivate(pkcs8Spec);
		
		KeyAgreement keyAgree = KeyAgreement.getInstance(keyFactory.getAlgorithm());
		keyAgree.init(priKey);
		keyAgree.doPhase(pubKey, true);
    	
		// 生成本地密钥
		SecretKey secretKey = keyAgree.generateSecret(SECRET_ALGORITHM_DES); //指定对称加密算法
		
		return secretKey;
	}
	
	/**
	 * 获取私钥
	 * @param keyMap
	 * @return
	 */
	public static String getPrivateKeyStr(Map<String,Object> keyMap) {
		
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		
		return Base64Util.encodeBase64Str(key.getEncoded());
	}
	
	/**
	 * 获取公钥
	 * @param keyMap
	 * @return
	 */
	public static String getPublicKeyStr(Map<String,Object> keyMap) {
		
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		
		return Base64Util.encodeBase64Str(key.getEncoded());
	}
	
	
	
}
