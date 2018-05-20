package conc.collection.map;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import conc.util.CommonUtil;

/**
 * HashMap-多线程并发不安全
 * 
 * 问题现象：
 *	1、 多线程并发修改HashMap，可能会发生死循环！
 *  2、 也可能发生Exception in thread "Thread-0" java.util.ConcurrentModificationException
 *  
 *  死循环的原因：
 *  	导致HashMap线程不安全的根本原因是扩容。扩容就是在put加入元素的个数超过capacity * loadFactor的时候就会将内部Entry数组大小扩大至原来的2倍，
 *  	然后将数组元素按照新的数组大小重新计算索引，放在新的数组中，同时修改每个节点的链表关系（主要是next和节点在链表中的位置）。
 *		如果多个线程同时进行扩容，由于线程的执行顺序的不确定性，导致多个线程操作节点的next值会发生不确定的变化，造成数据丢失，或者形成循环链表等问题，导致线程不安全。
 *
 *	ConcurrentModificationException的原因：
 *		当读线程正在读取某个Entry时，另一个线程修改正在读取的Entry，从而引发并发修改异常
 */
public class TestHashMapNotThreadSafe {
	
	final static Map<Integer, Integer> hashMap = new HashMap<>();
	
	public static void main(String[] args) throws InterruptedException {
		int nThreads = 200;
		
		final CountDownLatch latch = new CountDownLatch(nThreads);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					for(Integer key : hashMap.keySet()) {
						Integer value = hashMap.get(key);
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
						hashMap.put(j, j);
					}
					latch.countDown();
				}
			}).start();
		}
		
		latch.await();
		
		System.exit(0);
	}
	
}
