package baeldung.synchronizer.CountDownLatch;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Waiting for a Pool of Threads to Complete
 *
 */
public class CountdownLatchExampleIntegrationTest {
	@Test
	public void whenParallelProcessing_thenMainThreadWillBlockUntilCompletion() throws InterruptedException {
		// Given
		List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
		CountDownLatch countDownLatch = new CountDownLatch(5);
		List<Thread> workers = Stream
				.generate(() -> new Thread(new Worker(outputScraper, countDownLatch)))
				.limit(5)
				.collect(toList());

		// When
		workers.forEach(Thread::start);
		countDownLatch.await(); // Block until workers finish ----
		outputScraper.add("Latch released");

		// Then
		assertThat(outputScraper).containsExactly(
				"Counted down", 
				"Counted down", 
				"Counted down", 
				"Counted down",
				"Counted down", 
				"Latch released");
	}

	@Test
	public void whenFailingToParallelProcess_thenMainThreadShouldTimeout() throws InterruptedException {
		// Given
		List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
		CountDownLatch countDownLatch = new CountDownLatch(5);
		List<Thread> workers = Stream
				.generate(() -> new Thread(new BrokenWorker(outputScraper, countDownLatch)))
				.limit(5)
				.collect(toList());

		// When
		workers.forEach(Thread::start);
		final boolean result = countDownLatch.await(3L, TimeUnit.SECONDS);

		// Then
		assertThat(result).isFalse();
	}

	/**
	 * 使用多个CountDownLatch来协调线程的运行
	 * 	主线程await()		等待其它线程
	 *  主线程countDown()	启动其它线程执行任务
	 * 	主线程await()		等待其它线程结束任务
	 */
	@Test
	public void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime() throws InterruptedException {
		// Given
		List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
		CountDownLatch readyThreadCounter = new CountDownLatch(5);
		CountDownLatch callingThreadBlocker = new CountDownLatch(1);
		CountDownLatch completedThreadCounter = new CountDownLatch(5);
		List<Thread> workers = Stream
				.generate(() -> new Thread(
				new WaitingWorker(outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
				.limit(5)
				.collect(toList());

		// When
		workers.forEach(Thread::start);
		
		readyThreadCounter.await(); // Block until workers start
		outputScraper.add("Workers ready");
		
		callingThreadBlocker.countDown(); // Start workers---触发其它线程开始执行
		
		completedThreadCounter.await(); // Block until workers finish
		outputScraper.add("Workers complete");

		// Then
		assertThat(outputScraper).containsExactly(
				"Workers ready", 
				"Counted down", 
				"Counted down", 
				"Counted down",
				"Counted down", 
				"Counted down", 
				"Workers complete");
	}

}