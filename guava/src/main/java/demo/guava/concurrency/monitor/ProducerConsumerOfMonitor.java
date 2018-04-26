package demo.guava.concurrency.monitor;

import com.google.common.util.concurrent.Monitor;

public class ProducerConsumerOfMonitor {
	
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
	private volatile boolean flag;
	
	private Monitor monitor = new Monitor();
	private Monitor.Guard hasProduct = new Monitor.Guard(monitor) {
		@Override
		public boolean isSatisfied() {
			return flag;
		}
	};
	private Monitor.Guard noProduct = new Monitor.Guard(monitor) {
		@Override
		public boolean isSatisfied() {
			return !flag;
		}
	};
	
	
	public void produce(String name) throws InterruptedException {
		monitor.enterWhen(noProduct);
		try {
			this.name = name+"---"+(++count);
			System.out.println(Thread.currentThread().getName()+ " create product " + this.name);
			flag = true;
		} finally {
			monitor.leave();
		}
	}
	
	public void consume() throws InterruptedException {
		monitor.enterWhen(hasProduct);
		try {
			System.out.println(Thread.currentThread().getName()+ " 消费者  consume product " + this.name);
			flag = false;
		} finally {
			monitor.leave();
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
