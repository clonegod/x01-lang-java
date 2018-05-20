package thread01.basic;

/**
 * JVM启动后，至少会启动2个线程，1个是主线程，另一个是进行垃圾清理的线程！
 * 
 * 直接调用Thread的run方法和通过start()启动线程有什么区别？
 * 	直接调用run方法，不会启动新的线程，执行run()的线程为当前主线程；
 *  调用start()，则启动新的线程，与主线程竞争CPU资源；
 *
 */
public class Test01Thread {
	public static void main(String[] args) {
		
		//new MyThread("MyThread").run();
		new MyThread("MyThread").start();
		
		for(int i=0;i<50;i++)
			System.out.println(Thread.currentThread().getName()+"------"+i);
		
	}
}

class MyThread extends Thread {
	
	public MyThread(String name) {
		super(name);
	}

	public void run() {
		for(int i=0;i<50;i++)
			System.out.println(Thread.currentThread().getName());
	}
}