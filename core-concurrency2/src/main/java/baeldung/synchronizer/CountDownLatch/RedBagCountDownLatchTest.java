package baeldung.synchronizer.CountDownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RedBagCountDownLatchTest {
	
	private static final int count = 5;

	public static void main(String[] args) {
		CountDownLatch cdl = new CountDownLatch(count);
		
		ExecutorService executor = Executors.newFixedThreadPool(count);
		
		for(int i=0; i<count; i++) {
			executor.execute(new RegBagTask(cdl));
		}
		
		try {
			cdl.await(); // wait until cdl inner count = 0
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("红包抢完了");
	}
	
}

class RedBag {
	double amount;
	
}

class RegBagTask implements Runnable {
	
	CountDownLatch cdl;
	
	public RegBagTask(CountDownLatch cdl) {
		this.cdl = cdl;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName());
		
		try {
			Thread.sleep(new Random().nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			cdl.countDown(); // minus 1
		}
		
	}
	
}
