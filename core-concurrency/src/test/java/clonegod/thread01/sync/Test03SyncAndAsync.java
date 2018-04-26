package clonegod.thread01.sync;

/**
 * 对象锁的同步和异步问题
 *	- 同一个对象的非同步方法的执行，不受被锁保护的其它方法的执行影响。
 */
public class Test03SyncAndAsync {

	public synchronized void method1(){
		try {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public /** synchronized */ void method2(){
			System.out.println(Thread.currentThread().getName());
	}
	
	public static void main(String[] args) {
		
		final Test03SyncAndAsync mo = new Test03SyncAndAsync();
		
		/**
		 * 分析：
		 * t1线程先持有object对象的Lock锁，t2线程可以以“异步”的方式调用对象中的非synchronized修饰的方法
		 * t1线程先持有object对象的Lock锁，t2线程如果在这个时候调用对象中的同步（synchronized）方法则需等待，也就是线程“同步”
		 */
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				mo.method1();
			}
		},"t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				mo.method2();
			}
		},"t2");
		
		t1.start();
		t2.start();
		
	}
	
}
