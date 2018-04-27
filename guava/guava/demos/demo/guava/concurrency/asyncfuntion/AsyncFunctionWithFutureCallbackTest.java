package demo.guava.concurrency.asyncfuntion;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * AsyncFunction(ListenableFuture)
 * FutureCallback
 * Futures.addCallback(listenableFuture, futureCallback, listeningExecutorService);
 * 
 * @author clonegod@163.com
 *
 */
public class AsyncFunctionWithFutureCallbackTest {
	static ListeningExecutorService listeningExecutorService;
	
	static int NUM_THREADS = 3;
	
	public static void main(String[] args) throws Exception {
		
		listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(NUM_THREADS));
		
		// ListenableFuture - 异步计算
		final AsyncFunction<Long, String> asyncFunction = 
				new CalcAsyncFuntion(listeningExecutorService);
		
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
		Lists.newArrayList(1L, 2L, 3L, 1L, 2L, 3L).forEach( X -> {
			try {
				Futures.addCallback(
						asyncFunction.apply(X), // ListenableFuture
						futureCallback, 
						listeningExecutorService);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		for(int i=0; i<3; i++) {
			System.out.println(Thread.currentThread().getName() + " 继续执行其它任务：" + i);
		}
		
		listeningExecutorService.awaitTermination(3, TimeUnit.SECONDS);
		listeningExecutorService.shutdown();
	}
	
}
