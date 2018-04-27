package demo.guava.cache;

import java.util.concurrent.Callable;

import com.google.common.util.concurrent.Callables;

public class CacheTest {
	/**
	 * 生成一个不需要异步计算的Callable
	 * don't need/want to execute an asynchronous task
	 */
	public void testSyncCallable() {
		String givenValue = "getValueFromSomeWhere";
		Callable<String> value = Callables.returning(givenValue);
	}
	
	/**
	 * 通过Callable异步返回value
	 * a Callable object implies that an asynchronous operation could have occurred
	 */
	public void testAsyncCallable() {
		Callable<String> value = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "getValueFromDB";
			}
		};
	}
	
	public void testInvalidateCache() {
		// cache.invalidate(key)
		// cache.invalidateAll()
		// cache.invalidateAll(Iterable<?> keys)
	}
	
	public void testRefreshCache() {
		// cache.refresh(key)
	}
	
	
}
