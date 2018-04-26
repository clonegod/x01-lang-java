package baeldung.synchronization.locks;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
/**
 * 读锁-写锁	互斥
 * 读锁-读锁	共享
 *
 */
public class SynchronizedHashMapWithRWLockManualTest {

	/**
	 * 写锁被暂用时，获取读锁将失败
	 */
    @Test
    public void whenWriting_ThenNoReading() throws InterruptedException {
        SynchronizedHashMapWithRWLock object = new SynchronizedHashMapWithRWLock();
        final int threadCount = 3;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        
        executeWriterThreads(object, threadCount, service);

        Thread.sleep(100);
        
        // 100ms后，writeLock已被锁定，因此获取readLock返回false
        assertEquals(object.isReadLockAvailable(), false);

        service.awaitTermination(3, TimeUnit.SECONDS);
        service.shutdown();
    }

    /**
     * 多线程可共享读锁
     */
    @Test
    public void whenReading_ThenMultipleReadingAllowed() {
        SynchronizedHashMapWithRWLock object = new SynchronizedHashMapWithRWLock();
        final int threadCount = 5;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);

        executeReaderThreads(object, threadCount, service);

        assertEquals(object.isReadLockAvailable(), true);

        service.shutdown();
    }

    private void executeWriterThreads(SynchronizedHashMapWithRWLock object, int threadCount, ExecutorService service) {
        for (int i = 0; i < threadCount; i++) {
            service.execute(() -> {
                try {
                	System.out.println(Thread.currentThread().getName() + " starting...");
                    object.put("key" + threadCount, "value" + threadCount);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void executeReaderThreads(SynchronizedHashMapWithRWLock object, int threadCount, ExecutorService service) {
        for (int i = 0; i < threadCount; i++)
            service.execute(() -> object.get("key" + threadCount));
    }

}
