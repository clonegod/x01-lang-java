package com.asynclife.cert;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.crypto.Cipher;

import com.asynclife.encrypt.Base64Util;

/**
 * JKS - Java Key Store 密钥和证书管理组件
 * 
 * 用javaAPI操作密钥库，从中提取私钥和公钥等内容
 * 
 * @author Administrator
 *
 */
public class JKSCertificate {

	public static final String KEY_STORE = "JKS";
	public static final String X509 = "X.509";

	// 密钥库
	// private static KeyStore keystore;
	// 密钥库密码
	// private static String keystore_password = "123456";
	// 密钥的密码
	// private static String sec_password = "123456";

	// 密钥的别名
	// private static String alias = "www.hqh.com";

	// 证书
	// private static Certificate certificate;

	/**
	 * 从文件加载keystore
	 */
	public static KeyStore getKeystoreFromFile(String keystorePath, String password)
			throws Exception {
		KeyStore ks = KeyStore.getInstance(KEY_STORE);

		FileInputStream is = new FileInputStream(keystorePath);
		ks.load(is, password.toCharArray());

		is.close();

		return ks;
	}

	/**
	 * 从keystore加载证书
	 */
	public static Certificate getCertificateFromKeystore(KeyStore keystore, String alias) throws Exception {
		Certificate certificate = keystore.getCertificate(alias);
		return certificate;
	}

	/**
	 * 直接加载证书文件
	 */
	public static Certificate getCertificateFromFile(String certificatePath)
			throws Exception {
		CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
		
		FileInputStream in = new FileInputStream(certificatePath);

		Certificate certificate = certificateFactory.generateCertificate(in);
		in.close();

		return certificate;
	}

	/**
	 * 从keystore获取密钥
	 */
	public static PrivateKey getPrivateKey(KeyStore keystore, String alias,
			String secPassword) throws Exception {
		PrivateKey key = (PrivateKey) keystore.getKey(alias, secPassword.toCharArray());
		return key;
	}

	/**
	 * 从证书加载公钥
	 * 
	 * @param certificate
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(Certificate certificate)
			throws Exception {
		PublicKey key = certificate.getPublicKey();
		return key;
	}

	
	/**
	 * 私钥加密
	 * @param data	待加密数据
	 * @param privateKey 私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 私钥加密
	 * @param data	待解密数据
	 * @param privateKey 私钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥加密
	 * @param data	待解密数据
	 * @param privateKey 公钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥解密
	 * @param data	待解密数据
	 * @param privateKey 公钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	 /** 
     * 验证证书是否过期或无效 
     *  
     * @param date 
     * @param certificate 
     * @return 
     */  
    public static boolean verifyCertificate(Date date, Certificate certificate) {  
        boolean status = true;  
        try {
            X509Certificate x509Certificate = (X509Certificate) certificate;  
            x509Certificate.checkValidity(date);  
        } catch (Exception e) {  
            status = false;  
        }  
        return status;  
    }  
	
    /**
     * 服务器签名
     */
    public static String sign(byte[] sign, Certificate cert, PrivateKey privateKey) throws Exception {  
        X509Certificate x509Certificate = (X509Certificate)cert;
        
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());  
        signature.initSign(privateKey);  
        signature.update(sign);  
        
        return Base64Util.encodeBase64Str(signature.sign());  
    }
    
    /**
     * 验证服务器签名
     */
    public static boolean verify(byte[] data, String sign,  
            Certificate cert) throws Exception {  
        // 获得证书  
        X509Certificate x509Certificate = (X509Certificate) cert;  
        // 获得公钥  
        PublicKey publicKey = x509Certificate.getPublicKey();  
        // 构建签名  
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());  
        signature.initVerify(publicKey);  
        signature.update(data);  
  
        return signature.verify(Base64Util.decodeBase64(sign));  
  
    }  
}
