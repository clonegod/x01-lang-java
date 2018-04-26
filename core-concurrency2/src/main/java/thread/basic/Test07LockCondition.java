package thread.basic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JDK1.5 引入的新特性：
 * Lock 锁 - 显示锁
 * 	同一个Lock锁上可以绑定多个Condition对象。
 *  等待与唤醒机制被封装到Condition对象上，
 * 	通过condition对象来唤醒对应的等待线程，避免了notifyAll唤醒本方线程的问题。
 */
public class Test07LockCondition {
	
	public static void main(String[] args) {
		
		// 共享资源
		MyResource res = new MyResource();
		
		// 多生产者
		Thread producer01 = new Thread(new MyProducer(res));
		Thread producer02 = new Thread(new MyProducer(res));
		producer01.start();
		producer02.start();
		
		// 多消费者
		Thread consumer01 = new Thread(new MyConsumer(res));
		Thread consumer02 = new Thread(new MyConsumer(res));
		consumer01.start();
		consumer02.start();
	}
}

/**
 * 共享资源
 *
 */
class MyResource {
	private String name;
	private int count = 0;
	private boolean flag;
	
	// JDK1.5提供的替代synchronized同步锁的方案
	Lock lock = new ReentrantLock();
	Condition con_producer = lock.newCondition(); // 同1个锁可以创建若干个Condition
	Condition con_consumer = lock.newCondition();
	
	public void produce(String name) throws InterruptedException {
		lock.lock();
		
		try {
			while(flag) {
				con_producer.await();
			}
			this.name = name+"---"+(++count);
			System.out.println(Thread.currentThread().getName()+ " create product " + this.name);
			flag = true;
			
			con_consumer.signal();
			
		} finally {
			lock.unlock();
		}
		
	}
	
	public void consume() throws InterruptedException {
		lock.lock();
		try {
			while(! flag) {
				con_consumer.await();
			}
			System.out.println(Thread.currentThread().getName()+ " 消费者  consume product " + this.name);
			flag = false;
			
			con_producer.signal();
			
		} finally {
			lock.unlock();
		}
		
	}
	
}

/**
 * 生产者
 */
class MyProducer implements Runnable {
	MyResource res;
	
	public MyProducer(MyResource res) {
		super();
		this.res = res;
	}

	public void run() {
		while(true) {
			try {
				res.produce("商品");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

/**
 * 消费者
 */
class MyConsumer implements Runnable {
	
	MyResource res;
	
	public MyConsumer(MyResource res) {
		super();
		this.res = res;
	}
	public void run() {
		for(;;) {
			try {
				res.consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
