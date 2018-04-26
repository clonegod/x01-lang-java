package com.aysnclife.dataguru.jvm;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class JVM03 {
	/*
	 * 	【堆/新生代】
	 *  def new generation   total 6464K, used 115K [0x34e80000, 0x35580000, 0x35580000)
	 *  	eden space 5760K,   2% used [0x34e80000, 0x34e9cd38, 0x35420000)
	 *  	from space 704K,   0% used [0x354d0000, 0x354d0000, 0x35580000)
	 *  	to   space 704K,   0% used [0x35420000, 0x35420000, 0x354d0000)
	 *  【堆/老年代】
	 *  tenured generation   total 18124K, used 8277K [0x35580000, 0x36733000, 0x37680000)
	 *   	the space 18124K,  45% used [0x35580000, 0x35d95758, 0x35d95800, 0x36733000)
	 *  
	 *  【永久区/方法区】
	 *  compacting perm gen  total 16384K, used 16383K [0x37680000, 0x38680000, 0x38680000)
	 *  	the space 16384K,  99% used [0x37680000, 0x3867ffc0, 0x38680000, 0x38680000)
	 *  	ro space 10240K,  44% used [0x38680000, 0x38af73f0, 0x38af7400, 0x39080000)
	 *  	rw space 12288K,  52% used [0x39080000, 0x396cdd28, 0x396cde00, 0x39c80000)
	 * */
	// -XX:+TraceClassLoading	输出系统启动时所有被加载的类
	// -Xloggc:logpath	重定向gc日志输出到文件。 如，-Xloggc:/gclog/classloading.log 将日志输出到工作目录下gclog目录下的classloading.log文件
	// -XX:+PrintGCDetails	程序运行结束后打印堆的垃圾清理详细日志
	// -Xmx40M -Xmn40M -Xmn7M -XX:+PrintGCDetails -Xloggc:log/gc.log
//	public static void main(String[] args) throws Exception {
//		int sizeNew = (0x35580000 - 0x34e80000) / 1024 / 1024;
//		System.out.println("size of new generation:"+sizeNew);
//		
//		int sizeOld = (0x37680000 - 0x35580000) / 1024 / 1024;
//		System.out.println("size of old generation:"+sizeOld);
//		
//		int sizeHeap = sizeNew+sizeOld; 
//		System.out.println("size of heap:"+sizeHeap);
//		
//		System.out.println("go");
//		List<Byte[]> datas = new ArrayList<Byte[]>();
//		for(int i=0;i<1;i++) {
//			datas.add(new Byte[10*1024*1024]);
//		}
//		
//		
//	}
	// -Xmx40m -Xms40m -XX:SurvivorRatio=4.5 -XX:+PrintGCDetails

	public static void main(String[] args) {
		   byte[] b=null;
		   for(int i=0;i<10;i++)
		       b=new byte[3*1024*1024];
		}

}
