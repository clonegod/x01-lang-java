package baeldung.synchronization.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

/**
 * use ReenrtantLock for synchronization - 与synchronized功能相似的锁机制 :
 *	2个线程并发获取锁：
 *		一个线程使用lock.lock()
 *		一个线程使用带超时时间的lock.tryLock(n, TimeUnit.SECONDS)
 */
public class SharedObjectWithLock {

    private static final Logger LOG = LoggerFactory.getLogger(SharedObjectWithLock.class);

    private ReentrantLock lock = new ReentrantLock(true);

    private int counter = 0;

    void perform() {

        lock.lock();
        LOG.info("Thread - " + Thread.currentThread().getName() + " acquired the lock");
        try {
            LOG.info("Thread - " + Thread.currentThread().getName() + " processing");
            counter++;
        } catch (Exception exception) {
            LOG.error(" Interrupted Exception ", exception);
        } finally {
        	LOG.info("Thread - " + Thread.currentThread().getName() + " released the lock");
            lock.unlock();
        }
    }

    private void performTryLock() {

        LOG.info("Thread - " + Thread.currentThread().getName() + " attempting to acquire the lock");
        try {
            boolean isLockAcquired = lock.tryLock(2, TimeUnit.SECONDS);
            if (isLockAcquired) {
                try {
                    LOG.info("Thread - " + Thread.currentThread().getName() + " acquired the lock");

                    LOG.info("Thread - " + Thread.currentThread().getName() + " processing");
                    sleep(1000);
                } finally {
                	LOG.info("Thread - " + Thread.currentThread().getName() + " released the lock");
                    lock.unlock();
                }
            } else {
            	LOG.info("Thread - " + Thread.currentThread().getName() + " could not acquire the lock");
            }
        } catch (InterruptedException exception) {
            LOG.error(" Interrupted Exception ", exception);
        }
    }

    public ReentrantLock getLock() {
        return lock;
    }

    boolean isLocked() {
        return lock.isLocked();
    }

    boolean hasQueuedThreads() {
        return lock.hasQueuedThreads();
    }

    int getCounter() {
        return counter;
    }

    public static void main(String[] args) {

        final int threadCount = 2;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final SharedObjectWithLock object = new SharedObjectWithLock();

        service.execute(object::perform);
        service.execute(object::performTryLock);

        service.shutdown();

    }

}
