package Concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

/**
 * AsyncFunction + SettableFuture
 *
 */
public class Test04AsyncFunction2 {
	
	static AtomicInteger ai = new AtomicInteger();
	
	/**
	 * 使用AsyncFunction对Future的结果进行处理，
	 * 
	 * 在AsyncFunction中，使用SettableFuture设置返回结果
	 * @throws InterruptedException 
	 * 
	 */
	public static void main(String[] args) throws InterruptedException {
		ListeningExecutorService executorService = 
				MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
		
		for(int i=0; i<10;i++) {
			ListenableFuture<Long> listenableFuture = 
					executorService.submit(new Callable<Long>() {
						@Override
						public Long call() throws Exception {
							System.out.printf("[thread - %s] 请求获取1个数字 \n", 
									Thread.currentThread().getName());
							
							long number = ThreadLocalRandom.current().nextLong(10, 16);
							
							System.out.printf("\t[thread - %s] 获取到数字:%s \n", 
									Thread.currentThread().getName(), number);
							return number;
						}
					});
			
			ListenableFuture<String> stringFuture = 
					Futures.transformAsync(listenableFuture, 
										   new AsyncFunctionSample(executorService, CacheType.SMART), 
										   executorService);
			
			Futures.addCallback(stringFuture, new FutureCallback<String>() {
				@Override
				public void onSuccess(String input) {
					System.out.println("hex = " + input);
				}
				@Override
				public void onFailure(Throwable t) {
					t.printStackTrace();
				}
			}, executorService);
		}
		
		TimeUnit.SECONDS.sleep(10);
		executorService.shutdown();
		executorService.awaitTermination(5, TimeUnit.SECONDS);
		
	}
	
	enum CacheType {
		SIMPLE, SMART;
	}
	
	private static class AsyncFunctionSample implements AsyncFunction<Long, String> {
		private static ConcurrentMap<Long, String> cacheV1 = Maps.newConcurrentMap();
		
		private static ConcurrentMap<Long, ListenableFutureTask<String>> cacheV2 = Maps.newConcurrentMap();
		
		CacheType cacheType;
		
		private ListeningExecutorService listeningExecutorService;
		
		public AsyncFunctionSample(ListeningExecutorService executorService, CacheType cacheType) {
			this.listeningExecutorService = executorService;
			this.cacheType = cacheType;
		}
		
		@Override
		public ListenableFuture<String> apply(final Long input) throws Exception {
			if(cacheType == CacheType.SIMPLE) {
				return calcAndCacheV1(input);
			} 
			if(cacheType == CacheType.SMART) {
				return calcAndCacheV2(input);
			}
			return null;
		}

		/**
		 * 当两个线程同时调用时，存在一个bug，会造成它们计算相同的值
		 * 
		 */
		private ListenableFuture<String> calcAndCacheV1(final Long input) {
			
			// use the SettableFuture class to create a Future
			// object and set the value with the retrieved value from the map
			if(cacheV1.containsKey(input)) {
				System.out.printf("\t[thread - %s] ------- 缓存命中，不需要计算, input=%s \n", 
						Thread.currentThread().getName(), input);
				
				// 手动预先设置value的ListenableFuture
				SettableFuture<String> listenableFuture = SettableFuture.create();
				listenableFuture.set(cacheV1.get(input));
				
				return listenableFuture;
			} else {
				System.out.printf("\t[thread - %s] 缓存未命中，执行计算, input=%s \n", 
						Thread.currentThread().getName(), input);
				// 异步计算
				ListenableFuture<String> listenableFuture = listeningExecutorService.submit(new Callable<String>() {
					@Override
					public String call() throws Exception {
						String retrived = SomeService.get(input);
						cacheV1.putIfAbsent(input, retrived);
						return retrived;
					}
				});
				return listenableFuture;
			}
		}
		
		/**
		 * 避免重复计算相同的数据
		 * 
		 */
		private ListenableFuture<String> calcAndCacheV2(final Long input) {
			while(true) {
				ListenableFutureTask<String> ft = cacheV2.get(input);
				
				if(ft == null) {
					// 创建一个异步任务
					ListenableFutureTask<String> futureTask = ListenableFutureTask.create(new Callable<String>() {
						@Override
						public String call() throws Exception {
							return SomeService.get(input);
						}
					});
					
					// 原子化复合检查
					ft = cacheV2.putIfAbsent(input, futureTask);
					if(ft == null) {
						ft = futureTask;
						// 执行新的计算
						listeningExecutorService.submit(futureTask);
						System.out.printf("\t[thread - %s] 缓存未命中，执行计算, input=%s \n", 
								Thread.currentThread().getName(), input);
					}
				} else {
					System.out.printf("\t[thread - %s] ------- 缓存命中，不需要计算, input=%s \n", 
							Thread.currentThread().getName(), input);
				}
				
				return ft; // 客户端ft.get(timeout, timeunit) 会阻塞获取结果。只要计算完成后，其它等待的线程也能拿到结果。
			}
		}
		
	}
	
	private static class SomeService {
		public static String get(Long input) {
			try {
				TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(10, 100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Long.toHexString(input);
		}
	}
	
	
}
