package baeldung.synchronization.locks;

/**
 * Differences between Lock and Synchronized block
1. synchronized block 的锁范围只能在1个方法内
 	Lock API的 lock() and unlock() 可以跨越多个方法
2. synchronized block 不支持公平策略，只要所被释放，任何线程都有机会获取到锁资源
 	Lock API 提供了fairness属性，可以让等待时间最长的线程优先获取到锁资源
3. 1个线程如果没有获取到锁资源，将发生阻塞式等待synchronized block.
 	Lock API 提供了tryLock()，tryLock(timeout)线程可以尝试获取锁资源，或者在指定超时范围内等待锁资源，减少线程阻塞时间
4. 1个线程处于waiting阻塞状态等待锁资源，将不可被中断
 	 Lock API 提供了lockInterruptibly()，让一个正在等待锁资源的线程可以被中断
 */

/**
Lock API
	void lock()					
		获取锁，如果锁被暂用，则线程阻塞直到锁被释放
	void lockInterruptibly()	
		与lock()功能类似，但是可以被中断
	boolean tryLock() 			
		lock()的非阻塞版本，尝试获取锁并立即返回，获取成功返回true，失败返回false
	boolean tryLock(long timeout, TimeUnit timeUnit)	
		与tryLock()类似，在超时时间内尝试获取锁，超时后返回false
	void unlock()				
		释放锁。正确释放锁：try...finally {lock.unlock();}
 */


/**
ReadWriteLock 
 |- ReentrantReadWriteLock 
	读锁	Lock readLock() –  如果没有线程暂用写锁，则多线程可以共享读锁并发访问资源
	写锁	Lock writeLock() – 如果没有线程正在读或写，则最多有一个线程可以获取到写锁资源

StampedLock - introduced in Java 8
	1. supports both read and write locks. 支持读写锁
	2. lock acquisition methods returns a stamp that is used to release a lock 
		or to check if the lock is still valid.
		获取锁成功后返回一个邮戳
			用来释放锁时使用
			乐观获取锁，之后用该邮戳判断锁是否仍然有效。如果失效，则从新获取锁。
	
 */


/**
Conditions
	wait for some condition to occur while executing the critical section.

线程已获取锁成功，但是不满足执行条件，需要等待某个条件满足后才能继续执行：
	比如，已经获取到读锁，但是队列为空，因此需要等待队列填充元素

Traditionally Java provides wait(), notify() and notifyAll() methods for thread intercommunication. 
	wait(), notify() and notifyAll()

Conditions have similar mechanisms, but in addition, we can specify multiple conditions:
	await(), signal() and signalAll()
	
	multiple conditions:
	Condition stackEmptyCondition = lock.newCondition();
    Condition stackFullCondition = lock.newCondition();

 */
