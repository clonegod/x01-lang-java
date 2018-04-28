package java7.concurrent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

	private static final int MAX_THREAD = 10;


	public static void main(String[] args) {
		int quorum = 1 + MAX_THREAD / 2; // 超过半数
		CountDownLatch latch = new CountDownLatch(quorum);
		
		Set<Worker> nodes = new HashSet<>();
		try {
			for(int i = 0; i < MAX_THREAD; i++) {
				Worker worker = new Worker("localhost:"+(9000+i), latch);
				nodes.add(worker);
				worker.start();
			}
			latch.await();
			// 等待一半以上的线程初始化完成后，开始发送更新通知
			System.out.println("Send update info to nodes");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	
	static class Worker extends Thread {
		private final String identifier;
		private final CountDownLatch latch;
		
		public Worker(String identifier, CountDownLatch latch) {
			this.identifier = identifier;
			this.latch = latch;
		}

		@Override
		public void run() {
			initialize();
		}

		private void initialize() {
			System.out.println(identifier + ": Node initialize successful");
			latch.countDown();
		}
		
	}
}

