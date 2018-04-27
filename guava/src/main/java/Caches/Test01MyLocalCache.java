package Caches;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.collect.Lists;

public class Test01MyLocalCache {
	/**
	 * 不借助第三方缓存框架，自己实现一个本地缓存该怎么做？
	 * 	1、多线程安全
	 * 	2、缓存清除
	 * 
	 * @throws InterruptedException 
	 * @throws ExecutionException 
	 */
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		IpCounter ipCounter = new IpCounter();
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		Collection<Callable<String>> tasks = Lists.newArrayList();
		for(int i=0; i<10; i++) {
			tasks.add(() -> {
				String threadName = Thread.currentThread().getName();
				long count = ipCounter.increment(Thread.currentThread().getName()); 
				return threadName + " => " + count;
			});
		}
		
		List<Future<String>> futures = executor.invokeAll(tasks);
		
		futures.forEach( f -> {
			try {
				System.out.println(f.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		executor.shutdown();
		
		System.out.println(ipCounter.getCount("pool-1-thread-1"));
		
		ipCounter.clear();
	}
	
	/**
	 * 统计每天独立IP的请求次数
	 *
	 */
	private static class IpCounter {
		final ConcurrentMap<String, AtomicLong> cacheMap = new ConcurrentHashMap<>(16);
		
		public long increment(String ip) {
			AtomicLong al = null;
			while(al == null) {
				al = cacheMap.putIfAbsent(ip, new AtomicLong(0));
			}
			return al.incrementAndGet();
		}
		
		public long getCount(String ip) {
			return cacheMap.getOrDefault(ip, new AtomicLong(0)).get();
		}
		
		public void clear() {
			cacheMap.clear();
		}
	}
}
