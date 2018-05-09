package juc.sapmles;

/**
 * ThreadLocal 
 *	每个线程与持有的数值对象进行关联，它为每个线程维护一份独立的数据拷贝。
 *
 *	get()总是返回当前执行线程通过set()设置的最新值。
 *
 *	线程终止后，这些设置的值会被垃圾回收。
 */
public class Test04TheadLocal {
	
	// TheadLocal： key-Thread, value-Object
	public static final ThreadLocal<Object> tl = new ThreadLocal<Object>() {
		@Override
		protected Object initialValue() {
			return "";
		}
	};
	
	public static void set(Object i) {
		tl.set(i);
	}
	
	public static Object get() {
		return tl.get();
	}
	
	public static void main(String[] args) {
		TLTask task = new TLTask();
		new Thread(task, "Thread01").start();
		new Thread(task, "Thread02").start();
		
	}
}

class TLTask implements Runnable {
	
	public void run() {
		String value = "";
		for(int j=1; j<10; j++) {
			if(Thread.currentThread().getName().equals("Thread01")) {
				value = Thread.currentThread().getName()+"-------"+j;
			} else {
				value = Thread.currentThread().getName()+"......."+j;
			}
			Test04TheadLocal.tl.set(value);
			System.out.println(Test04TheadLocal.tl.get());
		}
	}
}