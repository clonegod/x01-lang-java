package clonegod.thread02.threadlocal;

/** 
 *  InheritableThreadLocal - 可与子线程共享的ThreadLocal
 *  
 *  底层原理：线程内部维护了2个ThreadLocalMap变量：
 *   ThreadLocal.ThreadLocalMap threadLocals = null; 			// 当前线程自己使用的ThreadLocalMap
 *   ThreadLocal.ThreadLocalMap inheritableThreadLocals = null; // 可与子线程共享的ThreadLocalMap
 *   
 *  子线程在刚创建时，执行init初始化，会判断父线程的inheritableThreadLocals属性是否为空，
 *  如果不为空，则复制1个父线程的ThreadLocalMap的副本，
 *  因此，可以在子线程中可以获取到父线程中设置的那些本地变量。
 *
 */
public class TestInheritableThreadLocal {
	
	static ThreadLocal<String> threadLocal = new ThreadLocal<>();
	
	static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
	
	public static void main(String[] args) {
		threadLocal.set("init-123");
		inheritableThreadLocal.set("init-abc");
		
		print(threadLocal.get());
		print(inheritableThreadLocal.get());
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				print(threadLocal.get());
				print(inheritableThreadLocal.get()); // 继承了父线程中threaLocals中的变量
			}
		}, "NewThread").start();
	}
	
	static void print(String value) {
		System.out.println(Thread.currentThread().getName() + "\t" + value);
	}
	
}
