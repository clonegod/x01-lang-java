package juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * ReentrantReadWriteLock
 * 	读写锁：
 * 		读读共享，写写互斥，读写互斥
 * 
 *	适用于读多写少的场景：
 *		例如，更新缓存配置：少量更新配置，大量读。
 *
 */
public class TestReentrantReadWriteLock {

	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private ReadLock readLock = rwLock.readLock();
	private WriteLock writeLock = rwLock.writeLock();
	
	public void read(){
		try {
			readLock.lock();
			System.out.println("当前线程:" + Thread.currentThread().getName() + "进入...");
			Thread.sleep(3000);
			System.out.println("当前线程:" + Thread.currentThread().getName() + "退出...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			readLock.unlock();
		}
	}
	
	public void write(){
		try {
			writeLock.lock();
			System.out.println("当前线程:" + Thread.currentThread().getName() + "进入...");
			Thread.sleep(3000);
			System.out.println("当前线程:" + Thread.currentThread().getName() + "退出...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writeLock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		final TestReentrantReadWriteLock urrw = new TestReentrantReadWriteLock();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				urrw.read();
			}
		}, "t1-read");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				urrw.read();
			}
		}, "t2-read");
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				urrw.write();
			}
		}, "t3-write");
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {
				urrw.write();
			}
		}, "t4-write");		
		
		t1.start();
		t2.start();
		
//		t1.start(); // R 
//		t3.start(); // W
		
		t3.start();
		t4.start();
		
	}
}
