package baeldung.concurrentmap.ConcurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;

import juc.ThreadSafe;

public class Test07ConcurrentHashMap {
	
	public static void main(String[] args) {
		ThreadSafeConcurrentHashMap.test();
	}
}

/**
 *
map.putIfAbsen(key, value) 与下面的代码功能相同： 

 if (!map.containsKey(key))
   return map.put(key, value);
 else
   return map.get(key);

但是在多线程环境下，两者的表现还是有区别的：
map.putIfAbsen(key, value) 可以保证“先检查再执行”是原子操作的。

测试结果：两个线程并发对同一个ConcurrentHashMap调用putIfAbsent(key,value)，结果是正确的，该方法保证了数据操作的原子性！

0---Thread-0...0
1---Thread-0...1
2---Thread-1,,,2
3---Thread-1,,,3
4---Thread-1,,,4
5---Thread-0...5
6---Thread-0...6
7---Thread-0...7
8---Thread-1,,,8
9---Thread-0...9
 *
 */
@ThreadSafe
class ThreadSafeConcurrentHashMap {
	
	public static void test() {
		
		final ConcurrentHashMap<String, String> shareMap = new ConcurrentHashMap<String, String>();
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				for(int i=0; i<10; i++) {
					shareMap.putIfAbsent(String.valueOf(i), Thread.currentThread().getName()+"..."+i);
					try { Thread.sleep(10); } catch (InterruptedException e) {}
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				for(int i=0; i<10; i++) {
					shareMap.putIfAbsent(String.valueOf(i), Thread.currentThread().getName()+",,,"+i);
					try { Thread.sleep(10); } catch (InterruptedException e) {}
				}
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(String key : shareMap.keySet()) {
			System.out.println(key+"---"+shareMap.get(key));
		}
	}
	
}