package baeldung.queue.SynchronousQueue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HandoffsUsingCountDownLatch {
	/**
	 * 使用CountDownLatch协调生产者线程与消费者线程
	 * 1. 生产者生成数据，放入共享资源对象
	 * 2. 消费者等待
	 * 3. 消费者消费数据
	 */
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		AtomicInteger sharedState = new AtomicInteger();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		Runnable producer = () -> {
		    Integer producedElement = ThreadLocalRandom
		      .current()
		      .nextInt();
		    sharedState.set(producedElement);
		    System.out.println("Saving an element: " + producedElement + " to the exchange point");
		    countDownLatch.countDown();
		};
		
		Runnable consumer = () -> {
		    try {
		    	// 等待生产者生成数据，然后再获取数据
		        countDownLatch.await();
		        Integer consumedElement = sharedState.get();
		        System.out.println("Consumed an element: " + consumedElement + " from the exchange point");
		    } catch (InterruptedException ex) {
		        ex.printStackTrace();
		    }
		};
		
		executor.execute(producer);
		executor.execute(consumer);
		 
		executor.awaitTermination(500, TimeUnit.MILLISECONDS);
		executor.shutdown();
	}
}
