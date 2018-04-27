package Caches;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

/**
 * Local caching, done right, and supporting a wide variety of expiration behaviors.
 * 
 * LoadingCache - 本地缓存，非分布式共享缓存！
 * 	1. 缓存过期时间设置
 *  2. 缓存数据量设置
 *  3. 缓存未命中时，自动调用CacheLoader从外部查询数据
 *	4. 缓冲池其它参数的配置
 *
 *	--------------------------
 *	Question 1: 本地缓存可以用在哪些场景？本地缓存有哪些其它方式来实现？
 *
 *	Question 2: 本地缓存与分布式缓存有哪些异同？
 *
 *	Question 3: 分布式缓存的使用需要注意什么？
 *		写入：集群中各节点的数据均衡分布 - key的设计
 *		读取：精确匹配，避免模糊查询
 *		参数调优：
 *	Question 4: redis集群不可用可能由哪些因素导致？
 *		keys *
 *		未使用的数据存在于内存中，但是未设过期时间，严重浪费内存
 *		缓存分布不均衡，某个节点严重过载
 *		
 */
public class Test03LocalCacheUsage {
	
	@Test
	public void testLocalCache() throws Exception {
		// create a cache for employees based on their employee id
		LoadingCache<String, Employee> employeeCache = 
			CacheBuilder.newBuilder()
				.softValues()
				.initialCapacity(50)
				.maximumSize(1) // maximum 100 records can be cached --- 最多缓存100条数据
//				.expireAfterAccess(30, TimeUnit.MINUTES)  // cache will expire after 30 minutes of access
				.expireAfterWrite(10, TimeUnit.SECONDS)
				.concurrencyLevel(4) // 并发更新缓存的线程数量-太大，浪费内存；太小，会引起多线程竞争
				.recordStats() // 开启统计功能
				.build(new CacheLoader<String, Employee>(){
					// build the cacheloader
					@Override
					public Employee load(String empId) throws Exception {
						// make the expensive call
						// the value is computed and cached.
						return DB.getFromDatabase(empId); // 缓存不命中时，从数据库查询，并将对结果进行缓存
					}
			});
		
		try {
			while(true) {
				//on first invocation, cache will be populated with corresponding employee record
				System.out.println("\nInvocation #1");
				System.out.println(employeeCache.get("100"));
				System.out.println(employeeCache.get("101"));
				System.out.println(employeeCache.get("102"));
				
				TimeUnit.SECONDS.sleep(new Random().nextInt(2));
				
				//second invocation, data will be returned from cache
				System.out.println("\nInvocation #2");
				System.out.println(employeeCache.get("100"));
				System.out.println(employeeCache.get("101"));
				System.out.println(employeeCache.get("102"));
				
				CacheStats stats = employeeCache.stats();
				System.out.println(stats.toString());
				
				TimeUnit.SECONDS.sleep(new Random().nextInt(2));
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testLocalCache2() throws Exception {
		// create a cache for employees based on their employee id
		LoadingCache<String, Employee> employeeCache = 
				CacheBuilder.newBuilder()
				.initialCapacity(50)
				.maximumSize(100) // maximum 100 records can be cached --- 最多缓存100条数据
				//.expireAfterAccess(3, TimeUnit.SECONDS)
				.expireAfterWrite(30, TimeUnit.SECONDS)
				.concurrencyLevel(4) // 并发更新缓存的线程数量-太大，浪费内存；太小，会引起多线程竞争
				// cache will expire after 30 minutes of access
				.build(new CacheLoader<String, Employee>(){
					// build the cacheloader
					@Override
					public Employee load(String empId) throws Exception {
						// make the expensive call
						return DB.getFromDatabase(empId); // 缓存不命中时，从数据库查询，并将对结果进行缓存
					}
				});
		
		System.out.print(1);
		employeeCache.get("100");
		
		System.out.print(2);
		employeeCache.refresh("100"); // 手动刷新缓存
		
		System.out.print(3);
		employeeCache.get("100");
		
		System.out.print(4);
		employeeCache.invalidate("100"); // 手动删除缓存
		
		System.out.print(5);
		employeeCache.get("100");
		
		System.out.println(employeeCache.size());
	}
		
	
}

