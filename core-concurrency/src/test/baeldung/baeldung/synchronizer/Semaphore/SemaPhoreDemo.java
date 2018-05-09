package baeldung.synchronizer.Semaphore;

import java.util.concurrent.Semaphore;

/**
tryAcquire() 
	– return true if a permit is available immediately and acquire it otherwise return false
	
acquire() 
	- acquires a permit and blocking until one is available
	
release() 
	– release a permit
	
availablePermits() 
	– return number of current permits available
 *
 */
public class SemaPhoreDemo {

	static Semaphore semaphore = new Semaphore(10);
	
	public void execute() throws InterruptedException {

		System.out.println("Available permit : " + semaphore.availablePermits());
		System.out.println("Number of threads waiting to acquire: " + semaphore.getQueueLength());
		
		// tryAcquire 以非阻塞方式尝试获取一个permit，返回true，表示已经获取成功。
		if (semaphore.tryAcquire()) {
			System.out.println("tryAcquire success! Available permit : " + semaphore.availablePermits());
			
			semaphore.acquire();
			System.out.println("Get another permit! Available permit : " + semaphore.availablePermits());

			// perform some critical operations
			
			semaphore.release();
			System.out.println("After release one permit, Available permit : " + semaphore.availablePermits());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		SemaPhoreDemo demo = new SemaPhoreDemo();
		demo.execute();
	}
}
