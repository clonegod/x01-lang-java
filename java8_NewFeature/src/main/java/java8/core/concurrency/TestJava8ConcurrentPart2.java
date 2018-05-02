package java8.core.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * 线程同步，锁
 * 
 * Synchronization and Locks 
 * 	对共享变量或资源的独占访问控制
 *		synchronized keyword	隐式加锁，不需要手动释放锁
 *		locks					显示通过代码进行加锁，需要手动释放锁
 *
 * 	对应用程序的某些部分进行并发访问控制
 *		semaphores
 *
 */
public class TestJava8ConcurrentPart2 {
	
	int count = 0;

	void increment() {
	    count = count + 1;
	}
	
	/**
	 * synchronized
	 * 	 monitor lock 
	 * 	 reentrant
	 */
	synchronized void incrementSync() {
		count = count + 1;
	}

	@Test
	public void testNotSynchronized() {
		ExecutorService executor = Executors.newFixedThreadPool(2);

		IntStream.range(0, 10000)
		    .forEach(i -> executor.submit(this::increment));

		ConcurrentUtils.stop(executor);

		System.out.println(count);  // 9965
	}
	
	@Test
	public void testSynchronized() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		IntStream.range(0, 10000)
		.forEach(i -> executor.submit(this::incrementSync));
		
		ConcurrentUtils.stop(executor);
		
