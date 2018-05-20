package juc.executor.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import conc.util.CommonUtil;

/**
 * 创建一个线程池需要考虑哪些点？
 * 	核心池大小，最大池大小，空闲线程池存活时长，工作队列，线程工厂，拒绝执行策略。
 * 
 * 1. cpu密集型 ? io密集型? 
 * 		不同的场景需要的线程数是不同的。cpu密集型线程数建议为cpu核数+1；IO密集型建议设置较多线程提供服务；
 * 2. 阻塞队列，有界？无界？
 * 		有界可以防止创建大量线程导致服务崩溃。
 * 3. 线程池中线程什么时候被启动？ 
 * 		默认是有任务达到后才启动，可以预启动策略，调用prestartAllCoreThreads()方法。
 * 4. 饱和策略？
 * 		RejectedExecutionHandler
 * 			AbortPolicy				默认策略。终止该任务。会引起executor抛出未检查的RejectedExecutionException
 * 			DiscardPolicy			丢弃此任务。
 * 			DiscardOldestPolicy		丢弃最旧的任务，即本来该接下来运行的任务。当使用PriorityQueue时，不能使用此策略，否则丢弃的是优先级最高的任务！
 * 			CallerRunsPolicy		把任务退回给调用者，由调用者线程来执行该任务。
 * 5. 线程池提供的回调方法？
 * 		可以使用这些回调方法来添加日志，统计信息
 * 		beforeExecute	如果抛出一个RuntimeException，任务将不会被执行，afterExecute也不会被执行
 * 		afterExecute	无论任务正常从run()返回，还是抛出异常，afterExecute都会被执行（除非是Error异常）
 * 		terminated		在线程池完成关闭后调用(所有任务都done并且所有线程都退出),此时可以发出通知，记录日志，完成统计信息。
 */
public class CustomThreadPool {
	private static final int MIN_THREADS = Runtime.getRuntime().availableProcessors(); // 核心线程数-即使没有任务运行也要维持存活的线程
	private static final int MAX_THREADS = 100; // 线程池允许的线程上限
	private static final int CAPACITY = 100; // 有限队列大小
	private static final long keepAliveTime = 300; // 空闲线程存活期
	private static final TimeUnit unit = TimeUnit.SECONDS; // 空闲线程超时时间单位
	
	/** 创建一个可变长的线程池，使用受限队列和“调用者运行”饱和策略 */
	private static final TimingThreadPool executor = 
			new TimingThreadPool(MIN_THREADS, MAX_THREADS, 
					keepAliveTime, unit, 
					new LinkedBlockingQueue<>(CAPACITY),
					new MyThreadFactory("AppThreadPool"),
					new ThreadPoolExecutor.CallerRunsPolicy()
					);
	
	static {
		executor.prestartAllCoreThreads();
		// executor的配置可以在实例化之后进行修改
//		executor.setCorePoolSize(corePoolSize);
//		executor.setKeepAliveTime(time, unit);
//		executor.setMaximumPoolSize(maximumPoolSize);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	public static void main(String[] args) {
		System.out.println(executor);
		for(int i=0; i< 5; i++)
			executor.submit(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<100; i++) {
					CommonUtil.sleep(10);
					System.out.println(Thread.currentThread().getName() + "----" + i);
				}
				System.out.println("====>>>>>>>>>>>>>>" + Thread.currentThread().getName() + "----done");
			}
		});
		
		CommonUtil.sleep(3000);
		
		executor.shutdown();
	}
	
}
