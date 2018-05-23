package juc.executor.shutdown;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * shutdown() 
 * 	will just tell the executor service that it can't accept new tasks, but the already submitted tasks continue to run。
 *  调用shutdown()之后，线程池不再接收新提交的任务，但是会把正在执行的，以及当前阻塞队列中的任务全部执行完成。
 *  
 * shutdownNow() 
 * 	will do the same AND will try to cancel the already submitted tasks by interrupting the relevant threads. 
 * 	Note that if your tasks ignore the interruption, shutdownNow will behave exactly the same way as shutdown.
 *	调用shutdownNow()之后，线程池不再接收新提交的任务，也不再执行阻塞队列中的任务（cancel tasks which still in the blockingQueue）。
 *	注意：
 *		shutdownNow()仅仅是尝试立即终止正在运行的任务
 *		如果你的任务代码里忽略了中断异常（没有代码检查中断状态并退出任务的执行），那么shutdowNow无法让当前正在执行的任务结束运行。
 */
public class ShutdownExecutorTest {
	public static void main(String[] args) throws InterruptedException {
		 ExecutorService executor = Executors.newFixedThreadPool(1);
		    executor.submit(new Runnable() {
		        @Override
		        public void run() {
		            while (true) {
		                if (Thread.currentThread().isInterrupted()) {
		                    System.out.println("interrupted"); // when invoke shutdownNow()
		                    break;
		                }
		            }
		        }
		    });

		    executor.shutdown(); // shutdown不会中断当前正在执行的线程，仅仅是不再接收新任务的提交
		    //executor.shutdownNow(); // shutdownNow会向当前正在执行的线程发出中断异常，仅仅是发出中断信号，线程是否停止执行取决于运行的代码是否对中断进行了响应并退出
		    
		    // 调用完shutdown之后，再调用awaitTermination对关闭结果进行检测，如果还未关闭，则直接退出jvm进程。
		    if (!executor.awaitTermination(100, TimeUnit.MICROSECONDS)) {
		        System.out.println("Still waiting after 100ms: calling System.exit(0)..."); // when invoke shutdown()
		        System.exit(0);
		    }
		    System.out.println("Exiting normally..."); // when invoke shutdownNow()
	}
}
