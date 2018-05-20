package printABC;

import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore信号量来协调线程间的交替运行
 *
 */
public class PrintABC_Semaphore {

	public static void main(String[] args) {
		Semaphore aSem = new Semaphore(1); // 初始信号设置为true
		Semaphore bSem = new Semaphore(0); // 初始信号设置为false
		Semaphore cSem = new Semaphore(0); // 初始信号设置为false

		T a = new T("A", aSem, bSem);
		T b = new T("B", bSem, cSem);
		T c = new T("C", cSem, aSem);

		a.start();
		b.start();
		c.start();
	}
}

class T extends Thread {
	String name;
	Semaphore me;
	Semaphore next;

	public T(String name, Semaphore me, Semaphore next) {
		this.name = name;
		this.me = me;
		this.next = next;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				// Acquires a permit, if one is available and returns immediately, reducing the number of available permits by one. 
				me.acquire();
				System.out.println(this.getName() + "\t" + name);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			} finally {
				// Releases a permit, increasing the number of available permits by one. 
				next.release();
			}
			
		}
	}
}