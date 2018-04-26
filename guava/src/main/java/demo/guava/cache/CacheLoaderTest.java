package demo.guava.cache;

/**
 * CacheLoader - 缓存加载器
 * 
 * 创建CacheLoader的三种方式：
 * 1. 使用CacheLoader实现类
 * 		new CacheLoader<String, TradeAccount>() {
			@Override
			public TradeAccount load(String key) throws Exception {
				return TradeAccount.getTradeAccountById(key);
			}
		}
 * 
 * 2. 使用Function
 * 		CacheLoader<Key,value> cacheLoader = CacheLoader.from(Function<Key,Value> func);
 * 
 * 3. 使用Supplier
 * 		CacheLoader<Object,Value> cacheLoader = CacheLoader.from(Supplier<Value> supplier);
 * 
 * @author clonegod@163.com
 *
 */
public class CacheLoaderTest {
	
}
