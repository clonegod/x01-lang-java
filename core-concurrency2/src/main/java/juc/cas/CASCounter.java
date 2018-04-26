package juc.cas;

/**
 * 基于SimulateCAS非阻塞机制，是线程安全的。
 *
 */
public class CASCounter {
	private SimulatedCAS value;
	
	public int getValue() {
		return value.get();
	}
	
	/**
	 * 	CasCounter不会阻塞，但如果其它线程同时更新计数器，那么会多次执行重试操作。
	 * 
	 * @return 递增1
	 */
	public int increment() {
		int v;
		/**不会造成线程阻塞，如果CAS操作失败，立即重试该操作。*/
		do {
			v = getValue();
		} while(v != value.compareAndSwap(v, v + 1));
		return v + 1;
	}
}

