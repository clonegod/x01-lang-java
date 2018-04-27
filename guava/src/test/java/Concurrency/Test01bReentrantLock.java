package Concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Test01bReentrantLock {
	
	static final ExecutorService executor = ThreadPoolManager.executor;
	
	/**
	 * 模拟有界队列 
	 */
	static class ReentrantLockSample {
	    private List<String> list = new ArrayList<String>();
	    private static final int MAX_SIZE = 10;

	    private ReentrantLock rLock = new ReentrantLock();
	    private Condition putCond = rLock.newCondition();
	    private Condition removeCond = rLock.newCondition();

	    public void put(String item) throws InterruptedException {
	        rLock.lock();
	        try {
	            while (list.size() == MAX_SIZE) {
	            	System.out.println(Thread.currentThread().getName() + ": " +"queue is full, waiting");
	            	putCond.await();
	            }
	            list.add(item);
	            removeCond.signal();
	        } catch(InterruptedException ex) {
	    		throw ex;
	    	}  finally {
	            rLock.unlock();
	        }
	    }
	    
	    public String remove() throws InterruptedException {
	    	rLock.lock();
	    	try {
	    		while (list.size() == 0) {
	    			System.out.println(Thread.currentThread().getName() + ": " +"queue is empty, waiting");
	    			removeCond.await();
	    		}
	    		String item = list.remove(0);
	    		putCond.signal();
	    		return item;
	    	} catch(InterruptedException ex) {
	    		throw ex;
	    	} finally {
	    		rLock.unlock();
	    	}
	    }
	    
	    public void print(String type) {
	    	rLock.lock();
	    	try {
	    		System.out.println(Thread.currentThread().getName() + ": " +  type + ": " + list);
	    	} finally {
	    		rLock.unlock();
	    	}
	    }
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		final ReentrantLockSample sample = new ReentrantLockSample();
		
		executor.execute(() -> {
			while(true) {
				try {
					sample.put("item");
					sample.print("put");
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		});
		
		executor.execute(() -> {
			while(true) {
				try {
					sample.remove();
					sample.print("remove");
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		});
		
		ThreadPoolManager.shutdownAndAwaitTermination(executor, 5, TimeUnit.SECONDS);
		
	}
	
	
	
}
