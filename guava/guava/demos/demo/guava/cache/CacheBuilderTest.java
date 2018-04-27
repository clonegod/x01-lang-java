package demo.guava.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;

/**
 * 缓存过期策略：
 * 
 * expireAfterWrite(duration, unit) - 超时后清除
 * 	invalidate cache entries once a fixed duration has elapsed after the entry's creation
 * 
 * expireAfterAccess(duration, unit) - 超时后清除
 *  invalidate cache entries based on how much time has elapsed since an entry was last accessed
 * 
 * refreshAfterWrite(5L,TimeUnit.SECONDS) - 超时后刷新
 *  Instead of removing values explicitly, we are refreshing values after a given amount of time has passed
 *  Note that the trigger for the refreshing values is activated when the value is requested and the time limit has expired.
 * 
 * @author clonegod@163.com
 *
 */
public class CacheBuilderTest {
	static ExecutorService executorService = Executors.newFixedThreadPool(10);
	
	public static void main(String[] args) throws ExecutionException {
		LoadingCache<String, TradeAccount> tradeAccountCache =
			CacheBuilder.newBuilder()
				.concurrencyLevel(10) // default is 4
				.expireAfterWrite(5L, TimeUnit.MINUTES) // 缓存一旦写入就开始计算超时时间
				//.expireAfterAccess(duration, unit) // 缓存被第一次访问后才开始计算超时时间
				//.refreshAfterWrite(5L,TimeUnit.SECONDS) // 写入后开始计算超时，若访问时已超时，则执行refresh
				.maximumSize(3L) // 设置缓存最大容量
				//.softValues()
				//.weakKeys()
				//.weakValues()
//				.removalListener(new TradeAccountRemovalListener()) // 缓存清除时的监听器
				.removalListener(
						// process removal notifcations asynchronously. 
						RemovalListeners.asynchronous(
								new TradeAccountRemovalListener(), 
								executorService)) 
				.ticker(Ticker.systemTicker()) // provides nanosecond-level precision for when entries should be expired
				.recordStats() // record CacheStats
				.build(new CacheLoader<String, TradeAccount>() {
					// will be used to retrieve the TradeAccount objects when
					// a key is presented to the cache and the value is not present
					@Override
					public TradeAccount load(String key) throws Exception {
						System.out.println("Loading: " + key);
						return TradeAccount.getTradeAccountById(key);
					}
				});
		
		tradeAccountCache.get("Alice");
		tradeAccountCache.get("Bob");
		tradeAccountCache.get("Cindy");
		
		// 添加第4个新元素，会清除已有的某个缓存(缓存总大小为3)
		/**
		 * Less recently used entries are subject to be removed 
		 * as the size of the cache approaches the maximum size number
		 */
		tradeAccountCache.get("Dugg");
		
		CacheStats cacheStats = tradeAccountCache.stats();
		System.out.println(cacheStats);
		
	}
}

/**
 * receive notifcations when an entry has been removed from the cache
 */
class TradeAccountRemovalListener implements RemovalListener<Object, Object>{
	// 缓存被清除时的回调函数
	@Override
	public void onRemoval(RemovalNotification<Object, Object> notification) {
		System.out.println(
				"Warnning: Cache onRemoval:" + 
				notification.getKey()
				+":"
				+notification.getValue()
				+":"
				+notification.getCause());
	}

}

class TradeAccount {
	String id;
	String owner;
	double balance;
	
	public TradeAccount(String id, String owner, double balance) {
		this.id = id;
		this.owner = owner;
		this.balance = balance;
	}

	static TradeAccount getTradeAccountById(String key) {
		return new TradeAccount(
				key,
				"owner",
				ThreadLocalRandom.current().nextDouble(100)
			);
	}
}