package thread.basic;

/**
 * 多生产者多消费者的线程并发访问控制：
 * 	1. 必须用while循环来检查线程被唤醒后的条件检查；
 *  2. 必须用notifyAll()来唤醒“等待线程池”中的线程，如果用notify()唤醒线程，则可能唤醒到本分线程，导致程序一直wait()。
 *  
 *  待优化的地方：
 *  1. 使用notifyAll()不仅唤醒了对方线程，还把本方线程也唤醒了，造成了不必要的竞争；
 *  2. 只唤醒对方线程，不唤醒本方线程。
 *
 */
public class Test06ProducerAndConsumer {
	
	public static void main(String[] args) {
		
		// 共享资源
		Resource res = new Resource();
		
		// 多生产者
		Thread producer01 = new Thread(new Producer(res));
		Thread producer02 = new Thread(new Producer(res));
		producer01.start();
		producer02.start();
		
		// 多消费者
		Thread consumer01 = new Thread(new Consumer(res));
		Thread consumer02 = new Thread(new Consumer(res));
		consumer01.start();
		consumer02.start();
	}
}

/**
 * 共享资源
 *
 */
class Resource {
	private String name;
	private int count = 0;
	private boolean flag;
	
	public synchronized void produce(String name) {
		while(flag) { // 被notify()唤醒后，必须继续判断条件是否满足
			try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
		}
		this.name = name+"---"+(++count);
		System.out.println(Thread.currentThread().getName()+ " create product " + this.name);
		flag = true;
		this.notifyAll();
	}
	
	public synchronized void consume() {
		while(! flag) { // 被notify()唤醒后，必须继续判断条件是否满足
			try { this.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
		}
		System.out.println(Thread.currentThread().getName()+ " 消费者  consume product " + this.name);
		flag = false;
		this.notifyAll();
	}
	
}

/**
 * 生产者
 */
class Producer implements Runnable {
	Resource res;
	
	public Producer(Resource res) {
		super();
		this.res = res;
	}

	public void run() {
		for(;;)
			res.produce("商品");
	}
}

/**
 * 消费者
 */
class Consumer implements Runnable {
	
	Resource res;
	
	public Consumer(Resource res) {
		super();
		this.res = res;
	}
	public void run() {
		for(;;)
			res.consume();
	}
}
