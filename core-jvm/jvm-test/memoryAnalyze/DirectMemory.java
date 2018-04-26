package com.aysnclife.dataguru.jvm.memoryAnalyze;

import java.nio.ByteBuffer;

/**
 * 分配直接内存，内存超出限制后不会触发GC
 *	-Xmx1g -XX:+PrintGCDetails
 */
public class DirectMemory {
	public static void main(String[] args) {
		for(int i=0;i<2000;i++) {
			ByteBuffer.allocateDirect(1024*1024);
			System.out.println(i);
			System.gc();
		}
	}
}
