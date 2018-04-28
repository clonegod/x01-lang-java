package spring5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import clonegod.commons.log.Console;

public class TestListenableFuture {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		AsyncListenableTaskExecutor executor = 
				new SimpleAsyncTaskExecutor("ListenableFutureTask-");
		
		ListenableFuture<Integer> future = 
				executor.submitListenable(new Callable<Integer>() {
					public Integer call() throws Exception {
						return ThreadLocalRandom.current().nextInt(1, 10);
					}
				});
		
		
		future.addCallback(new ListenableFutureCallback<Integer>() {
			public void onSuccess(Integer result) {
				Console.log(result);
			}

			public void onFailure(Throwable t) {
				Console.error(t);
			}
		});
		
		
		future = 
				executor.submitListenable(new Callable<Integer>() {
					public Integer call() throws Exception {
						return ThreadLocalRandom.current().nextInt(1, 10);
					}
				});
		
		
		future.addCallback(new ListenableFutureCallback<Integer>() {
			public void onSuccess(Integer result) {
				Console.log(result);
			}

			public void onFailure(Throwable t) {
				Console.error(t);
			}
		});
		
		Console.log("Starting...");
		
	}
}
