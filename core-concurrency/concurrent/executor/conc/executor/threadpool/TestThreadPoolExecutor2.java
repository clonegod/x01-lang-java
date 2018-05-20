package conc.executor.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 无界队列-对任务执行的影响
 * 	线程池中线程个数只受coreSize影响，maxSize参数无效。
 * 	队列无界，永远不会满，所以线程池最多创建coreSize个线程来处理任务。	
 *
 */
public class TestThreadPoolExecutor2 implements Runnable{

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
				new LinkedBlockingQueue<Runnable>(); // 无界队列
		ExecutorService executor  = new ThreadPoolExecutor(
					5, 		//core
					10, 	//max - 无界队列，此参数无效
					120L, 	//2分钟
					TimeUnit.SECONDS,
					queue);
		
		for(int i = 0 ; i < 20; i++){
			executor.execute(new TestThreadPoolExecutor2());
		}
		Thread.sleep(1000);
		System.out.println("queue size:" + queue.size());		//15
		
		executor.shutdown();
		executor.awaitTermination(2, TimeUnit.SECONDS);
	}


}
