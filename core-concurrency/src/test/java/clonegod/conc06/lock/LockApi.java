package clonegod.conc06.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LockApi {
	
	public static void main(String[] args) {
		boolean fair = false;
		ReentrantLock lock = new ReentrantLock(fair);
		
		try {
			lock.lock(); // 阻塞等待
			boolean tryLock = lock.tryLock(); // 尝试获取锁，立即返回是否成功获取到锁
			boolean tryLockWithTimeout = lock.tryLock(10, TimeUnit.SECONDS); // 尝试获取锁，超时等待，返回是否成功获取到锁
			lock.lockInterruptibly(); // 在阻塞等待锁的过程中，可以对中断进行响应（synchronized阻塞等待是无法响应中断的）
			
			boolean isFair = lock.isFair(); // 是否为公平锁。默认非公平。效率更高。
			if(isFair) {
			}
			
			boolean locked = lock.isLocked(); // 查询是否成功获取到锁
			if(locked) {
				lock.getHoldCount(); // 返回当前线程持有锁的个数
				
				if(lock.hasQueuedThreads()) { // 查询是否由线程正在等待获取该锁
					lock.getQueueLength(); // 返回正在等待此锁的线程个数
				}
				
//				lock.hasQueuedThread(thread); // 查询指定线程是否正在等待该锁
				
				// 针对某个具体condition
//				lock.getWaitQueueLength(condition);
//				lock.hasWaiters(condition);
//				lock.getWaitQueueLength(condition);
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
		
	}
}
