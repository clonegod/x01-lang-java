package clonegod.conc01.collection.list;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

/**
 * CopyOnWrite容器-写时复制的容器，
 *  特点：
 *  1、读写分离：写发生在容器的副本数据上，读发生在原始集合上。
 *  2、多线程并发写的安全操作，写操作底层是加锁的。
 *  
 * 	最大的好处：既可以保证多个线程写并发的线程安全，又不对读线程造成同步阻塞！
 * 
 * 底层原理：
 * 	当对该类型容器进行写操作（增加元素、修改元素、删除元素）时，不直接操作原来的容器，而是以加锁的方式复制一个副本，在副本上进行写操作，因此多个写是线程安全的。
 *  当写操作完成后，再将修改后的容器赋值给原来的容器。
 *  这样做的好处：可以对CopyOnWrite容器进行并发的读，即使有其它线程进行写操作，也不需要加锁，因为当前容器不会发生更新。更新是发生在副本容器上的 。
 *	所以，CopyOnWrite容器是一种读写分离的思想，读和写操作的是不同的容器。
 *
 * 适用场景:
 * 	读多写少 - 因为每个写操作都需要复制集合的一个副本，如写操作多，则会导致大量复制操作，影响性能。
 *
 */
public class TestCopyOnWriteArrayList {
	
	/**
	 * 测试CopyOnWriteArrayList在多个写线程并发时的安全性
	 * 	结论：
	 * 		ArrayList多线程并发写-不安全
	 * 		CopyOnWriteArrayList多线程并发写-安全
	 */
	@Test
	public void testCopyOnWriteArrayList() throws InterruptedException {
		boolean useCopyOnWriteArraylist = true;
    	
    	final List<Integer> list = 
    			useCopyOnWriteArraylist ? new CopyOnWriteArrayList<Integer>() : new ArrayList<Integer>();
        
    	final int totalSize = 2000;
        final CountDownLatch latch = new CountDownLatch(totalSize << 1);
        
        
        // 初始化集合
        for (int i=0; i<totalSize; i++){
            list.add(i);
        }
        
        // 并发删除元素
        for (int i=0; i<totalSize; i++) {
        	new Thread(){
        		@Override
        		public void run(){
    				list.remove(0);
        			latch.countDown();
        		}
        	}.start();
        }
        
        // 并发添加新元素
        for (int i=0; i<totalSize; i++) {
        	new Thread(){
        		@Override
        		public void run(){
        			list.add(1);
        			latch.countDown();
        		}
        	}.start();
        }
                
        latch.await();
        
        // 如果集合是线程安全的，则最后的元素个数仍然是2000个
        System.out.println(list.size());
        assertTrue(list.size() == totalSize);
	}
	
	
}
