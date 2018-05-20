package conc.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 线程池工厂方法
 *	创建不同特性的线程池：
 *		SingleThreadExecutor	
 *			仅有1个线程的线程池
 *			内部使用LinkedBlockingQueue
 *			返回的是一个代理：FinalizableDelegatedExecutorService-在finalize方法中调用了poll.shutdown()关闭线程池。
 *		FixedThreadPool			
 *			需要指定线程池大小
 *			固定大小的线程池
 *			内部使用LinkedBlockingQueue
 *		CachedThreadPool		
 *			线程池内部最小线程数为0，最大线程数无上限
 *			可多态伸缩的线程池（超时控制），根据任务的多少动态调整线程池中线程的个数
 *			内部使用SynchronousQueue
 *			空闲线程超时60秒后被自动回收
 *		ScheduledThreadPool
 *			需要指定线程池大小
 *			可执行延时任务的线程池
 *			内部使用DelayedWorkQueue
 *			空闲线程超时0秒后被自动回收
 *
 *	注意：被回收的线程，指的是超过coreSize后的那部分线程，且线程状态处于idle时，才会被回收。
 */
public class TestExecutors {
	
	public static void main(String[] args) {
		
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
		
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);
		
		
		singleThreadExecutor.shutdown();
		fixedThreadPool.shutdown();
		cachedThreadPool.shutdown();
		scheduledThreadPool.shutdown();
		
	}
	
}
