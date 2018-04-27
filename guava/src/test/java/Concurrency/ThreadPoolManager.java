package Concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {

	
	final static ExecutorService executor = Executors.newFixedThreadPool(15);
	
	
	/**
	 * 关闭线程池的正确姿势：
	 * 		--->>>  MoreExecutors.shutdownAndAwaitTermination(service, timeout, unit)
	 * 
	 * ----->>> shutdown (不会发出中断)
	 * shutdown means the executor service takes no more incoming tasks. 
	 * 	重点在于 --- 不再接收新提交的任务，并且调用后立即返回，不会阻塞。

	 * ----->>> awaitTermination
	 * awaitTermination is invoked after a shutdown request.
	 * 	重点在于 --- 阻塞一定时间，等待正在执行的任务结束，如果任务没有结束，则发生超时自动解除阻塞
	 * 	在调用shutdown 或者 shutdownNow之后，再调用awaitTermination进行阻塞等待
		awaitTermination在以下3种情况会结束阻塞：
		1. 线程池中的任务已全部结束； 2.线程池中的任务还没有结束，但是超时时间已到； 3.当前线程被其它线程中断
 
	 * You need to first shut down the service and then block and wait for threads to finish.
	 * 	重点在于 --- 先调用shutdown，再awaitTermination
 
 	 * ---->>> shutdownNow (通过发出中断，通知线程池中运行的线程结束运行。)
	 * 重点在于 --- 向线程池中所有正在执行任务的线程发出中断，并停止处理队列中处于等待状态的任务，返回哪些被放弃执行的任务 
	 * 是否能立即结束线程池的任务，关键是取决于线程运行的代码是否对中断进行了响应，
	 * 比如catch到interruptException后，如果没有对中断异常进行任何处理，那任务将继续执行下去，是无法停止线程的，只能等待线程运行的任务正常结束
	 * 比如catch到interruptException后，立即从循环中break，这样就能结束线程的运行。
	 * 
	 */
	 final static void shutdownAndAwaitTermination(ExecutorService pool, int timeout, TimeUnit timeUnit) {
		   pool.shutdown(); // Disable new tasks from being submitted
		   try {
		     // Wait a while for existing tasks to terminate
		     if (!pool.awaitTermination(timeout, timeUnit)) {
		       pool.shutdownNow(); // Cancel currently executing tasks
		       // Wait a while for tasks to respond to being cancelled
		       if (!pool.awaitTermination(timeout, timeUnit))
		           System.err.println("Pool did not terminate");
		       
		       // If your pool is taking more time to shutdown
		       /*while (!pool.awaitTermination(60, TimeUnit.SECONDS))*/ 
		     }
		   } catch (InterruptedException ie) {
		     // (Re-)Cancel if current thread also interrupted
		     pool.shutdownNow();
		     // Preserve interrupt status
		     Thread.currentThread().interrupt();
		   }
	 }
}
