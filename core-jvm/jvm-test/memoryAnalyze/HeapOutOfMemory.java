package com.aysnclife.dataguru.jvm.memoryAnalyze;

import java.util.ArrayList;

/**
 * 堆内存溢出
 */
public class HeapOutOfMemory {
	public static void main(String[] args) {
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		for(int i=0; i<1000;i++) {
			list.add(new byte[1024*1024]);
		}
		System.out.println(list.size());
	}
}
