package java8.core.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class Test02Java5Executor {
	
	/**
	 * Executor 提供线程池来执行任务 
	 * 
	 */
	@Test
	public void testExecutor() {
		// 执行器，线程池（ThreadPoolExecutor）是它的一种实现
		Executor executor = Executors.newFixedThreadPool(10);
		
		executor.execute(new Runnable() {
			@Override
			public void run() {
				System.out.printf("[Thread - %5s] - Hello World\n", 
						Thread.currentThread().getName());
			}
		});
		
		// 关闭线程池
		if(executor instanceof ExecutorService) {
			ExecutorService executorService = ExecutorService.class.cast(executor);
			executorService.shutdown();
		}
	}

	/**
	 * Callable 可返回结果的任务
	 * Future 获取异步结果的API
	 */
	@Test
	public void testCallable() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		
		// Future引用线程的异步执行结果
		Future<String> future = executorService.submit(new Callable<String>() {
			@Override
			public String call() {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
				}
				System.out.printf("[Thread - %5s] - Hello World\n", 
						Thread.currentThread().getName());
				return "Hello world";
			}
		});
		
		// 判断线程是否执行完成
		while(! future.isDone()) {
			try {
				// 获取线程执行后返回的结果
				String value = future.get(1, TimeUnit.SECONDS);
				
				System.out.printf("[Thread - %5s] - 返回结果:%s\n", 
						Thread.currentThread().getName(), value);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				break;
			} catch (TimeoutException e) {
				System.err.println("get result from future timeout...");
				continue;
			}
		}
		
		
		// 关闭线程池
		executorService.shutdown();
		
	}
	
}
