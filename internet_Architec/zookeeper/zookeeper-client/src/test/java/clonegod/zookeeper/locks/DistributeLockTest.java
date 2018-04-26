package clonegod.zookeeper.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class DistributeLockTest {
	
	int nThreads = 5;
	ExecutorService executor = Executors.newFixedThreadPool(nThreads);
	
	@Test
	public void test() throws InterruptedException {
		for(int i = 0; i < nThreads; i++) {
			executor.submit(new DistributeLock());
		}
		executor.awaitTermination(20, TimeUnit.SECONDS);
		executor.shutdown();
	}
	
}
