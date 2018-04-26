package com.asynclife.encrypt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * DSA-Digital Signature Algorithm 
 * 私钥加密生成数字签名，公钥验证数据及签名。如果数据和签名不匹配则认为验证失败！
 * 数字签名的作用就是校验数据在传输过程中不被修改。数字签名，是单向加密的升级！
 * @author Administrator
 *
 */
public class DSA {

	public static final String ALGORITHM = "DSA";  
	private static final int KEY_SIZE = 1024;  
	private static final String DEFAULT_SEED = "123FSDA3244FSDFSWREE23EWQ432";
	
	private static final String PUBLIC_KEY = "DSAPublicKey";  
	private static final String PRIVATE_KEY = "DSAPrivateKey";
	
	
	/**
	 * 生成密钥
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey(String seed) throws Exception {  
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);  
        // 初始化随机产生器  
        SecureRandom secureRandom = new SecureRandom();  
        secureRandom.setSeed(seed.getBytes());  
        keygen.initialize(KEY_SIZE, secureRandom);  
  
        KeyPair keys = keygen.genKeyPair();  
  
        DSAPublicKey publicKey = (DSAPublicKey) keys.getPublic();  
        DSAPrivateKey privateKey = (DSAPrivateKey) keys.getPrivate();  
  
        Map<String, Object> map = new HashMap<String, Object>(2);  
        map.put(PUBLIC_KEY, publicKey);  
        map.put(PRIVATE_KEY, privateKey);  
  
        return map;  
    }  
	
	/** 
     * 根据默认种子生成密钥 
     */  
    public static Map<String, Object> initKey() throws Exception {  
        return initKey(DEFAULT_SEED);  
    }  
    
    /** 
     * 获取私钥 
     */  
    public static String getPrivateKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PRIVATE_KEY);  
  
        return Base64Util.encodeBase64Str(key.getEncoded());  
    }  
  
    /** 
     * 获取公钥 
     */  
    public static String getPublicKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PUBLIC_KEY);  
  
        return Base64Util.encodeBase64Str(key.getEncoded());  
    }  
    
    /**
     * 私钥签名
     * 
     * @param data	待签名的数据
     * @param privateKey 签名者的私钥
     * @return 返回签名
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {  
        // 解密由base64编码的私钥  
        byte[] keyBytes = Base64Util.decodeBase64(privateKey);  
  
        // 构造PKCS8EncodedKeySpec对象  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
  
        // KEY_ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);  
  
        // 取私钥匙对象  
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);  
  
        // 用私钥对信息生成数字签名  
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());  
        signature.initSign(priKey);  
        signature.update(data);  
  
        return Base64Util.encodeBase64Str(signature.sign());  
    }  
    
    /**
     * 公钥对数字签名进行验证
     * 
     * @param data 已签名的数据
     * @param publicKey 签名者的公钥
     * @param sign 签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)  
            throws Exception {  
  
        // 解密由base64编码的公钥  
        byte[] keyBytes = Base64Util.decodeBase64(publicKey);  
  
        // 构造X509EncodedKeySpec对象  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
  
        // ALGORITHM 指定的加密算法  
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);  
  
        // 取公钥匙对象  
        PublicKey pubKey = keyFactory.generatePublic(keySpec);  
  
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());  
        signature.initVerify(pubKey);  
        signature.update(data);  
  
        // 验证签名是否正常  
        return signature.verify(Base64Util.decodeBase64(sign));  
    }  
    
    
	
}
