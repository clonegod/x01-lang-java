package concurrent.practice;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一道比较经典的面试题，题目要求如下：
 * 	建立三个线程，A线程打印10次A，B线程打印10次B,C线程打印10次C，要求线程同时运行，交替打印10次ABC。
 * 	
 * 这个问题用Object的wait()，notify()就可以很方便的解决。 
 *
 */
public class PrintABC_WaitNotify {
	
	public static void main(String[] args) {
		AtomicInteger synObj = new AtomicInteger(0);
		Thread t1 = new Thread(new PrintThread(10, synObj, "A", 0), "Thread-A:flag=0");
		Thread t2 = new Thread(new PrintThread(10, synObj, "B", 1), "Thread-B:flag=1");
		Thread t3 = new Thread(new PrintThread(10, synObj, "C", 2), "Thread-C:flag=2");
		t1.start();
		t2.start();
		t3.start();
	}
	
	static class PrintThread implements Runnable {
		private AtomicInteger synObj;
		private int count;
		String string;
		private int flag;
		private int total;
		
		public PrintThread(int count, AtomicInteger synObj, String string, int flag) {
			this.synObj = synObj;
			this.count = count;
			this.string = string;
			this.flag = flag;
		}

		@Override
		public void run() {
			while(true) {
				synchronized (synObj) {
					System.out.println(Thread.currentThread().getName() + ":synObj=" + synObj.toString());
					if(synObj.intValue() % 3 == flag) { // 当且仅当模数与线程标记一致时，当前线程才满足打印条件
						System.out.println(string);
						synObj.incrementAndGet(); // 切换到下一个锁上，锁对象内部的值在不断的变化：1,2,3,4,5,6...
						synObj.notifyAll();
						if(++total == count) {
							break;
						}
					} else {
						try {
							System.out.println(Thread.currentThread().getName() + ":wait");
							synObj.wait();
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt(); // reset the interrupt status
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}
	
}

