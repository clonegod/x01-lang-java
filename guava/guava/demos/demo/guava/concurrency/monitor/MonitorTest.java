package demo.guava.concurrency.monitor;

import java.util.ArrayList;
import java.util.List;

import com.google.common.util.concurrent.Monitor;

/**
 * 多线程操作共享资源，两个核心要素：
 * 	1. 获取到锁
 *  2. 被操作的资源满足某个条件（如消费者必须在队列有元素存在时才能执行消费）
 *  
 * Monitor与Guard组合在一起，协调线程间的执行
 * 与JDK提供的Condition相比，简化了操作共享资源的代码：
 * 1. 不需要在await外部使用while循环中检查条件
 * 2. 不需要显示signal通知对方线程---对方线程自行调用isSatisfied方法来检查条件是否满足
 * 
 * monitor.enter/enterIf/enterWhen 都重载了超时版本的方法
 * monitor.tryEnter/tryEnterIf 非阻塞，所以没有超时版本的方法
 * 
 * @author clonegod@163.com
 *
 */
public class MonitorTest {

	private List<String> list = new ArrayList<String>();
	private static final int MAX_SIZE = 10;
	
	// Monitor -> acquire the lock 
	private Monitor monitor = new Monitor();
	// Guard -> check condition to be satisfied
	private Monitor.Guard listBelowCapacity = new Monitor.Guard(monitor) {
		@Override
		public boolean isSatisfied() {
			return list.size() < MAX_SIZE;
		}
	};

	/**
	 * Enters this monitor when the guard is satisfied. Blocks indefinitely, but may be interrupted.
	 * 当条件满足时，进入监视器。否则，无限阻塞，但可能被其它线程中断。
	 */
	public void addToListOfEnterWhen(String item) throws InterruptedException {
		// 1. 无限等待锁 	2. 无限等待condition满足
//		monitor.enterWhen(listBelowCapacity, 3, TimeUnit.SECONDS);
		monitor.enterWhen(listBelowCapacity);
		try {
			list.add(item);
		} finally {
			monitor.leave();
		}
	}
	
	/**
	 * enterIf: 
	 * 	a. first enter the monitor 
	 * 	b. second check weather condition satisfied or not and return immediately 
	 * 
	 * 1. Blocks indefinitely acquiring the lock, 
	 * 无限阻塞直到进入监视器
	 * 2. but does not wait for the guard to be satisfied.
	 * 一旦进入到监视器，不等待条件满足，而是返回一个布尔值：
	 * 	如果值为true，则条件满足，并且已经进入到监视器，否则，进入监视器失败。
	 * 
	 */
	public void addToListOfEnterIf(String item) throws InterruptedException {
		// 1. 无限等待锁 	2. 不等待condition满足
//		monitor.enterIf(listBelowCapacity, 3, TimeUnit.SECONDS);
		while(! monitor.enterIf(listBelowCapacity)) {
			Thread.sleep(10);
		}
		try {
			list.add(item);
		} finally {
			monitor.leave();
		}
	}
	
	/**
	 * Enters this monitor if it is possible to do so immediately and the guard is satisfied. 
	 * Does not block acquiring the lock and does not wait for the guard to be satisfied. 
	 * 
	 * 当可以获取到锁并且condition满足时，立即进入监视器；
	 * 不会阻塞式等待获取锁，不会阻塞等待资源条件得到满足；
	 * 
	 * 返回true，表示已经进入到监视器，并且条件是满足的；
	 * 返回false，表示未成功获取锁，或者资源操作条件不满足；
	 */
	public void addToListOfTryEnterIf(String item) throws InterruptedException {
		// 1. 不等待锁 	2. 不等待condition满足
		while(! monitor.tryEnterIf(listBelowCapacity)) {
			Thread.sleep(10);
		}
		try {
			list.add(item);
		} finally {
			monitor.leave();
		}
	}
	
	/**
	 * 无Condition约束，阻塞式等待进入监视器
	 * 
	 * Enters this monitor. Blocks indefinitely.
	 */
	public void addToListOfEnter(String item) throws InterruptedException {
		// 一直阻塞等待，直到进入监视器
		monitor.enter();
//		monitor.enter(1, TimeUnit.SECONDS);
		try {
			list.add(item);
		} finally {
			monitor.leave();
		}
	}
	
	/**
	 * 无Condition约束，非阻塞，等待进入监视器
	 * 
	 * Enters this monitor if it is possible to do so immediately. Does not block. 
	 */
	public void addToListOfTryEnter(String item) throws InterruptedException {
		// 非阻塞等待，快速返回是否进入监视器成功
		while(! monitor.tryEnter()) {
			Thread.sleep(10);
		}
		try {
			list.add(item);
		} finally {
			monitor.leave();
		}
	}
	
}
