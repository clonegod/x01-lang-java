package conc.executor.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 有界队列-对任务执行的影响
 *	coreSize, maxSize, queueSize 都影响任务的执行
 */
public class TestThreadPoolExecutor1 implements Runnable{

	private static AtomicInteger count = new AtomicInteger(0);
	
	@Override
	public void run() {
		try {
			int temp = count.incrementAndGet();
			System.out.println("任务" + temp);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
		//System.out.println(Runtime.getRuntime().availableProcessors());
		BlockingQueue<Runnable> queue = 
				new ArrayBlockingQueue<Runnable>(10); // 有界队列
		ExecutorService executor  = new ThreadPoolExecutor(
					5, 		//core
					10, 	//max
					120L, 	//2分钟
					TimeUnit.SECONDS,
					queue);
		
		for(int i = 0 ; i < 20; i++){
			executor.execute(new TestThreadPoolExecutor1());
		}
		Thread.sleep(1000);
		System.out.println("queue size:" + queue.size());		//10
		
		executor.shutdown();
		executor.awaitTermination(2, TimeUnit.SECONDS);
	}


}
