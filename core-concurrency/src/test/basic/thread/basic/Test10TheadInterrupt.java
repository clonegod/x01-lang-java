package thread.basic;

public class Test10TheadInterrupt {
	
	public static void main(String[] args) {
		
		InterruptTask task = new InterruptTask();
		
		Thread t1 = new Thread(task);
		Thread t2 = new Thread(task);
		
		t1.start();
		t2.start();
		
		int num = 0;
		while(true) {
			if(++num == 50) {
				t1.interrupt();
				t2.interrupt();
				break;
			}
			System.out.println("main num "+num);
		}
		
		System.out.println("main over!");
	}
	
}


class InterruptTask implements Runnable {
	boolean flag = true;
	public synchronized void run() {
		while(flag) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				changeFlag(false); // 更改循环标记为false
				Thread.currentThread().interrupt(); // 重新设置线程的中断状态为true
				System.out.println(Thread.currentThread().getName()+" InterruptedException. 中断状态：" + Thread.currentThread().isInterrupted());
			}
			System.out.println(Thread.currentThread().getName()+" 被中断，恢复到运行状态");
		}
		// 线程从while循环中跳出后，由于被设置了中断状态，调用sleep()将抛InterruptedException
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			System.err.println(Thread.currentThread().getName()+"的中断状态为true，调用sleep,wait,join方法都会抛出异常");
		}
		
	}
	
	public void changeFlag(boolean flag) {
		this.flag = flag;
	}
}