package thread02.threadlocal;


/**
 * 线程局部变量
 * 	使用ThreadLocal 在线程上绑定变量，一般用来简化参数在方法间的传递
 * 	每个线程Thread内部都有一个threadLocals变量，类型为ThreadLocal.ThreadLocalMap
 * 		实例化：t.threadLocals = new ThreadLocalMap(threadLocal, firstValue);
 * 	1个线程内部的threadLocals可以为多个threadLocal实例绑定相应的value。不同线程间互不影响。
 *	
 *	ThreadLocal
 *		ThreadLocalMap - key: ThreadLocal实例, value: xxx Object
 *			ThreadLocalMap.Entry extends WeakReference<ThreadLocal>
 *
 *	当线程退出运行时，会执行线程内部的exit方法，将threadLocals置为null，线程上绑定的局部变量可被垃圾回收。
 */
public class TestThreadLocal {

	public ThreadLocal<String> th = new ThreadLocal<String>();
	
	public void setTh(String value){
		th.set(value);
	}
	public void getTh(){
		System.out.println(Thread.currentThread().getName() + ":" + this.th.get());
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		final TestThreadLocal ct = new TestThreadLocal();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				ct.setTh("张三");
				ct.getTh();
			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					// ct.setTh("李四");
					ct.getTh();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t2");
		
		t1.start();
		t2.start();
	}
	
}
