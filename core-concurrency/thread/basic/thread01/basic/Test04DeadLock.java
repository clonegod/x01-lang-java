package thread01.basic;

import java.util.Random;

/**
 * 死锁产生的原因：
 * 	锁嵌套---同步中嵌套同步，但是各自使用的锁是不同的；
 * 
 * 如何避免死锁：
 * 	检查程序中的加锁是否存在不同锁的嵌套---如果有，必须修改程序：调整共享资源的访问方式！比如，采用阻塞队列来实现共享资源的访问。
 *
 */
public class Test04DeadLock {
	
	public static final Object lock01 = new Object();
	public static final Object lock02 = new Object();
	
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				lockIt();
			}

			private void lockIt() {
				while(true) {
					if(new Random().nextInt(10) % 2 == 0) {
						synchronized (lock01) {
							System.out.println("if...lock01");
							synchronized (lock02) {
								System.out.println("if...lock02");
							}
						}
					} else {
						synchronized (lock02) {
							System.out.println("else...lock02");
							synchronized (lock01) {
								System.out.println("else...lock01");
							}
						}
					}
				}
			}
		};
		
		new Thread(r).start();
		new Thread(r).start();
	}

}

