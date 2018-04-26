package demo.guava.concurrency.asyncfuntion;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.SettableFuture;

public class CalcAsyncFuntion implements AsyncFunction<Long, String> {
	
	private ConcurrentMap<Long, String> cachedResutMap = Maps.newConcurrentMap();
	
	private ListeningExecutorService listeningExecutorService;

	public CalcAsyncFuntion(ListeningExecutorService listeningExecutorService) {
		this.listeningExecutorService = listeningExecutorService;
	}

	/**
	 * AsyncFunction：异步计算
	 * 	1. 如果resultMap已经存在被计算的key，则直接返回结果
	 * 	2. 否则，再次通过异步调用，进行计算，再返回结果
	 */
	@Override
	public ListenableFuture<String> apply(final Long input) throws Exception {
		TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
		// 缓存中已经存在被计算的key
		if (cachedResutMap.containsKey(input)) {
			// 使用SettableFuture封装一个已知的值，返回ListenableFuture类型的结果
			SettableFuture<String> listenableFuture = SettableFuture.create();
			listenableFuture.set(cachedResutMap.get(input));
			return listenableFuture;
		} else {
			// 异步计算
			return listeningExecutorService.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					String retrieved = calc(input);
					cachedResutMap.putIfAbsent(input, retrieved);
					return retrieved;
				}
			});
		}
	}

	protected String calc(Long input) {
		System.out.println("Execute for input: " + input);
		return String.valueOf(input + 1);
	}
}
