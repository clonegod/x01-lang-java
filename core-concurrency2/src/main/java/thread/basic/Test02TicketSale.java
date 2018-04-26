package thread.basic;

/**
 * 使用Runnable的好处：
 * 1. 避免了通过继承来实现线程，通过接口来扩张类的功能更加通用；
 * 2. 共享资源可以直接封装到Runnable子类中，供多线程使用；
 *
 * 同步：
 * 1. 前提：两个或两个以上的线程操作共享资源，才有必须进行加锁同步；多线程使用的必须是同一个锁！
 * 2. 好处：解决了多线程访问共享资源的安全问题！
 * 3. 弊端：加锁后，线程需要先判断锁，耗费资源！
 */
public class Test02TicketSale {
	
	public static void main(String[] args) {
		TicketWindow tw = new TicketWindow();
		
		// 多个线程操作同一个Runnable
		Thread thread1 = new Thread(tw, "SaleTicketThread-01");
		Thread thread2 = new Thread(tw, "SaleTicketThread-02");
		Thread thread3 = new Thread(tw, "SaleTicketThread-03");
		
		thread1.start();
		thread2.start();
		thread3.start();
	}
	
}

class TicketWindow implements Runnable {
	
	// Runnable内部封装共享资源
	private int ticketCount = 100;
	
	public void run() {
		while(true) {
			// 对共享资源的操作进行加锁
			// 读或写都需要在加锁环境下才能保证数据准确
			synchronized (this/*TicketWindow.class*/) { //确保多线程的锁是唯一的
				if(ticketCount <= 0) {
					return;
				}
				System.out.println(Thread.currentThread().getName()+"---"+(ticketCount--));
			}
		}
	}
	
	
}