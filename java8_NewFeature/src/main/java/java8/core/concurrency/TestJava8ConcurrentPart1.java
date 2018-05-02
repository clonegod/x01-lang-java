package java8.core.concurrency;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

/**
 * 线程池，异步任务执行 
 *
 */
public class TestJava8ConcurrentPart1 {
	
	/**
	 * 线程的运行
	 */
	@Test
	public void testRunThread() {
		
		Runnable task = () -> {
		    try {
				String threadName = Thread.currentThread().getName();
				System.out.println("Hello " + threadName);
				String name = Thread.currentThread().getName();
				System.out.println("Foo " + name);
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Bar " + name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		Thread sub = new Thread(task);
		sub.start();

		System.out.println("Done!");
		
		try {
			// wait sub thread complete, prevent JVM exit
			sub.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 向线程池提交普通任务，任务执行完成后不需要返回结果  - Runnable
	 */
	@Test
	public void testExecutor() {
		// SingleThreadExecutor
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		executor.execute(() -> {
		    String threadName = Thread.currentThread().getName();
		    System.out.println("Hello " + threadName);
		});
		
		// ------- shutdown executors
		/**
		 * The executor shuts down softly by waiting a certain amount of time for termination of currently running tasks. 
		 * After a maximum of five seconds the executor finally shuts down by interrupting all running tasks.
		 */
		try {
		    System.out.println("attempt to shutdown executor");
		    executor.shutdown();
		    executor.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
		    System.err.println("tasks interrupted");
		}
		finally {
		    if (!executor.isTerminated()) {
		        System.err.println("cancel non-finished tasks");
		    }
		    executor.shutdownNow();
		    System.out.println("shutdown finished");
		}
		
	}
	
	/**
	 * 向线程池提交可返回结果的任务 - Callable
	 */
	@Test
	public void testCallableAndFuture() {
		Callable<Integer> task = () -> {
		    try {
		        TimeUnit.SECONDS.sleep(2);
		        return 123;
		    }
		    catch (InterruptedException e) {
		        throw new IllegalStateException("task interrupted", e);
		    }
		};
		
		// FixedThreadPool
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Integer> future = executor.submit(task);

		// After submitting the callable to the executor we first check if the future has already been finished execution 
		System.out.println("future done? " + future.isDone());

		try {
			// Any call to future.get() will block and wait until the underlying callable has been terminated. 
			Integer result = future.get();
			
			System.out.println("future done? " + future.isDone());
			System.out.println("result: " + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 获取异步任务结果，使用超时配置
	 */
	@Test(expected=TimeoutException.class)
	public void testGetFutureResultWithTimeout() throws TimeoutException {
		Callable<Integer> task = () -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				return 123;
			}
			catch (InterruptedException e) {
				throw new IllegalStateException("task interrupted", e);
			}
		};
		
		// FixedThreadPool
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<Integer> future = executor.submit(task);
		
		// After submitting the callable to the executor we first check if the future has already been finished execution 
		System.out.println("future done? " + future.isDone());
		
		try {
			// Any call to future.get() will block and wait until the underlying callable has been terminated. 
			// In the worst case a callable runs forever - thus making your application unresponsive. 
			// You can simply counteract those scenarios by passing a timeout
			Integer result = future.get(1, TimeUnit.SECONDS); // task sleep 2 seconds, future.get will timeout 
			
			System.out.println("future done? " + future.isDone());
			System.out.println("result: " + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			throw e;
		} 
	}
	
	/**
	 * 批量提交任务到线程池
	 */
	@Test
	public void testInvokAll() throws InterruptedException {
		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> callables = Arrays.asList(
		        () -> "task1",
		        () -> "task2",
		        () -> "task3");

		// We first map each future to its return value and then print each value to the console. 
		executor.invokeAll(callables)
		    .stream()
		    .map(future -> {
		        try {
		            return future.get();
		        }
		        catch (Exception e) {
		            throw new IllegalStateException(e);
		        }
		    })
		    .forEach(System.out::println);
	}
	
	
	/**
	 * InvokeAny - 返回第一个执行完成的异步结果，。多个异步任务，哪个最先执行完成，就返回哪个。其它未完成的任务被cancel
	 * 
	 * 	Instead of returning future objects this method blocks until the first callable terminates and returns the result of that callable.
	 */
	@Test
	public void testInvokeAny() throws InterruptedException, ExecutionException {
		
		// ForkJoinPool 
		// Instead of using a fixed size thread-pool ForkJoinPools are created for a given parallelism size which per default is the number of available cores of the hosts CPU.
		// ForkJoinPools are created for a given parallelism size which per default is the number of available cores of the hosts CPU.
		ExecutorService executor = Executors.newWorkStealingPool();

		List<Callable<String>> callables = Arrays.asList(
		    callable("task1", 2),
		    callable("task2", 1),
		    callable("task3", 3));

		String result = executor.invokeAny(callables); // returning the result of one that has completed successfully
		System.out.println(result);
		
		TimeUnit.SECONDS.sleep(2);
		
		System.out.println(((ForkJoinPool)executor));
		
		// [Running, parallelism = 8, size = 5, active = 2, running = 1, steals = 1, tasks = 0, submissions = 0]
		
	}
	
	private Callable<String> callable(String result, long sleepSeconds) {
		return () -> {
			System.out.println(Thread.currentThread().getName() + " Starting ..");
			TimeUnit.SECONDS.sleep(sleepSeconds);
			return result;
		};
	}
	
	/**
	 * 任务调度线程池
	 */
	@Test
	public void testScheduledExecutors1() throws InterruptedException {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
		
		// 延迟3s后，启动任务的执行
		ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

		TimeUnit.MILLISECONDS.sleep(1337);

		long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
		System.out.printf("Remaining Delay: %sms", remainingDelay);
	}
	
	@Test
	public void testScheduledExecutors2() throws IOException {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());

		int initialDelay = 0;
		int period = 1;
		
		// 不管之前的任务是否已经结束，都按每隔1s的频率，执行下一次调度，
		executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
		
		System.in.read();
	}
	
	@Test
	public void testScheduledExecutors3() throws IOException {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

		Runnable task = () -> {
		    try {
		        TimeUnit.SECONDS.sleep(2);
		        System.out.println("Scheduling: " + System.nanoTime());
		    }
		    catch (InterruptedException e) {
		        System.err.println("task interrupted");
		    }
		};

		// 上一个任务执行完成后，延迟1s，再执行下一次调度
		executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
		
		System.in.read();
	}
	
	
}
