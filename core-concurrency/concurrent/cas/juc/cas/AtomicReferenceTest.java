package juc.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import bean.UserAccount;
import conc.util.CommonUtil;

/**
 * 原子更新基本类型的AtomicInteger，只能更新一个变量，如果要原子的更新多个变量，就需要使用这个原子更新引用类型提供的类。
 *
 */
public class AtomicReferenceTest {
	
	static ConcurrentHashMap<String, AtomicReference<UserAccount>> concurrentHashMap = new ConcurrentHashMap<>();
	static ExecutorService executor = Executors.newFixedThreadPool(100);
	
	public static void main(String[] args) throws Exception {
		List<Callable<Boolean>> tasks = new ArrayList<>();
		for(int i = 0; i < 30; i++) {
			tasks.add(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					int rand = new Random().nextInt(100000);
					UserAccount ua = new UserAccount("alice"+rand, rand);
					System.out.println(Thread.currentThread().getName() + "---" + ua);
					
					// 等待所有线程都打印完初始信息
					CommonUtil.sleep(100);
					
					int numRetries = 3;
					boolean isValueUpdated = false;
					do {
						isValueUpdated = insertIntoCache("account-alice", ua);
					} while(--numRetries > 0 && !isValueUpdated); // 失败之后，客户端选择重试。
					return true;
				}
			});
		}
		executor.invokeAll(tasks);
		executor.shutdown();
	}
	
	/**
	 * CAS比较的是对象的内存地址，相当于直接用==进行比较。
	 * 存在并发更新cachedValue的情况，因此会有线程会更新失败。
	 * CAS更新失败之后，客户端可以选择重试若干次，如果仍然不成功，可以选择放弃更新。
	 * 
	 * @param key	缓存的key
	 * @param updatedValue	要设置的新值
	 * @return 返回false表示更新失败---别的线程已经更新内存中的值了，已经不再是oldValue。
	 */
	public static boolean insertIntoCache(String key, UserAccount updatedValue) {
		// 如果不存在，则添加到缓存中。返回null，表示添加的时候key还不存在
		AtomicReference<UserAccount> cachedValue = concurrentHashMap.putIfAbsent(key, new AtomicReference<UserAccount>(updatedValue));
		
		// 如果是第一次添加，返回null，因此需要在添加之后重新对cachedValue赋值
		if(cachedValue == null) {
			cachedValue = concurrentHashMap.get(key);
		}
		
		// 获取到缓存中的值
		UserAccount oldValue = cachedValue.get();
		
		// 如果当前内存的值与oldValue相同，表明未被其它线程更新，则更新为updatedValue新值，
		boolean isValueUpdated = cachedValue.compareAndSet(oldValue, updatedValue);
		
		System.out.println(Thread.currentThread().getName() + "---current value="+cachedValue.get() +" :" + isValueUpdated);
		
		return isValueUpdated;
	}
	
}


