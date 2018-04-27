package Caches;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;

/**
 * http://www.baeldung.com/guava-cache
 * Guava cache 的使用
 * 
 */
public class Test02LocalCache {

	/**
	 * Basic Use Guava Cache
	 * 
	 * Notice how there is no value in the cache for our “hello” key – and so the value is computed and cached.
	 */
	@Test
	public void whenCacheMiss_thenValueIsComputed() {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(String key) {
	            return key.toUpperCase();
	        }
	    };
	 
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder().build(loader);
	 
	    assertEquals(0, cache.size());
	    assertEquals("HELLO", cache.getUnchecked("hello"));
	    assertEquals(1, cache.size());
	}
	
	/**
	 *  Eviction Policies
	 *		Every cache needs to remove values at some point. Let’s discuss the mechanism of evicting values out of the cache – using different criteria.
	 *
	 * 	Eviction by Size
	 */
	@Test
	public void whenCacheReachMaxSize_thenEviction() {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(String key) {
	            return key.toUpperCase();
	        }
	    };
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder()
	    		.maximumSize(3) //  limit the size of our cache using maximumSize().
	    		.build(loader);
	 
	    cache.getUnchecked("first");
	    cache.getUnchecked("second");
	    cache.getUnchecked("third");
	    cache.getUnchecked("forth");
	    assertEquals(3, cache.size());
	    assertNull(cache.getIfPresent("first")); // If the cache reaches the limit, the oldest items will be evicted.
	    assertEquals("FORTH", cache.getIfPresent("forth"));
	}
	
	/**
	 * Eviction by Weight
	 * 	
	 */
	@Test
	public void whenCacheReachMaxWeight_thenEviction() {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(String key) {
	            return key.toUpperCase();
	        }
	    };
	 
	    Weigher<String, String> weighByLength;
	    weighByLength = new Weigher<String, String>() {
	        @Override
	        public int weigh(String key, String value) {
	            return value.length(); // use the length as our custom weight function
	        }
	    };
	 
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder()
	      .maximumWeight(16) // weight is only used to determine whether the cache is over capacity; it has no effect on selecting which entry should be evicted next
	      .weigher(weighByLength) //  limit the cache size using a custom weight function
	      .build(loader);
	 
	    cache.getUnchecked("first");
	    cache.getUnchecked("second");
	    cache.getUnchecked("third");
	    cache.getUnchecked("last");
	    assertEquals(3, cache.size());
	    assertNull(cache.getIfPresent("first"));
	    assertEquals("LAST", cache.getIfPresent("last"));
	}
	
	/**
	 * Eviction by Time
	 * 	 use time to evict old records
	 */
	@Test
	public void whenEntryIdle_thenEviction() throws InterruptedException {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(String key) {
	            return key.toUpperCase();
	        }
	    };
	 
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder()
	      .expireAfterAccess(2,TimeUnit.MILLISECONDS) // remove records that have been idle for 2ms
	      .build(loader);
	 
	    cache.getUnchecked("hello");
	    assertEquals(1, cache.size());
	 
	    cache.getUnchecked("hello");
	    Thread.sleep(300);
	 
	    cache.getUnchecked("test");
	    assertEquals(1, cache.size());
	    assertNull(cache.getIfPresent("hello"));
	}
	
	/**
	 * Eviction by Time
	 * 	evict records based on their total live time
	 */
	@Test
	public void whenEntryLiveTimeExpire_thenEviction() throws InterruptedException {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(String key) {
	            return key.toUpperCase();
	        }
	    };
	 
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder()
	      .expireAfterWrite(2,TimeUnit.MILLISECONDS) // the cache will remove the records after 2ms of being stored:
	      .build(loader);
	 
	    cache.getUnchecked("hello");
	    assertEquals(1, cache.size());
	    Thread.sleep(300);
	    cache.getUnchecked("test");
	    assertEquals(1, cache.size());
	    assertNull(cache.getIfPresent("hello"));
	}
	
	/**
	 * Weak Keys - 当一个对象的所有引用都是weak reference时（没有其它强引用关系时），允许gc回收这部分内存。
	 * 	 allowing the garbage collector to collect cache key that are not referenced elsewhere.
	 * 
	 */
	@Test
	public void whenWeakKeyHasNoRef_thenRemoveFromCache() {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(String key) {
	            return key.toUpperCase();
	        }
	    };
	 
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder().weakKeys().build(loader);
	}
	
	/**
	 * Soft Values - 直到内存不够时，gc才会清理这部分内存
	 * 	allow garbage collector to collect our cached values
	 */
	@Test
	public void whenSoftValue_thenRemoveFromCache() {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(String key) {
	            return key.toUpperCase();
	        }
	    };
	 
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder().softValues().build(loader);
	}
	
	
	/**
	 * Handle null Values
	 * 	LoadingCache is a concurrent map, it doesn’t allow null keys or values.
	 * 
	 * 	By default, Guava Cache will throw exceptions if you try to load a null value – as it doesn’t make any sense to cache a null.
	 * 	But if null value means something in your code, then you can make good use of the Optional class 
	 */
	@Test
	public void whenNullValue_thenOptional() {
	    CacheLoader<String, Optional<String>> loader;
	    loader = new CacheLoader<String, Optional<String>>() {
	        @Override
	        public Optional<String> load(String key) {
	            return Optional.fromNullable(getSuffix(key));
	        }
	    };
	 
	    LoadingCache<String, Optional<String>> cache;
	    cache = CacheBuilder.newBuilder().build(loader);
	 
	    assertEquals("txt", cache.getUnchecked("text.txt").get());
	    assertFalse(cache.getUnchecked("hello").isPresent());
	}
	private String getSuffix(final String str) {
	    int lastIndex = str.lastIndexOf('.');
	    if (lastIndex == -1) {
	        return null;
	    }
	    return str.substring(lastIndex + 1);
	}

	/**
	 * Refresh the Cache
	 * 	We can refresh our cache automatically using refreshAfterWrite().
	 * 
	 * 	Note: You can refresh specific record manually using refresh(key).
	 */
	@Test
	public void whenLiveTimeEnd_thenRefresh() {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(String key) {
	            return key.toUpperCase();
	        }
	    };
	 
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder()
	      .refreshAfterWrite(1,TimeUnit.MINUTES)
	      .build(loader);
	}
	
	/**
	 * Preload the Cache
	 * 	We can insert multiple records in our cache using putAll() method. 
	 */
	@Test
	public void whenPreloadCache_thenUsePutAll() {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(String key) {
	            return key.toUpperCase();
	        }
	    };
	 
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder().build(loader);
	 
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("first", "FIRST");
	    map.put("second", "SECOND");
	    cache.putAll(map);
	 
	    assertEquals(2, cache.size());
	}
	
	/**
	 * RemovalNotification
	 * 	take some actions when a record is removed from the cache; 
	 * 	register a RemovalListener to get notifications of a record being removed. 
	 * 	We also have access to the cause of the removal – via the getCause() method.
	 */
	@Test
	public void whenEntryRemovedFromCache_thenNotify() {
	    CacheLoader<String, String> loader;
	    loader = new CacheLoader<String, String>() {
	        @Override
	        public String load(final String key) {
	            return key.toUpperCase();
	        }
	    };
	 
	    // 缓存被清除时的监听器
	    RemovalListener<String, String> listener;
	    listener = new RemovalListener<String, String>() {
	        @Override
	        public void onRemoval(RemovalNotification<String, String> n){
	        	System.out.printf("Evicted, key: %s, value: %s, cause by: %s\n", n.getKey(), n.getValue(), n.getCause().name());
	            if (n.wasEvicted()) {
	                String cause = n.getCause().name();
	                assertEquals(RemovalCause.SIZE.toString(), cause); // 记录缓存key被清理的原因
	            }
	        }
	    };
	 
	    LoadingCache<String, String> cache;
	    cache = CacheBuilder.newBuilder()
	      .maximumSize(3) // 最多存3个key
	      .removalListener(listener)
	      .recordStats() // 启用缓存使用统计功能
	      .build(loader);
	 
	    for(int i=0; i<2; i++) {
	    	cache.getUnchecked("first"); 
	    	cache.getUnchecked("second");
	    	cache.getUnchecked("third");
	    	cache.getUnchecked("last"); // first 被清理
	    	assertEquals(3, cache.size());
	    	System.out.println(cache.stats());
	    }
	}
	
	/**
	 * additional notes
	 * 	it is thread-safe 多线程安全  LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>
	 * 	you can insert values manually into the cache using put(key,value)  可以手动管理缓存
	 * 	you can measure your cache performance using CacheStats ( hitRate(), missRate(), ..)  提供缓存使用统计报告
	 */
}
