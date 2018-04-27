package guava.concurrency;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class Test03FutureCallback {
	@Test
	public void testFutureCallback() throws InterruptedException {
		
		final ExecutorService executor = Executors.newFixedThreadPool(10);
		
		// wrap our ExecutorService object with a ListentingExecutorService interface
		ListeningExecutorService service = MoreExecutors.listeningDecorator(executor);
		
		Callable<String> callable = () -> {
			System.out.printf("\t[%s] Executing very expensive Task start!\n", 
					Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(3);
			System.out.printf("\t[%s] Executing very expensive Task end!\n", 
					Thread.currentThread().getName());
			return UUID.randomUUID().toString();
		};
		
		System.out.println(Thread.currentThread().getName() + " 主线程提交异步任务");
		ListenableFuture<String> futureTask = service.submit(callable);
		
		FutureCallbackImpl callback = new FutureCallbackImpl();
		
		System.out.println(Thread.currentThread().getName() + " 主线程对listernable future task设置callback回调处理");
		// Future的结果不是阻塞获取，而是交给另一个线程去异步处理。好处是：不会阻塞主线程的执行。
		// we want this handler to run immediately after task complete!
		// let FutureCallback handle the result asynchronously on its own.
		Futures.addCallback(
				futureTask, 
				// 1. FutureCallback operation will be executed on a thread from the supplied ExecutorService parameter
				callback, 
				// 2. execute the FutureCallback operation behaving much like the ThreadPoolExecutor.CallerRunsPolicy executor service, the task will be run on the caller's thread.
				Executors.newCachedThreadPool()
			);
		
		
		System.out.println(Thread.currentThread().getName() + " 主线程继续执行代码");
		
//		TimeUnit.SECONDS.sleep(5);
		
		// 获取对future进行异步处理的结果
		while(true) {
			System.out.println(Thread.currentThread().getName() + " 主线程等待callback函数返回最终结果");
			if(callback.isComplete()) {
				String result = callback.getCallbackResult();
				System.out.printf("[%s] get result from callback: %s\n", 
						Thread.currentThread().getName(), result);
				break;
			}
			TimeUnit.MILLISECONDS.sleep(500);
		}
		
		ThreadPoolManager.shutdownAndAwaitTermination(service, 5, TimeUnit.SECONDS);
		
	}
	
	
	public class FutureCallbackImpl implements FutureCallback<String> {
		private StringBuilder builder = new StringBuilder();
		private volatile boolean complete;

		// onSuccess method takes the result of the Future instance as an
		// argument so we have access to the result of our task.
		@Override
		public void onSuccess(String result) {
			System.out.printf("\t[%s] callback process result start!\n", 
					Thread.currentThread().getName());
			builder.append(result).append(" successfully");
			complete = true;
			System.out.printf("\t[%s] callback process result end!\n", 
					Thread.currentThread().getName());
		}

		@Override
		public void onFailure(Throwable t) {
			builder.append(Throwables.getStackTraceAsString(t));
			complete = true;
		}

		public String getCallbackResult() {
			return builder.toString();
		}
		
		public boolean isComplete() {
			return complete;
		}
	}
}
