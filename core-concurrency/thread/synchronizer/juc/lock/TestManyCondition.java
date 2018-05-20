package juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import conc.util.CommonUtil;

/**
 * Lock下多个Condition的使用
 *	Lock下可以创建不同的Condition，线程可在不同的condition下被阻塞和唤醒。
 *	注意：不同condition下的线程都受同一个lock锁控制
 *	- condition 线程在获取到锁之后，在某个condition下阻塞或唤醒对应线程。
 *	- 被唤醒的线程不能立即得到执行，仍然需要获取到lock锁，之后才能继续执行。
 *	- 发出唤醒通知的线程，在发出通知后，其它线程还不能立即获取到lock锁，直到该线程调用unlock()释放锁，
 *
 */
public class TestManyCondition {

	private ReentrantLock lock = new ReentrantLock();
	private Condition c1 = lock.newCondition();
	private Condition c2 = lock.newCondition();
	
	public void m1(){
		try {
			lock.lock();
			System.out.println("当前线程：" +Thread.currentThread().getName() + "进入方法m1等待..");
			c1.await(); // 在c1的锁上，等待被唤醒
			System.out.println("当前线程：" +Thread.currentThread().getName() + "方法m1继续..");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonUtil.sleep(10000);
			System.out.println("当前线程：" +Thread.currentThread().getName() + "方法m1，等待该锁的线程数：" + lock.getQueueLength());
			System.out.println("当前线程：" +Thread.currentThread().getName() + "方法m1释放锁..");
			lock.unlock();
		}
	}
	
	public void m2(){
		try {
			lock.lock();
			System.out.println("当前线程：" +Thread.currentThread().getName() + "进入方法m2等待..");
			c1.await(); // 在c1的锁上，等待被唤醒
			System.out.println("当前线程：" +Thread.currentThread().getName() + "方法m2继续..");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("当前线程：" +Thread.currentThread().getName() + "方法m2，等待该锁的线程数：" + lock.getQueueLength());
			lock.unlock();
		}
	}
	
	public void m3(){
		try {
			lock.lock();
			System.out.println("当前线程：" +Thread.currentThread().getName() + "进入方法m3等待..");
			c2.await(); // 在c2的锁上，等待被唤醒
			System.out.println("当前线程：" +Thread.currentThread().getName() + "方法m3继续..");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("当前线程：" +Thread.currentThread().getName() + "方法m3，等待该锁的线程数：" + lock.getQueueLength());
			lock.unlock();
		}
	}
	
	public void m4(){
		try {
			lock.lock();
			System.out.println("当前线程：" +Thread.currentThread().getName() + "唤醒..t1,t2");
			c1.signalAll(); // 唤醒在c1的锁上阻塞的所有线程
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void m5(){
		try {
			lock.lock();
			System.out.println("当前线程：" +Thread.currentThread().getName() + "唤醒..t3");
			c2.signal(); // 唤醒在c2的锁上阻塞的1个线程
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		
		final TestManyCondition umc = new TestManyCondition();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				umc.m1();
			}
		},"t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				umc.m2();
			}
		},"t2");
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				umc.m3();
			}
		},"t3");
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {
				umc.m4();
			}
		},"t4");
		Thread t5 = new Thread(new Runnable() {
			@Override
			public void run() {
				umc.m5();
			}
		},"t5");
		
		t1.start();	// c1
		t2.start();	// c1
		t3.start();	// c2
		

		CommonUtil.sleep(2000);
		t4.start();	// c1
		
		CommonUtil.sleep(2000);
		t5.start();	// c2
		
	}
	
	
	
}
