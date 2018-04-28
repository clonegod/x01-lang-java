package java8.core.concurrency;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Test00JavaThreadFeature {
	
	/**
	 * 前台线程 - 主线程结束后，有其它前台线程在运行，JVM不会退出
	 */
	@Test
	public void testFrontThread() {
		Runnable r = () -> {
			while(true) {
				System.out.println(Thread.currentThread().getName());
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread t = new Thread(r);
		t.setDaemon(false); // 线程默认就是前台模式运行
		t.start();
		
		System.out.println(t.isDaemon());
		System.out.println("Starting...");
	}
	
	
	
	
	/**
	 * 后台线程 - 主线程结束后，只有台线程在运行，JVM立即退出
	 */
	@Test
	public void testDaemonThread() {
		Runnable r = () -> {
			while(true) {
				System.out.println(Thread.currentThread().getName());
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread t = new Thread(r);
		t.setDaemon(true); // 设置为后台运行模式
		t.start();
		
		System.out.println(t.isDaemon());
		System.out.println("Starting...");
	}
	
}
