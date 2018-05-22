package juc.executor.completionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class InvokeAllVsCompletionService {
	
	ExecutorService pool = Executors.newCachedThreadPool();
	
	CompletionService<String> completionService = new ExecutorCompletionService<>(pool);
	
	Collection<Callable<String>> tasks = IntStream.rangeClosed(1, 10).mapToObj(n -> {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				TimeUnit.SECONDS.sleep(n);
				return "Sleep:"+n;
			}
		};
	}).collect(Collectors.toList());
	
	

	/**
	 * invokeAll 会执行所有任务，知道所有提交的任务都执行结束才返回---阻塞调用
	 */
	@Test
	public void testExecutorInvokeAll() throws Exception {
		
		System.out.println("begin at: " + getNow() );
		
		// main thread will block here until all task complete
		List<Future<String>> list = pool.invokeAll(tasks); 
		 
		System.out.println("invokeAll return at: " + getNow() );
		
		 for(Future<String> f : list) {
			 System.out.println(f.get());
		 }
		 
		 System.out.println("end at: " + getNow() );
		 
		 pool.shutdown();
	}
	
	/**
	 * CompletionService内部会将一批任务中，先执行完成的任务进行返回---异步调用，更灵活
	 */
	@Test
	public void testCompletionService() throws Exception {
		System.out.println("begin at: " + getNow() );
		
		tasks.forEach(task -> completionService.submit(task)); // nonblocking
		
		pool.shutdown(); // nonblocking
		
		System.out.println("invokeAll return at: " + getNow() );
		
		while(! pool.isTerminated()) {
			// immediately return when any one task completed
			Future<String> future = completionService.take(); 
			System.out.println(future.get());
		}
		
		System.out.println("end at: " + getNow() );
	}
	
	private String getNow() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
	}
}
