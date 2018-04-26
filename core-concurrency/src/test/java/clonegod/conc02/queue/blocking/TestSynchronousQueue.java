package clonegod.conc02.queue.blocking;

import java.util.concurrent.SynchronousQueue;

import clonegod.concurrency.util.CommonUtil;

/**
 *  SynchronousQueue
 *  	没有内部缓冲队列，生产者产生的数据直接被消费者获取并消费。
 *  
 *  底层原理：
 *  	使用TransferQueue（公平）或TransferStack（非公平）将任务从生产者传递消费者线程。
 *  	注：公平与非公平的却别在于，是否按FIFO顺序或无序进行任务的处理。
 *  
 *  特点：
 *  	1、 内部不提供数据存储。
 *  	2、生产者往队列添加元素的前提，有空闲消费者线程正在等待处理任务。否则，生产者无法添加成功，会抛异常。
 *  		---生产者生产任务的速度不能超过消费者处理任务的速度，否则生产者提交任务会失败.
 *  适用场景：
 *  	并发量很低的时候，生产者生产的任务不会对消费者线程造成任何压力，因此，可将任务直接交给消费者线程来处理，没必要将任务放入队列缓存。
 *  	例如，晚上2-5点的车流浪非常小，没有限流的必要。---完全不需要内部提供队列缓冲。
 *  	Executors.newCachedThreadPool 内部使用SynchronousQueue也正可以应正这点。
 *
 */
public class TestSynchronousQueue {

	public static void main(String[] args) throws Exception {
		
		final SynchronousQueue<String> q = new SynchronousQueue<String>();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(q.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start(); // 必须先有消费者线程等待处理任务，生产者线程才可以传递任务。
		
		CommonUtil.sleep(1000);
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				q.add("asdasd"); // 必须事先有空闲的消费者线程等待处理数据，否则add会抛出异常---因为此queue没有内部缓存来存元素
			}
		});
		t2.start();		
	}
}
