package guava.concurrency;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

public class Test05MySimpleRateLimiter {
	
	@Test
	public void mySimpleRateLimiter() throws InterruptedException {
//		System.out.println(System.currentTimeMillis());
//		System.out.println(Instant.now().plusSeconds(1).toEpochMilli());
		
		// 服务端
		int permit = 1;
		RangeMap<Long, Semaphore> rangeMap = TreeRangeMap.create();
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		// 以固定速率添加permit, permit的数量可动态修改
		executor.scheduleAtFixedRate(()->{
			Instant now = Instant.now();
			Range<Long> range = Range.open(now.toEpochMilli(), now.plusSeconds(1).toEpochMilli());
			int more = ThreadLocalRandom.current().nextInt(1, 10);
			System.out.println(fulltime() + " -------------------more="+more);
			rangeMap.put(range, new Semaphore(permit+more));
		}, 0, 1, TimeUnit.SECONDS);
		
		
		// 客户端
		Executor executor2 = Executors.newFixedThreadPool(10);
		AtomicInteger ai = new AtomicInteger(0);
		while(true) {
			TimeUnit.SECONDS.sleep(1);
			for(int i=0 ;i <20; i++) {
				executor2.execute(() -> {
					try {
						handleRequest(rangeMap, ai.incrementAndGet());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		}
		
	}
	
	private void handleRequest(RangeMap<Long, Semaphore> rangeMap, int arg) throws InterruptedException {
		// gets a permit if one is available
		if (rangeMap.get(System.currentTimeMillis()).tryAcquire(1)) {
			TimeUnit.MILLISECONDS.sleep(200);
			System.out.println(fulltime() + " --------- Success! Server handle  request ..." + arg);
		} else {
			// Boo can't get in
			System.out.println(fulltime() + " please wait for a while, i am busy now");
		} 
	}
	
	private String fulltime() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
	}
}
