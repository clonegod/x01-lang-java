package baeldung.synchronizer.Semaphore;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SemaphoresManualTest {

    // ========= login queue ======
	/**
	 * To test our login queue, 
	 * we will first try to reach the limit and check if the next login attempt will be blocked:
	 */
    @Test
    public void givenLoginQueue_whenReachLimit_thenBlocked() {
        final int slots = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final LoginQueueUsingSemaphore loginQueue = new LoginQueueUsingSemaphore(slots);
        IntStream.range(0, slots)
          .forEach(user -> executorService.execute(loginQueue::tryLogin));
        executorService.shutdown();

        assertEquals(0, loginQueue.availableSlots()); // slots已经被用完
        assertFalse(loginQueue.tryLogin()); // 无可用slots
    }
    
    /**
     * Next, we will see if any slots are available after a logout:
     */
    @Test
    public void givenLoginQueue_whenLogout_thenSlotsAvailable() {
        final int slots = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final LoginQueueUsingSemaphore loginQueue = new LoginQueueUsingSemaphore(slots);
        IntStream.range(0, slots)
          .forEach(user -> executorService.execute(loginQueue::tryLogin));
        executorService.shutdown();

        assertEquals(0, loginQueue.availableSlots());
        loginQueue.logout(); // 注销后，释放出1个slot
        assertTrue(loginQueue.availableSlots() > 0);
        assertTrue(loginQueue.tryLogin());
    }

    // ========= delay queue =======
    /**
     * When we use a delay queue with one second as time period 
     * and after using all the slots within one second, 
     * none should be available --- 1s内所有slots已经用完，再次获取时将无可用slot可提供
     */
    @Test
    public void givenDelayQueue_whenReachLimit_thenBlocked() {
        final int slots = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final DelayQueueUsingTimedSemaphore delayQueue = new DelayQueueUsingTimedSemaphore(1, slots);
        IntStream.range(0, slots)
          .forEach(user -> executorService.execute(delayQueue::tryAdd));
        executorService.shutdown();

        assertEquals(0, delayQueue.availableSlots());
        assertFalse(delayQueue.tryAdd()); // 未超时，将获取permit失败
    }

    /**
     * But after sleeping for some time, the semaphore should reset and release the permits:
     */
    @Test
    public void givenDelayQueue_whenTimePass_thenSlotsAvailable() throws InterruptedException {
        final int slots = 50;
        final ExecutorService executorService = Executors.newFixedThreadPool(slots);
        final DelayQueueUsingTimedSemaphore delayQueue = new DelayQueueUsingTimedSemaphore(1, slots);
        IntStream.range(0, slots)
          .forEach(user -> executorService.execute(delayQueue::tryAdd));
        executorService.shutdown();

        assertEquals(0, delayQueue.availableSlots());
        
        Thread.sleep(1000); // 1s后超时，TimedSemaphore将释放所有的permit
        
        assertTrue(delayQueue.availableSlots() > 0);
        assertTrue(delayQueue.tryAdd());
    }

    // ========== mutex ========

    /**
     * When a lot of threads try to access the counter at once, they’ll simply be blocked in a queue.
     */
    @Test
    public void whenMutexAndMultipleThreads_thenBlocked() throws InterruptedException {
        final int count = 5;
        final ExecutorService executorService = Executors.newFixedThreadPool(count);
        final CounterUsingSemaphoreAsMutex counter = new CounterUsingSemaphoreAsMutex();
        IntStream.range(0, count)
          .forEach(user -> executorService.execute(() -> {
              try {
                  counter.increase();
              } catch (final InterruptedException e) {
                  e.printStackTrace();
              }
          }));
        executorService.shutdown();

        assertTrue(counter.hasQueuedThreads());
    }

    /**
     * When we wait, all threads will access the counter and no threads left in the queue
     */
    @Test
    public void givenMutexAndMultipleThreads_ThenDelay_thenCorrectCount() throws InterruptedException {
        final int count = 5;
        final ExecutorService executorService = Executors.newFixedThreadPool(count);
        
        final CounterUsingSemaphoreAsMutex counter = new CounterUsingSemaphoreAsMutex();
        IntStream.range(0, count)
          .forEach(user -> executorService.execute(() -> {
              try {
                  counter.increase();
              } catch (final InterruptedException e) {
                  e.printStackTrace();
              }
          }));
        
        executorService.shutdown();
        assertTrue(counter.hasQueuedThreads());
        Thread.sleep(5000);
        assertFalse(counter.hasQueuedThreads());
        assertEquals(count, counter.getCount());
    }
}
