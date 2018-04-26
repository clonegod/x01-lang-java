package clonegod.conc04.design.producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class TestProducerConsumer {

	public static void main(String[] args) throws Exception {
		//生产者和消费者共享的内存缓冲区
		BlockingQueue<Data> queue = new LinkedBlockingQueue<Data>(10);
		
		//生产者
		Provider p1 = new Provider(queue);
		Provider p2 = new Provider(queue);
		Provider p3 = new Provider(queue);
		
		//消费者
		Consumer c1 = new Consumer(queue);
		Consumer c2 = new Consumer(queue);
		Consumer c3 = new Consumer(queue);
		
		// 线程池可创建线程上限无界；空闲线程存活时间为60s（默认值）；缓冲队列：SynchronousQueue，
		ExecutorService cachePool = Executors.newCachedThreadPool();
		
		cachePool.execute(p1);
		cachePool.execute(p2);
		cachePool.execute(p3);
		cachePool.execute(c1);
		cachePool.execute(c2);
		cachePool.execute(c3);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		p1.stop();
		p2.stop();
		p3.stop();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//cachePool.awaitTermination(1, TimeUnit.SECONDS);
		//cachePool.shutdown(); // 关闭线程池：拒绝新任务的提交，等待正在执行的任务完成任务后才退出。
		
		// 立即关闭线程池：向线程池中所有线程发出中断，不等待正在执行任务的线程执行完成。
		//---执行任务的线程需要针对中断进行有效处理（比如跳出无限循环），这样才能实现线程的退出。
		cachePool.shutdownNow(); 
	}
	
}
