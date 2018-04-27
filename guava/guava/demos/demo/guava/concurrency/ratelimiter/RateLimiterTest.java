package demo.guava.concurrency.ratelimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * RateLimiter
 * 	限流器：每秒释放N个permit，限制任务的提交频率。
 * 
 * @author clonegod@163.com
 *
 */
public class RateLimiterTest {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(20);
		
		// don't want more than 5 tasks submitted per second
		RateLimiter limiter = RateLimiter.create(5.0);
		
		for(int i=0; i<16; i++) {
//			limiter.acquire(1);
			
			// permitsPerSecond: the rate limiter will release one permit every (1.0 / permitsPerSecond) seconds
			// permitsPerSecond = 5, 则平均1个permit为200ms，小于200ms则超时几率很大
			boolean acquireSuccess = limiter.tryAcquire(1, 200, TimeUnit.MILLISECONDS);
			if(! acquireSuccess) {
				System.err.println(
						Thread.currentThread().getName() + ":" +
						"System Busy, Try Again Later");
			} else {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						System.out.println(
								String.format("%s:\t%s", 
										Thread.currentThread().getName(),
										new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS").format(new Date())
										));
					}
				});
			}
		}
		
		executor.awaitTermination(5, TimeUnit.SECONDS);
		executor.shutdown();
	}
}
