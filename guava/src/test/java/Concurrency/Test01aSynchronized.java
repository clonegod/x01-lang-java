package Concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Test01aSynchronized {
	
	static final ExecutorService executor = ThreadPoolManager.executor;
	
	/**
	 * 模拟有界队列 
	 */
	static class ReentrantLockSample {
	    private List<String> list = new ArrayList<String>();
	    private static final int MAX_SIZE = 10;

	    public void put(String item) throws InterruptedException {
	        synchronized (this) {
	        	try {
	        		while (list.size() == MAX_SIZE) {
	        			System.out.println(Thread.currentThread().getName() + ": " +"queue is full, waiting");
	        			this.wait();
	        		}
	        		list.add(item);
	        		this.notifyAll();
	        	} catch(InterruptedException ex) {
	        		throw ex;
	        	}  finally {
	        	}
			}
	    }
	    
	    public String remove() throws InterruptedException {
	    	synchronized (this) {
	    		try {
	    			while (list.size() == 0) {
	    				System.out.println(Thread.currentThread().getName() + ": " +"queue is empty, waiting");
	    				this.wait();
	    			}
	    			String item = list.remove(0);
	    			this.notifyAll();
	    			return item;
	    		} catch(InterruptedException ex) {
	    			throw ex;
	    		} finally {
	    		}
			}
	    }
	    
	    public synchronized void print(String type) {
	    	System.out.println(Thread.currentThread().getName() + ": " +  type + ": " + list);
	    }
	}
	
	
	
	
	public static void main(String[] args) throws InterruptedException {
		final ReentrantLockSample sample = new ReentrantLockSample();
		
//		for(int i=0; i<2; i++)
		executor.execute(() -> {
			while(true) {
				try {
					sample.put("item");
					sample.print("put");
//					Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10));
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		});
		
//		for(int i=0; i<2; i++)
		executor.execute(() -> {
			while(true) {
				try {
					sample.remove();
					sample.print("remove");
//					Thread.sleep(ThreadLocalRandom.current().nextInt(10, 20));
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		});
		
		ThreadPoolManager.shutdownAndAwaitTermination(executor, 60, TimeUnit.SECONDS);
		
	}
	
}
