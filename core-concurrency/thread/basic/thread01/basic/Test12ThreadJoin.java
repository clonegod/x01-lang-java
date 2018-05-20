package thread01.basic;

/**
 * join
 * 
 * 当A线程执行到B线程的join()时，A线程将会等待B线程运行结束后，A线程才会接着运行
 * join()可以用来确保某个线程运算完成后，主程序再继续向下执行。
 * 
 * 如果某个线程调用了join()，但是进入了阻塞状态-wait()，那主线程将一直等待下去。
 * 解决办法：
 * 	新开一个线程，对阻塞线程调用interrupt()进行中断！
 *
 */
public class Test12ThreadJoin {
	
	public static void main(String[] args) throws InterruptedException {
		
		// 线程A打印50条语句之后，进入等待状态
		Runnable r1 = new Runnable() {
			public synchronized void run() {
				for(int i=0; i<50; i++)
					System.out.println(Thread.currentThread().getName()+" running...");
				try {
					this.wait();
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName()+" 被中断...");
				}
			}
		};
		
		// 线程B打印50条语句
		Runnable r2 = new Runnable() {
			public void run() {
				for(int i=0; i<50; i++)
					System.out.println(Thread.currentThread().getName()+" running...");
			}
		};
		
		final Thread t1 = new Thread(r1, "线程A-");
		Thread t2 = new Thread(r2, "线程B-");
		
		// 线程C专门用来中断线程A的阻塞状态
		Runnable r3 = new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
					t1.interrupt(); // 3秒后中断线程t1，解除其阻塞状态。这样main线程才能继续运行
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread t3 = new Thread(r3, "线程C-");
		t3.start();
		
		t1.start();
		t1.join(); // 主线程看到t1.join后，不会继续往下执行。直到t1线程运行结束后，主线程才继续向下运行。
		
		// 接下来，就是t2线程与主线程争抢cpu资源了
		t2.start();
		
		// 主线程
		for(int i=0; i<50; i++)
			System.out.println(Thread.currentThread().getName()+" running...");
	}
	
}
