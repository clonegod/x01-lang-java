package juc.cas;

/**
 * 用同步synchronized来实现CAS的语义（不是CAS的实现或性能）
 *
 */
public class SimulatedCAS {
	private int value;
	
	public synchronized int get() {
		return value;
	}
	
	/**
	 * 
	 * @param expectedValue	期待将被更新的变量在内存中的值
	 * @param newValue	将要对该变量设置的新值
	 * @return
	 */
	public synchronized int compareAndSwap(int expectedValue, int newValue) {
		int oldValue = value;
		if(oldValue == expectedValue) { // 如果内存中的值与期望值相同，则执行更新操作
			value = newValue;
		}
		return oldValue; // 返回原来的值
	}
	
	/**
	 * 
	 * @param expectedValue
	 * @param newValue
	 * @return 返回true表示CAS操作成功，否则失败。
	 */
	public synchronized boolean compareAndSet(int expectedValue, int newValue) {
		return expectedValue == compareAndSwap(expectedValue, newValue);
	}
}
