package juc.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.CommonUtil;
import util.UserAccount;

/**
 * 更新操作时，直接通过ConcurrentHashMap的put来更新缓存---存在更新丢失的问题
 *
 */
public class AtomicReferenceNotUseTest {
	
	static ConcurrentHashMap<String, UserAccount> concurrentHashMap = new ConcurrentHashMap<>();
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
					CommonUtil.threadSleep(100);
					
					insertIntoCache("account-alice", ua);
					return true;
				}
			});
		}
		executor.invokeAll(tasks);
		executor.shutdown();
	}
	
	public static void insertIntoCache(String key, UserAccount updatedValue) {
		// concurrentHashMap.replace(key, oldValue, newValue) --- 具有CAS效果
		concurrentHashMap.put(key, updatedValue);
		System.out.println(Thread.currentThread().getName() + "---current value="+concurrentHashMap.get(key));
	}
	
}


