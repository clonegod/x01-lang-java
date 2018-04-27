package demo.guava.concurrency.futurelistener;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * ListenableFuture
 * FutureCallback
 * Futures.addCallback(listenableFuture, futureCallback, listeningExecutorService);
 * 
 * @author clonegod@163.com
 *
 */
public class ListenableFutureWithFutureCallbackTest {
	static ListeningExecutorService listeningExecutorService;
	
	static int NUM_THREADS = 5;
	
	public static void main(String[] args) throws InterruptedException {
		
		listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(NUM_THREADS));
		
		ListenableFuture<String> listenableFuture =
				listeningExecutorService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Thread.sleep(1000);
					if(Math.random() > 0.5) {
						throw new NullPointerException();
					}
					return "Task Completed";
				}
			});
		
		FutureCallback<String> futureCallback = new FutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				System.out.println(result + " successfully");
			}
			@Override
			public void onFailure(Throwable t) {
				System.err.println("Something Bad Happen:\n"+Throwables.getStackTraceAsString(t));
			}
		};
		
		// 将异步任务返回的结果，交给futureCallback处理，并且可指定线程池
		// attach a FutureCallback instance to run after a ListenableFuture instance 
		// has completed its task.
		Futures.addCallback(listenableFuture, futureCallback, listeningExecutorService);
		
		for(int i=0; i<3; i++) {
			System.out.println(Thread.currentThread().getName() + " 继续执行其它任务：" + i);
		}
		
		listeningExecutorService.awaitTermination(3, TimeUnit.SECONDS);
		listeningExecutorService.shutdown();
	}
	
}
