package baeldung.synchronization.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

import static java.lang.Thread.sleep;

/**
 * 使用 StampedLock
 * 1. 获取锁成功后，会返回一个stamp，在是否锁时使用。
 * 2. 可以乐观锁，然后得到结果，再判断此时的stamp是否有效，如果有效，则结果为最新的，否则需要重新获取结果。
 * 
 * @author clonegod@163.com
 *
 */
public class StampedLockResource {
	private Logger logger = LoggerFactory.getLogger(StampedLockResource.class);
	
	// 共享资源-需要被锁保护
    private Map<String, String> map = new HashMap<>();

    private final StampedLock lock = new StampedLock();

    /**
     * 写锁
     */
    public void put(String key, String value) throws InterruptedException {
        long stamp = lock.writeLock();

        try {
            logger.info(Thread.currentThread().getName() + " acquired the write lock with stamp " + stamp);
            map.put(key, value);
        } finally {
            lock.unlockWrite(stamp);
            logger.info(Thread.currentThread().getName() + " unlocked the write lock with stamp " + stamp);
        }
    }

    /**
     * 读锁
     */
    public String get(String key) throws InterruptedException {
        long stamp = lock.readLock();
        logger.info(Thread.currentThread().getName() + " acquired the read lock with stamp " + stamp);
        try {
            sleep(5000);
            return map.get(key);

        } finally {
            lock.unlockRead(stamp);
            logger.info(Thread.currentThread().getName() + " unlocked the read lock with stamp " + stamp);

        }

    }

    /**
     * 使用乐观读锁
     */
    private String readWithOptimisticLock(String key) throws InterruptedException {
        // upgrade to optimistic read lock:
    	long stamp = lock.tryOptimisticRead();
        String value = map.get(key);
        
        // 如果邮戳失效，才使用readLock()获取锁
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                sleep(5000);
                return map.get(key);

            } finally {
                lock.unlock(stamp);
                logger.info(Thread.currentThread().getName() + " unlocked the read lock with stamp " + stamp);

            }
        }
        return value;
    }

    public static void main(String[] args) {
        final int threadCount = 4;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        StampedLockResource object = new StampedLockResource();

        Runnable writeTask = () -> {

            try {
                object.put("key1", "value1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable readTask = () -> {

            try {
                object.get("key1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable readOptimisticTask = () -> {

            try {
                object.readWithOptimisticLock("key1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        service.submit(writeTask);
        service.submit(writeTask);
        service.submit(readTask);
        service.submit(readOptimisticTask);

        service.shutdown();

    }

}
