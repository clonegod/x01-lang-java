package com.aysnclife.dataguru.jvm;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * 模拟StopTheWorld-STW现象
 *
 * JVM参数设置如下：
 * -Xms500M -Xmx500M 				堆总内存大小
 * -Xmn5M 							新生代内存大小
 * -XX:PretenureSizeThreshold=10 	设置多大的对象直接进入老年代(单位字节) 
 * -XX:MaxTenuringThreshold=0 		在新生代中对象存活次数(经过MinorGC的次数,默认15)后仍然存活，就会晋升到旧生代
 * -XX:+PrintGCDetails 				输出gc详细日志
 * -Xloggc:log/gc.log				重定向gc日志到文件
 */
public class JVM04_01 {
	
	private static final Collection<Object> leak = new ArrayList<>();
	
	private static volatile Object sink;

	public static void main(String[] args) {
		
		// 不断产生新对象
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					try {
						leak.add(new byte[1024 * 1024]);
						sink = new byte[1024 * 1024];
					} catch (OutOfMemoryError e) {
						leak.clear();
					}
				}
			}
		}).start();
		
		// 每100毫秒输出1次时间
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					System.out.println(
							MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss.SSS}", new Date())
							);
					sleep(100L);
				}
					
			}
		}).start();
	}
	
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
