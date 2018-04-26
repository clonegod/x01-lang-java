package clonegod.zookeeper.serverlist;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class AppServerTest {
	
	int nThreads = 5;
	ExecutorService executor = Executors.newFixedThreadPool(nThreads);
	
	@Test
	public void test() throws InterruptedException {
		for(int i = 0; i < nThreads; i++) {
			executor.submit(new AppServer());
		}
		executor.awaitTermination(10, TimeUnit.SECONDS);
		executor.shutdown();
	}
	
}
