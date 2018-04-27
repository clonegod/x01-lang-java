package demo.guava.basic.hash;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

/**
 * 好的哈希算法，避免哈希冲突---基于Hash表存储结构的HashMap中，如果存在大量哈希冲突会严重影响性能
 * 
 * @author clonegod@163.com
 *
 */
public class HashFunctionTest {
	
	/**
	 * well-known checksum algorithms, Adler-32 and CRC-32
	 */
	@Test
	public void testChecksum() throws IOException {
		HashFunction adler32 = Hashing.adler32();
		HashFunction crc32 = Hashing.crc32();
		
		System.out.println(adler32.hashBytes(getBytes()));
		System.out.println(crc32.hashBytes(getBytes()));
	}
	
	/**
	 * General hash functions are noncryptographic 
	 * and are well suited to be used for hash-based lookup tasks.
	 */
	@Test
	public void testGeneralHash() {
		HashFunction gfh = Hashing.goodFastHash(128); // For speed
		HashFunction murmur3_32 = Hashing.murmur3_32();
		HashFunction murmur3_128 = Hashing.murmur3_128();
		
		System.out.println(gfh.hashBytes(getBytes()));
		System.out.println(murmur3_32.hashBytes(getBytes()));
		System.out.println(murmur3_128.hashBytes(getBytes()));
	}
	
	/**
	 * cryptographic hash functions are used for information security.
	 */
	@Test
	public void testCryptographicHash() {
		HashFunction sha1 = Hashing.sha1(); // weak
		HashFunction sha256 = Hashing.sha256(); // For security
		HashFunction sha512 = Hashing.sha512();
		
		System.out.println(sha1.hashBytes(getBytes()));
		System.out.println(sha256.hashBytes(getBytes()));
		System.out.println(sha512.hashBytes(getBytes()));
	}
	
	public byte[] getBytes() {
		try {
			return Files.asByteSource(new File("tmp/out.txt")).read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
