package demo.guava.concurrency.futurelistener;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/**
 * Runnable		描述一个可执行的任务，无返回值。
 * Callable		描述一个可执行的任务，并且有返回值。
 * Future		描述某个异步任务执行的结果。
 * FutrueTask	既描述一个任务，又表示某个异步任务执行的结果，它同时实现了Runnable和Future。
 * 				如果封装的是Runnable，则需由调用方提供一个返回值；
 * 				如果封装的是Callable，则之后可获取到执行后返回的结果；
 * 
 * ListenableFuture 
 * 	1. 传统方式下，在异步任务未返回结果之前调用future.get, 会导致当前线程被阻塞；
 *  2. 传统方式下，获取到future.get返回的结果后，可以由当前线程来处理结果，也可以将结果交给其它线程去处理；
 * 	3. 使用ListenableFuture，事先以Runnable的方式定义回调函数来处理future.get返回的结果，并将其提交给线程池执行，解放了当前线程。
 * 
 * @author clonegod@163.com
 *
 */
public class ListenableFutureTest {
	static ListeningExecutorService listeningExecutorService;
	
	static int NUM_THREADS = 5;
	
	public static void main(String[] args) throws InterruptedException {
		
		listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(NUM_THREADS));
		
		ListenableFuture<String> listenableFuture =
				listeningExecutorService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Thread.sleep(1000);
					return "hello";
				}
			});
		
		// addListener方式：在回调函数中，获取不到之前异步任务返回的结果
		listenableFuture.addListener(new Runnable() {
			@Override
			public void run() {
				methodToRunOnFutureTaskCompletion();
			}
		}, listeningExecutorService);
		
		listeningExecutorService.awaitTermination(3, TimeUnit.SECONDS);
		listeningExecutorService.shutdown();
	}
	
	private static void methodToRunOnFutureTaskCompletion() {
		System.out.println("FutureTask done!");
	}
}
