package Concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * AsyncFunction
 * 
 *	将ListenableFuture 通过 AsyncFunction 处理后，返回1个新的ListenableFuture
 *
 */
public class Test04AsyncFunction1 {
		
	/**
	 * asynchronously get a String and then asynchronously transform that String into a different one.
	 * 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		ExecutorService deletegate = Executors.newFixedThreadPool(1);
		ExecutorService deletegateForAsyncFunction = Executors.newFixedThreadPool(1);
		
		ListeningExecutorService pool = MoreExecutors.listeningDecorator(deletegate);
		ListeningExecutorService poolForAsyncFunction = MoreExecutors.listeningDecorator(deletegateForAsyncFunction);
		
		System.out.printf("[thread - %s] 提交初始任务到线程池\n", 
				Thread.currentThread().getName());
		// 提交1个Task任务
		ListenableFuture<String> resultFromWorker = pool.submit(new Worker());
		
		System.out.printf("[thread - %s] 将ListenableFuture的结果用AsyncFunction进行转化\n", 
				Thread.currentThread().getName());
		
		// the returned Future takes its result from a Future produced by applying the given AsyncFunction to the result of the original Future.
		// 将resultFromWorker返回的结果，作为input参数传递给AsyncTransformation的apply方法 --- 简单理解：将结果交给一个函数进行处理，返回1个新的结果
		ListenableFuture<String> finalResult = Futures.transformAsync(resultFromWorker, new AsyncTransformation(poolForAsyncFunction), deletegate);
		
		System.out.printf("[thread - %s] 将AsyncFunction处理完成后返回的结果，绑定到callback\n", 
				Thread.currentThread().getName());
		// 对上面返回的ListenableFuture绑定回调处理器。当finalResult返回结果后，由FutureCallback处理
		Futures.addCallback(finalResult, new MyFutureCallback(), deletegate);
		
		TimeUnit.SECONDS.sleep(10);
		
		pool.shutdown();
		poolForAsyncFunction.shutdown();
		
	}


	private static final class Worker implements Callable<String> {
		public String call() throws Exception {
			try {
				System.out.printf("\t[thread - %s] Executing 1st task start\n", 
						Thread.currentThread().getName());
				// simulate some work
				TimeUnit.SECONDS.sleep(3);
			} catch(InterruptedException ex){
				ex.printStackTrace();
			}
			return "CALCULATED_VALUE";
		}
	}
	
	/**
	 * Almost like Function transformation but it is asynchronous
	 */
	private static final class AsyncTransformation implements AsyncFunction<String, String> {
		
		private final ListeningExecutorService poolToRunFunctionIn;
		
		public AsyncTransformation(ListeningExecutorService poolToRunFunctionIn){
			this.poolToRunFunctionIn = poolToRunFunctionIn;
		}
		
		@Override
		public ListenableFuture<String> apply(String input) throws Exception {
			return poolToRunFunctionIn.submit(new FunctionWorker(input));
		}
	}
	
	/**
	 * 'worker' for the AsyncFunction
	 */
	private static final class FunctionWorker implements Callable<String> {
		private final String input;
		public FunctionWorker(String input){
			this.input = input;
		}
		public String call() throws Exception {
			try {
				System.out.printf("\t[thread - %s] Executing 2st task start\n", 
						Thread.currentThread().getName());
				TimeUnit.SECONDS.sleep(3);
			} catch(InterruptedException ex){
				ex.printStackTrace();
			}
			return input + "_TRANSFORMED";
		}
	}
	
	/**
	 * what do to when the ListenableFuture has been processed
	 */
	private static final class MyFutureCallback implements FutureCallback<String> {
		public void onSuccess(String result) {
			System.out.printf("\t[thread - %s] Result from computation: success\n", 
					Thread.currentThread().getName(), result);
		}
		
		public void onFailure(Throwable t) {
			t.printStackTrace();
		}
	}
	
	
}
