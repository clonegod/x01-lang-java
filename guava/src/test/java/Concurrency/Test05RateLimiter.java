package Concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;

/**
 * The RateLimiter class operates somewhat like a semaphore but instead of
 * restricting access by the number of concurrent threads, the RateLimiter class
 * restricts access by time, meaning how many threads can access a resource per
 * second.
 * 
 * It is used in the same way we would use a semaphore.
 *
 */
public class Test05RateLimiter {

	AtomicInteger ai = new AtomicInteger(0);
	
	@Test
	public void test1() throws InterruptedException {
		Executor executor = Executors.newFixedThreadPool(10);
		while(true) {
			for(int i=0 ;i <20; i++) {
				executor.execute(() -> {
					try {
						handleRequest(ai.incrementAndGet());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
			TimeUnit.SECONDS.sleep(1);
		}
	}

	// 在1秒内，无论接收到多少个请求，只允许permit个请求通过
	static final RateLimiter LIMITER = RateLimiter.create(6.0); // 每秒放行6个请求
	
	private static void handleRequest(int arg) throws InterruptedException {
		// gets a permit if one is available
		if (LIMITER.tryAcquire()) {
			TimeUnit.MILLISECONDS.sleep(200);
			System.out.println("handle  request ..." + arg);
		} else {
			// Boo can't get in
			System.out.println("please wait for a while, i am busy now");
		} 
	}

}
