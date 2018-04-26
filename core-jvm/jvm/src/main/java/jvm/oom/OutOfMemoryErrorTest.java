package jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出
 * => java.lang.OutOfMemeoryError: Java heap space
 * 
 * 解决办法：为堆分配更大的内存
 * 
 *
 */
public class OutOfMemoryErrorTest {
	public static void main(String[] args) {
		List<byte[]> list = new ArrayList<byte[]>();
		// 每次创建1M的字节数组，循环1024次，导致堆内存溢出
		for(int i=0; i<1024; i++) {
			list.add(new byte[1024*1024]);
		}
	}
}
