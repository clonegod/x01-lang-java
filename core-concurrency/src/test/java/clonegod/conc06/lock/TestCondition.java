package clonegod.conc06.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import clonegod.concurrency.util.CommonUtil;

/**
 * Condition
 *	- lock 配合condition提供更灵活的线程交互方式。
 * 	- 在lock上创建得到Condition对象，线程间在具体的condition下阻塞与唤醒。
 */
public class TestCondition {

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public void consume(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入等待状态..");
			Thread.sleep(3000);
			System.out.println("当前线程：" + Thread.currentThread().getName() + "释放锁..");
			condition.await();	// Object wait
			System.out.println("当前线程：" + Thread.currentThread().getName() +"继续执行...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void produce(){
		try {
			lock.lock();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "进入..");
			Thread.sleep(3000);
			//发出通知，但是被通知的阻塞线程不能立即执行，仍然需要等待当前线程释放锁。
			condition.signal();
			System.out.println("当前线程：" + Thread.currentThread().getName() + "发出唤醒..");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CommonUtil.sleep(5000);
			System.out.println("producer exit， release lock");
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		
		final TestCondition test = new TestCondition();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				test.consume();
			}
		}, "consumer");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				test.produce();
			}
		}, "producer");
		
		t1.start();
		t2.start();
	}
	
	
	
}
