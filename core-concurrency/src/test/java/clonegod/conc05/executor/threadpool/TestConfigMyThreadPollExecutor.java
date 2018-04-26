package clonegod.conc05.executor.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池配置参数
 * 
 *	ThreadPoolExecutor的核心配置参数：
 *		corePoolSize	核心线程数，线程池内部维护的最小线程个数
 *		maximumPoolSize	最大线程数，线程池可创建的最多线程数
 *		keepAliveTime	线程池中线程的最大空闲时间
 *		unit			时间单位
 *		workQueue		线程池存放暂时任务的队列缓冲
 *		threadFactory	线程工厂，为线程池创建线程，可自定义线程工厂来为线程自定义名称，以便排查问题时定位
 *		rejectHandler	任务拒绝执行的处理器（当线程池缓冲队列已满时，新提交任务被拒绝处理的处理策略）
 *							AbortPolicy			抛出异常RejectedExecutionException
 *							DiscardPolicy		不抛出异常，直接丢弃当前提交的任务
 *							DiscardOldestPolicy	不抛出异常，丢弃任务列表最早提交的任务
 *							CallerRunsPolicy	调用者自己执行提交的任务
 *
 *	关键1-线程池中线程的创建时机：
 *		1、线程池并不是在实例化时就立刻初始化coreSize个线程，而是等任务提交到线程池后才开始创建新线程。
 *		2、当线程池中的线程数等于coreSize后，新提交的任务将被放到任务队列中缓存起来，直到队列满时，才会创建新的线程来处理队列中的任务，
 *		3、队列满之后，再创建的线程数受maxSize限制。
 *		4、可以在实例化线程池后，预先启动1个线程或启动coreSize个线程，让这些线程进入空闲状态，等待任务的执行。而不是等任务来了才开始创建线程。
 *
 *	关键2-线程池中维护的队列类型对性能的影响：
 *		1、有界队列 - coreSize, maxSize 以及queueSize, rejectHandler这些参数都会影响到任务的处理。
 * 			在使用有界队列时，若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程，
 * 			若大于corePoolSize，则会将任务加入队列，
 * 			若队列已满，则在总线程数不大于maximumPoolSize的前提下，创建新的线程，
 * 			若线程数大于maximumPoolSize，则执行拒绝策略。或其他自定义方式。
 * 
 *		2、无界队列 - maxSize参数无效，因为队列不会满，
 *			与有界队列相比，除非系统资源耗尽，否则无界队列不存在任务入队失败的情况。
 *			当新任务达到时，系统的线程数小于coreSize，则新建线程执行任务，当达到coreSize后，不会再创建新的线程。
 *			后续有新任务加入，而没有空闲线程时，则直接进入队列等待；
 *			若任务的创建速度和处理速度差异很大，会导致无界队列快速增长，知道耗尽系统内存。
 * 
 */
public class TestConfigMyThreadPollExecutor {
	
	public static void main(String[] args) {
		int corePoolSize = 1;
		int maximumPoolSize = 2;
		
		long keepAliveTime = 60;
		TimeUnit unit = TimeUnit.SECONDS;
		
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(3); 	// 有界队列
//		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(3); 	// 有界队列
//		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(); 	// 无界队列
		
		ThreadFactory threadFactory = new MyThreadFactory(); // 自定义线程工厂
		
		// 任务拒绝策略
//		RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.CallerRunsPolicy();
		RejectedExecutionHandler rejectHandler = new MyRejectExecutionHandler();
		
		// 自定义线程执行器-内部重写了newTaskFor方法
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,
					                maximumPoolSize,
					                keepAliveTime,
					                unit,
					                workQueue,
					                threadFactory,
					                rejectHandler);
		
		// 可预先启动线程池中coreSize个线程进入空闲状态，否则新任务提交后线程池才会去创建线程来处理任务。
//		executor.prestartCoreThread();
//		executor.prestartAllCoreThreads();
		
		executor.execute(new MyTask(1)); // coreSize=1，任务立即执行
		
		executor.execute(new MyTask(2)); // 进入队列缓冲
		executor.execute(new MyTask(3)); // 进入队列缓冲
		executor.execute(new MyTask(4)); // 进入队列缓冲
		
		executor.execute(new MyTask(5)); // 队列已满，且maxSize=2，因此会再创建1个新的线程来处理任务，当前提交的任务立即被执行
		
		executor.execute(new MyTask(6)); // 队列已满，这个任务会被拒绝执行，不同拒绝策略有不同的处理方式
		
		executor.shutdown();
	}
	
	
}
