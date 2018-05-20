package juc.executor.shutdown;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

import conc.util.CommonUtil;

/**
 * shutdownNow()
 * 1. 不再接收新任务的提交---会抛出RejectException
 * 2. 向当前正在执行的线程发出中断
 * 3. 取消阻塞队列中等待执行的所有任务
 * 4. 返回一个列表，该列表包含所有被取消的任务（但不包含当前正在执行的任务）
 * 5. 有一点需要特别说明：向 当前运行的线程发出中断，不代表线程会立即退出，要看具体任务代码中有没有检测中断，并在中断时进行任务的退出操作。如果没有，那么当前运行的任务不会结束。
 * 
Attempts to stop all actively executing tasks, halts the processing of waiting tasks, 
and returns a list of the tasks that were awaiting execution. 
These tasks are drained (removed) from the task queue upon return from this method. 
尝试停止所有主动执行的任务，停止等待任务的处理，并返回正在等待执行的任务列表。 从此方法返回时，这些任务将从任务队列中排除（删除）。

This method does not wait for actively executing tasks to terminate. Use awaitTermination to do that. 
此方法不等待主动执行的任务终止。 使用等待终止来做到这一点。

There are no guarantees beyond best-effort attempts to stop processing actively executing tasks. 
This implementation cancels tasks via Thread.interrupt, so any task that fails to respond to interrupts may never terminate.
除了努力尝试停止处理积极执行任务之外，没有任何保证。 此实现通过Thread.interrupt取消任务，因此无法响应中断的任何任务可能永远不会终止。
 */
public class ShutdownNowThreadPoolExecutorTest {
	static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
	
	static final CountDownLatch CDL = new CountDownLatch(1);
	
	static FutureTask<String> fastestTask = new FutureTask<>(new MyTask(100, CDL, "fastestTask")) ;
	static FutureTask<String> noramlTask = new FutureTask<>(new MyTask(300, null, "noramlTask")) ;
	static FutureTask<String> slowestTask = new FutureTask<>(new MyTask(500, null, "slowestTask")) ;
	
	public static void main(String[] args) throws InterruptedException {
		executor.submit(fastestTask);
		executor.submit(noramlTask);
		executor.submit(slowestTask);
		System.out.println("Before shutdownNow: " + executor.toString());
		
		CommonUtil.sleep(10);
		List<Runnable> canceledTask = executor.shutdownNow();
		
		if (!canceledTask.isEmpty()) {
			System.out.println("线程池关闭后，返回阻塞队列中未被执行的任务。调用者可以继续处理这些FutureTask任务，也可以选择放弃这些任务");
			canceledTask.stream().forEach(x -> System.out.println("--------------取消执行的任务: " + ((FutureTask)x).toString()));
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
				// 如果循环中不对中断进行处理，那么程序将继续执行下去直到结束。
				if(Thread.currentThread().isInterrupted()) {
					throw new InterruptedException(); // 将异常抛给executor，由executor中断线程对中断进行处理（结束任务的执行）。
				}
				System.out.println(Thread.currentThread().getName() + ":::" + name + ":::" + i);
			}
			System.out.println("------>>>"+Thread.currentThread().getName() + ":::" + "complete task:" + name);
			return name;
		}
		
	}
	
}
