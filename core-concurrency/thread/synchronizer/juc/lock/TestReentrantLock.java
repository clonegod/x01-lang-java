package juc.lock;

import java.util.concurrent.locks.ReentrantLock;

import conc.util.CommonUtil;

/**
 * ReentrantLock
 *	- 提供比synchronized更灵活的锁方案，性能比synchronized更好。
 *	- 关键：必须在finally中释放锁
 */
public class TestReentrantLock {
	
	private ReentrantLock lock = new ReentrantLock();
	
	public void method1(){
		try {
			lock.lock();
			System.out.println("当前线程:" + Thread.currentThread().getName() + "进入method1..");
			Thread.sleep(1000);
			System.out.println("当前线程:" + Thread.currentThread().getName() + "退出method1..");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 最终都要释放锁
			lock.unlock();
		}
	}
	
	public void method2(){
		try {
			lock.lock();
			System.out.println("当前线程:" + Thread.currentThread().getName() + "进入method2..");
			Thread.sleep(2000);
			System.out.println("当前线程:" + Thread.currentThread().getName() + "退出method2..");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 最终都要释放锁
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final TestReentrantLock instance = new TestReentrantLock();
		for(int i = 0; i < 3; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					instance.method1();
					instance.method2();
				}
			}, "t"+i).start();
		}
		
		CommonUtil.sleep(10);
		System.out.println("waiting lock threads: " + instance.lock.getQueueLength());
		
	}
	
}
