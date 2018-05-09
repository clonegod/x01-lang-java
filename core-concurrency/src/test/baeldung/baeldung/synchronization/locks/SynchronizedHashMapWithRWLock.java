package baeldung.synchronization.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

/**
 * Let’s see how to make use of the ReadWriteLock:
 * 	读操作-使用readLock
 * 	写操作-使用writeLock
 * 
 * @author clonegod@163.com
 *
 */
public class SynchronizedHashMapWithRWLock {
	private Logger logger = LoggerFactory.getLogger(SynchronizedHashMapWithRWLock.class);
	
	// 非线程安全的HashMap，使用Lock进行保护
    private static Map<String, String> syncHashMapByLock = new HashMap<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    /**
     * 写操作，使用writeLock.lock();
     */
    public void put(String key, String value) throws InterruptedException {

        try {
            writeLock.lock();
            logger.info(Thread.currentThread().getName() + " writing");
            syncHashMapByLock.put(key, value);
            sleep(1000);
        } catch(Exception e) {
        	e.printStackTrace();
        }finally {
            writeLock.unlock();
        }

    }

    /**
     * 读操作，使用readLock.lock();
     */
    public String get(String key) {
        try {
        	readLock.lock();
            logger.info(Thread.currentThread().getName() + " reading");
            return syncHashMapByLock.get(key);
        } finally {
            readLock.unlock();
        }
    }
    
    /**
     * 写操作，使用writeLock.lock();
     */
    public String remove(String key) {
        try {
            writeLock.lock();
            return syncHashMapByLock.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 读操作，使用readLock.lock();
     */
    public boolean containsKey(String key) {
        try {
            readLock.lock();
            return syncHashMapByLock.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }
    
    /**
     * 
     * @return true-获取锁成功，false-获取失败
     */
    boolean isReadLockAvailable() {
        return readLock.tryLock();
    }
    

    public static void main(String[] args) throws InterruptedException {

        final int threadCount = 3;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        SynchronizedHashMapWithRWLock object = new SynchronizedHashMapWithRWLock();

        service.execute(new Thread(new Writer(object), "Writer"));
        service.execute(new Thread(new Reader(object), "Reader1"));
        service.execute(new Thread(new Reader(object), "Reader2"));

        service.shutdown();
    }

    private static class Reader implements Runnable {

        SynchronizedHashMapWithRWLock object;

        Reader(SynchronizedHashMapWithRWLock object) {
            this.object = object;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                object.get("key" + i);
            }
        }
    }

    private static class Writer implements Runnable {

        SynchronizedHashMapWithRWLock object;

        public Writer(SynchronizedHashMapWithRWLock object) {
            this.object = object;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    object.put("key" + i, "value" + i);
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
