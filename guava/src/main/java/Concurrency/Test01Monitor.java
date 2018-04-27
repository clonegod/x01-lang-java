package Concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.Monitor;


/**
 * Java多线程并发控制（对比几种同步方案的优缺点）
 * 1、synchronized - 最经典的同步锁 
 * 		 lock.wait
 * 		 lock.notify, lock.notifyAll
 * 
 * 2、ReentrantLock - 改良版  
 * 		condition.wait
 * 		condition.signal, condition.signalAll 
 * 
 * 3、Monitor - 优化版  
 * 		monitor.enter
 * 		monitor.leave
 *
 */
public class Test01Monitor {
	
	final static ExecutorService executor = ThreadPoolManager.executor;
	
	
	public static void main(String[] args) throws InterruptedException {
		final MonitorSample sample = new MonitorSample();
		
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
	

	/*
		The Monitor class that functions as a Mutex, ensuring serial access to the
		defined areas in our code, much like the synchronized keyword but with
		much easier semantics and some useful additional features.
		
		Monitor.enter			尝试进入monitor，会一直阻塞直到成功进入到monitor
		Monitor.enterIf			一旦进入monitor，不会阻塞等待condition条件的满足，而是立即返回一个布尔值描述是否进入monitor block成功
		Monitor.enterWhen		一旦进入monitor，就会无限阻塞，直到condition条件满足才结束阻塞状态
		Monitor.tryEnter		尝试进入monitor，如果失败，立即返回，不会阻塞
		Monitor.tryEnterIf		当且仅当进入monitor成功，并且condition也满足，才返回true，否则返回false，不会阻塞
		
		it's probably a good idea to use one of the timed versions and handle the condition when the lock is unavailable, 
		or the condition never seems to be satisfied.
	 */
	static class MonitorSample {
	    private List<String> list = new ArrayList<String>();
	    private static final int MAX_SIZE = 10;

	    private Monitor monitor = new Monitor();
	    
	    private Monitor.Guard listBelowCapacity = new Monitor.Guard(monitor) {
	        @Override
	        public boolean isSatisfied() {
	            boolean flag = (list.size() < MAX_SIZE);
	            if(! flag) {
	            	System.out.println(Thread.currentThread().getName() + ": put : no more space, waiting");
	            }
	            return flag;
	        }
	    };
	    
	    private Monitor.Guard listNotEmpty = new Monitor.Guard(monitor) {
	    	@Override
	    	public boolean isSatisfied() {
	    		boolean flag = (list.size() > 0);
	    		if(! flag) {
	    			System.out.println(Thread.currentThread().getName() + ": remove : no more item, waiting");
	    		}
	    		return flag;
	    	}
	    };

	    public void put(String item) throws InterruptedException {
	    	// Enters this monitor when the guard is satisfied. Blocks indefinitely, but may be interrupted.
	        monitor.enterWhen(listBelowCapacity);
	        try {
	            list.add(item);
	        } finally {
	            monitor.leave();
	        }
	    }
	    
	    public String remove() throws InterruptedException {
	    	monitor.enterWhen(listNotEmpty);
	    	try {
	    		String item = list.remove(0);
	    		return item;
	    	} finally {
	    		monitor.leave();
	    	}
	    }
	    
	    public void print(String type) {
	    	monitor.enter();
	    	try {
	    		System.out.println(Thread.currentThread().getName() + ": " +  type + ": " + list);
	    	} finally {
	    		monitor.leave();
	    	}
	    }
	}
	
}
