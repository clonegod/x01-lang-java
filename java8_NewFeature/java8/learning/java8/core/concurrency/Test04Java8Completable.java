package java8.core.concurrency;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import clonegod.commons.log.Console;

/**
 * 并行异步执行任务，提供了强大的异步编程接口
 * 
 * 		判断异步操作完成状态		
 * 			completableFuture.isDone()
 * 			completableFuture.isCompletedExceptionally()
 * 
 *  	链式操作多个Future，对上一个异步结果再次进行计算（过滤，筛选，合并结果等）	
 *  		completableFuture.thenRun(action)
 *  		completableFuture.thenApply(fn)
 *  		completableFuture.thenCombineAsync(other, fn)
 *  		...
 *  
 *		异常处理				
 *			completableFuture.isCompletedExceptionally()
 *			completableFuture.exceptionally(fn)
 */
public class Test04Java8Completable {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		CompletableFuture<Void> completableFuture = CompletableFuture.supplyAsync(() -> {
			
			String queryResultFromDB = "Hello, World";
			Console.log(queryResultFromDB);
			return queryResultFromDB;
		})
		.thenApply( result -> {
			
			Console.log("");
			return result.toLowerCase();
		})
		.thenApply( result -> {
			
			Console.log(Integer.parseInt("a"));
			return result + ", date: " + LocalDate.now();
		})
		.thenAccept(value -> {
			// 最后将结果消费掉
			Console.log(value);
			// conn.commit()
		})
		.exceptionally((Throwable t) -> {
			// 如果前面的步骤发生异常，在此处进行统一处理
			Console.error(t);
			// conn.rollback()
			return null;
		});
		
		
		Console.log("Main starting...");
	
		while(! completableFuture.isDone()) {
			// wait task complete
		}
		
		Console.log("任务全部执行结束");
	}
	
}
