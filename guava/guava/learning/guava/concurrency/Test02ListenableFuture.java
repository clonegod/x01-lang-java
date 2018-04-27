package guava.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class Test02ListenableFuture {

	@Test
	public void testSimpleFuture() {
		
		final int NUM_THREADS = 10;
		
		// 线程池
		ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
		
		// 提交任务，返回future
		Future<String> future =
				executorService.submit(new Callable<String>(){
					@Override
					public String call() throws Exception {
						System.out.printf("[thread: %s] - Execute future task\n", 
								Thread.currentThread().getName());
						return "search from database";
					}});
		
		try {
			String result = future.get();
			System.out.printf("[thread: %s] - (Blocking) Wait future task complete, result=%s\n", 
					Thread.currentThread().getName(), result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("主线程继续执行");
		System.out.println("主线程继续执行");
		System.out.println("主线程继续执行");
		System.out.println("主线程继续执行");
		System.out.println("主线程继续执行");
		
	}
	
	@Test
	public void testListenableFuture() {
		
		final int NUM_THREADS = 10;
		
		// 线程池
		ListeningExecutorService executorService =
				MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(NUM_THREADS));
		
		// 提交任务，返回future
		ListenableFuture<String> listenableFuture =
				executorService.submit(new Callable<String>(){
					@Override
					public String call() throws Exception {
						System.out.printf("[thread: %s] - Execute future task\n", 
								Thread.currentThread().getName());
						return "search from database";
					}});
		
		// add a listener to run once the original task is completed
		
		// 直接在listenableFuture上设置监听器，这有一个限制：无法访问到future的结果
		// There is a small limitation: can not access to the returned object
		listenableFuture.addListener(new Runnable() {
			@Override
			public void run() {
				methodToRunOnFutureTaskCompletion();
			}
		}, executorService);
		
		System.out.println("主线程继续执行");
		System.out.println("主线程继续执行");
		System.out.println("主线程继续执行");
		System.out.println("主线程继续执行");
		System.out.println("主线程继续执行");
		
	}
	
	private void methodToRunOnFutureTaskCompletion() {
		System.out.printf("[thread: %s] - Handle result of future task\n", 
				Thread.currentThread().getName());
	}

	
}
 