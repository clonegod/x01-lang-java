package clonegod.conc01.collection.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import clonegod.concurrency.util.CommonUtil;

/**
 * ConcurrentHashMap-线程安全
 * 	- Segment 分段，多线程在"不同段"上可以并发写，实现无锁并发。
 * 		如果并发修改发生在同一个Segment，则对该Segment进行加锁同步。
 * 	优点：
 * 		分段锁技术，将Map分为N个段，不同段上的并发互不影响，从而避免了锁竞争，更好的支持高并发。
 * 
 *	ConcurrentHashMap提供的几个具有原子性保证的复合操作：
		concHashMap.replace(key, value)
		concHashMap.replace(key, oldValue, newValue)
		concHashMap.remove(key, value)
		concHashMap.putIfAbsent(key, value)
 *	
 */
public class TestConcurrentHashMap {
	
	final static ConcurrentHashMap<Integer, Integer> concHashMap = new ConcurrentHashMap<>();
	
	public static void main(String[] args) throws InterruptedException {
		int nThreads = 200;
		
		final CountDownLatch latch = new CountDownLatch(nThreads);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					for(Integer key : concHashMap.keySet()) {
						Integer value = concHashMap.get(key);
						if(! key.equals(value)) {
							System.err.println(key+"!="+value);
							System.exit(0);
						}
					}
					System.out.println("check hashmap done");
					CommonUtil.sleep(10);
				}
			}
		}).start();
		
		for(int i = 0; i < nThreads; i++)  {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < 100000; j++) {
						concHashMap.put(j, j);
					}
					latch.countDown();
				}
			}).start();
		}
		
		latch.await();
		
		System.exit(0);
	}
}
