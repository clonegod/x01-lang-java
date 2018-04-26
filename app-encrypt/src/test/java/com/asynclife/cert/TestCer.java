package com.asynclife.cert;

import java.nio.ByteBuffer;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestCer {
	// 密钥库
	private static KeyStore keystore;
	// 密钥库密码
	private static String keystore_password = "123456";
	// 密钥的别名
	private static String alias = "www.hqh.com";

	// 证书
	private static Certificate certificate;

	// 私钥的密码
	private static String sec_password = "123456";

	// 公钥
	PublicKey publicKey;

	// 私钥
	PrivateKey privateKey;

	@Before
	public void setUp() throws Exception {
		keystore = JKSCertificate.getKeystoreFromFile(
				"src/main/resources/hqh.keystore", keystore_password);

		certificate = JKSCertificate
				.getCertificateFromKeystore(keystore, alias);

		publicKey = JKSCertificate.getPublicKey(certificate);

		privateKey = JKSCertificate
				.getPrivateKey(keystore, alias, sec_password);
	}

	/**
	 * 测试加密、解密
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEncrypt() throws Exception {
		System.err.println("公钥加密——私钥解密");

		String inputStr = "一段很重要的内容";
		byte[] data = inputStr.getBytes();

		byte[] encrypt = JKSCertificate.encryptByPublicKey(data, publicKey);

		byte[] decrypt = JKSCertificate
				.decryptByPrivateKey(encrypt, privateKey);
		String outputStr = new String(decrypt);

		System.err.println("加密前: " + inputStr + "\r\n" + "解密后: " + outputStr);

		// 验证数据一致
		Assert.assertTrue(ByteBuffer.wrap(data)
				.equals(ByteBuffer.wrap(decrypt)));

		// 验证证书有效
		Assert.assertTrue(JKSCertificate.verifyCertificate(new Date(),
				certificate));
	}

	/**
	 * 测试签名验证
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSign() throws Exception {
		System.err.println("私钥加密——公钥解密");

		String inputStr = "一段需要被签名的文字";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = JKSCertificate.encryptByPrivateKey(data, privateKey);

		byte[] decodedData = JKSCertificate.decryptByPublicKey(encodedData, publicKey);

		String outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "\r\n" + "解密后: " + outputStr);
		Assert.assertTrue(inputStr.equals(outputStr));

		
		System.err.println("私钥签名——公钥验证签名");
		// 产生签名
		String sign = JKSCertificate.sign(encodedData, certificate, privateKey);
		System.err.println("签名:\r" + sign);

		// 验证签名
		boolean status = JKSCertificate.verify(encodedData, sign, certificate);
		System.err.println("状态:\r" + status);
		Assert.assertTrue(status);

	}
}
