package thread.basic;

/**
 * yield 
 *	1. 通知线程调度器：当前线程愿意放弃cpu执行权，把执行机会让给其它线程；
 *	2. 可用于调试：让多线程竟可能交替运行，重现由于竞态条件所引起的bug.
 */
public class Test13ThreadYield {
	
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			
			public void run() {
				for(int i=0; i<10; i++) {
					System.out.println(Thread.currentThread().getName()+" running...");
					Thread.yield(); // 放弃cpu执行权，让其它线程得到执行
				}
			}
		};
		new Thread(r).start();
		new Thread(r).start();
	}
}
