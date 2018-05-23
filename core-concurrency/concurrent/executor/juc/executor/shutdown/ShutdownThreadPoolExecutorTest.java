package juc.executor.shutdown;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import conc.util.CommonUtil;

/**
 * 
Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted. 
Invocation has no additional effect if already shut down. 
启动有序关闭，其中先前提交的任务将被执行，但不会接受任何新任务。 如果已经关闭，调用没有额外的作用。

This method does not wait for previously submitted tasks to complete execution. Use awaitTermination to do that.
此方法不等待任务的结束，会立即返回。建议结合使用“等待终止”来加强退出逻辑，即让主线程wait一定时间。
 */
public class ShutdownThreadPoolExecutorTest {
	static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
	
	static final CountDownLatch CDL = new CountDownLatch(1);
	
	static FutureTask<String> fastestTask = new FutureTask<>(new MyTask(100, CDL, "fastestTask")) ;
	static FutureTask<String> noramlTask = new FutureTask<>(new MyTask(300, null, "noramlTask")) ;
	static FutureTask<String> slowestTask = new FutureTask<>(new MyTask(500, null, "slowestTask")) ;
	
	public static void main(String[] args) throws InterruptedException {
		testShutdown();
	}
	
	public static void testShutdown() throws InterruptedException {
		executor.submit(fastestTask);
		executor.submit(noramlTask);
		executor.submit(slowestTask);
		
		System.out.println("Before shutdownNow: " + executor.toString());
		
		CommonUtil.sleep(10);
		executor.shutdown();
		if (!executor.awaitTermination(1, TimeUnit.MILLISECONDS)) {
			System.out.println("-----------还有任务正在被执行或等待被执行，线程池正在等待关闭中");
			// executor.shutdownNow();
			//System.exit(0);
		}
		
		System.out.println("After shutdownNow: " + executor.toString());
		
		CDL.await();
	}
	
	
	static class MyTask implements Callable<String> {
		int n;
		String name;
		
		public MyTask(int n, CountDownLatch CDL, String name) {
			this.n = n;
			this.name = name;
		}
		@Override
		public String call() throws InterruptedException {
			for(int i = 0; i < n; i++) {
				// 由于shutdown()不会中断当前正在执行 的任务，因此这里是检测不到中断的
				if(Thread.currentThread().isInterrupted()) {
					throw new InterruptedException();
				}
				System.out.println(Thread.currentThread().getName() + ":::" + name + ":::" + i);
			}
			System.out.println("------>>>"+Thread.currentThread().getName() + ":::" + "complete task:" + name);
			return name;
		}
		
	}
	
}
