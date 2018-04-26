package clonegod.thread02.communication;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用synchronized结合wait,notify，模拟1个阻塞队列
 *
 * @param <T>
 */
public class MyQueue<T> {

	private LinkedList<T> list = new LinkedList<>(); // 存储队列元素的容器
	private final int minSize = 0; // 队列容量下限
	private final int maxSize; // 队列容量上限
	
	private AtomicInteger count = new AtomicInteger(0); // 维护队列中元素的个数
	
	private final Object lock = new Object(); // 锁
	
	
	public MyQueue(int size) {
		this.maxSize = size;
	}
	
	public int getSize() {
		return this.list.size();
	}
	
	public void put(T t) {
		synchronized (lock) {
			while(this.count.get() == this.maxSize) {
				try {
					lock.wait(); // 队列满，则等待
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			list.add(t); // 向队列加入元素
			count.incrementAndGet(); // 计数器递增
			System.out.println("新加入元素：" + t);
			
			lock.notify(); // 唤醒锁上的对方线程
		}
	}
	
	public T take() {
		T ret = null;
		synchronized (lock) {
			while(this.count.get() == this.minSize) {
				try {
					lock.wait(); // 队列为空，则等待
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ret = list.removeFirst(); // 从队列取出1个元素
			count.decrementAndGet(); // 计数器递减
			
			System.out.println("---移除队列元素：" + ret);
			lock.notify(); // 通知锁上的对方线程
		}
		
		return ret;
	}
	
	public static void main(String[] args) throws InterruptedException {
		final MyQueue<String> mq = new MyQueue<>(5);
		
		// 先把队列存满
		mq.put("a");
		mq.put("b");
		mq.put("c");
		mq.put("d");
		mq.put("e");
		
		System.out.println("当前队列长度：" + mq.getSize());
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				mq.put("f");
				mq.put("g");
			}
		}, "PutThread");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				mq.take();
				mq.take();
			}
		}, "TakeThread");
		
		t1.start();
		
		TimeUnit.SECONDS.sleep(2);
		
		t2.start();
	}
}