		System.out.println(count);  // 9965
	}
	
	//--------------------------------------
	
	/**
	 * ReentrantLock - 在实现synchronized相同功能的前提下，增加了新的功能，比如可超时等待锁，可中断等
	 * 
	 *  a mutual exclusion lock as the implicit monitors accessed via the synchronized keyword but with extended capabilities.
	 *  	reentrant characteristics
	 *  	tryLock
	 *  	lockInterruptibly
	 */
	ReentrantLock lock = new ReentrantLock();

	void incrementWithLock() {
	    lock.lock();
	    try {
	        count++;
	    } finally {
	    	// It's important to wrap your code into a try/finally block to ensure unlocking in case of exceptions
	        lock.unlock();
	    }
	}
	
	@Test
	public void testReentrantLock() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		ReentrantLock lock = new ReentrantLock();

		executor.submit(() -> {
		    lock.lock();
		    try {
		        ConcurrentUtils.sleep(1);
		    } finally {
		        lock.unlock();
		    }
		});

		executor.submit(() -> {
		    System.out.println("Locked: " + lock.isLocked());
		    System.out.println("Held by me: " + lock.isHeldByCurrentThread());
		    boolean locked = lock.tryLock();
		    System.out.println("Lock acquired: " + locked);
		});

		ConcurrentUtils.stop(executor);
	}
	
	/**
	 * ReadWriteLock
	 * 		适用场景： reads are more frequent than writes， can improve performance and throughput
	 * 
	 * 	the read-lock can be held simultaneously by multiple threads as long as no threads hold the write-lock.
	 *  usually safe to read mutable variables concurrently as long as nobody is writing to this variable. 
	 *  
	 */
	@Test
	public void testReadWriteLock() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		Map<String, String> map = new HashMap<>();
		
		ReadWriteLock lock = new ReentrantReadWriteLock();

		// first acquires a write-lock in order to put a new value to the map 
		executor.submit(() -> {
		    lock.writeLock().lock(); // 获取写锁
		    try {
		    	ConcurrentUtils.sleep(1);
		        map.put("foo", "bar");
		    } finally {
		        lock.writeLock().unlock();
		    }
		});
		
		
		Runnable readTask = () -> {
		    lock.readLock().lock(); // read task wait until the write task has finished
		    // read-locks can safely be acquired concurrently as long as no write-lock is held by another thread.
		    try {
		        System.out.println(map.get("foo"));
		        ConcurrentUtils.sleep(1);
		    } finally {
		        lock.readLock().unlock();
		    }
		};

		executor.submit(readTask);
		executor.submit(readTask);

		ConcurrentUtils.stop(executor);
	}
	
	
	/**
	 * StampedLock
	 * 		support read and write locks just as ReadWriteLock  支持读写锁
	 * 		StampedLock return a stamp represented by a long value, you can use it to check lock is still valid or to release lock 锁的有效判断，锁的释放
	 * 		stamped locks don't implement reentrant characteristics, so you must careful about dead lock  不支持锁重入，注意发生死锁
	 * 		optimistic locking 支持乐观锁
	 */
	@Test
	public void testStampedLock() {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Map<String, String> map = new HashMap<>();
		
		StampedLock lock = new StampedLock();

		executor.submit(() -> {
		    long stamp = lock.writeLock(); // a stamp which is later used for unlocking within the finally block.
		    try {
		    	ConcurrentUtils.sleep(1);
		        map.put("foo", "bar");
		    } finally {
		        lock.unlockWrite(stamp);
		    }
		});

		Runnable readTask = () -> {
		    long stamp = lock.readLock(); // a stamp which is later used for unlocking within the finally block.
		    try {
		        System.out.println(map.get("foo"));
		        ConcurrentUtils.sleep(1);
		    } finally {
		        lock.unlockRead(stamp);
		    }
		};

		executor.submit(readTask);
		executor.submit(readTask);

		ConcurrentUtils.stop(executor);
		
	}
	
	/**
	 * StampedLock - tryOptimisticRead
	 * 	 In contrast to normal read locks, an optimistic lock doesn't prevent other threads to obtain a write lock instantaneously
	 * 	 乐观锁，比较锁。即使获取到乐观锁（读操作），在释放前，申请写操作的线程仍可能获取锁成功。
	 *  因此，对于读操作的乐观锁，每次都要判断锁是否还有效。因为随时可能有其它线程获取到写锁，从而导致读锁状态失效--数据版本号已过期。
	 *  
	 *  好处：在存在读操作的情况下，进一步提高写线程获取锁的成功率。
	 */
	@Test
	public void testStampedOptimisticLocking() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		StampedLock lock = new StampedLock();

		executor.submit(() -> {
		    long stamp = lock.tryOptimisticRead(); // 获取乐观锁
		    try {
		        System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
		        ConcurrentUtils.sleep(1);
		        System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
		        ConcurrentUtils.sleep(2);
		        System.out.println("Optimistic Lock Valid: " + lock.validate(stamp));
		    } finally {
		        lock.unlock(stamp);
		    }
		});

		executor.submit(() -> {
		    long stamp = lock.writeLock();
		    try {
		        System.out.println("Write Lock acquired");
		        ConcurrentUtils.sleep(2);
		    } finally {
		        lock.unlock(stamp);
		        System.out.println("Write done");
		    }
		});

		ConcurrentUtils.stop(executor);
	}
	
	/**
	 * StampedLock
	 * 		tryConvertToWriteLock - 读锁转化为写锁（）
	 */
	@Test
	public void testConvertToWriteLock() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		StampedLock lock = new StampedLock();

		executor.submit(() -> {
		    long stamp = lock.readLock();
		    try {
		    	// if the current value is zero we want to assign a new value of 23
		        if (count == 0) {
		            stamp = lock.tryConvertToWriteLock(stamp);
		            // tryConvertToWriteLock() doesn't block but may return a zero stamp indicating that no write lock is currently available
		            if (stamp == 0L) {
		                System.out.println("Could not convert to write lock");
		                // call writeLock() to block the current thread until a write lock is available.
		                stamp = lock.writeLock();
		            }
		            count = 23;
		        }
		        System.out.println(count);
		    } finally {
		        lock.unlock(stamp);
		    }
		});

		ConcurrentUtils.stop(executor);
	}
	
	
	// ========================================================

	/**
	 * Semaphores
	 * 	对系统的某个部分的并发量进行控制
	 */
	@Test
	public void testSemaphores() {
		ExecutorService executor = Executors.newFixedThreadPool(10);

		// use a semaphore of size 5, thus limiting concurrent access to 5
		Semaphore semaphore = new Semaphore(5);

		Runnable longRunningTask = () -> {
		    boolean permit = false;
		    try {
		        permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
		        if (permit) {
		        	// limit access to a long running task
		            System.out.println("Semaphore acquired");
		            ConcurrentUtils.sleep(5);
		        } else {
		            System.out.println("Could not acquire semaphore");
		        }
		    } catch (InterruptedException e) {
		        throw new IllegalStateException(e);
		    } finally {
		        if (permit) {
		            semaphore.release();
		        }
		    }
		};

		IntStream.range(0, 10)
		    .forEach(i -> executor.submit(longRunningTask));

		ConcurrentUtils.stop(executor);
		
	}
	
	
	
	
	
	
	
}
