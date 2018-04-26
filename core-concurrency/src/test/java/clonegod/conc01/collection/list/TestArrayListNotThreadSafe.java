package clonegod.conc01.collection.list;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import clonegod.concurrency.util.CommonUtil;

/**
 *	Vector - 线程安全的容器
 *	ArrayList - 线程不安全的容器
 */
public class TestArrayListNotThreadSafe {
	
	@Test
    public void testArrayListNotThreadSafe() throws InterruptedException {
    	
    	boolean useArraylist = false;
    	
    	final List<Integer> list = useArraylist ? new ArrayList<Integer>() : new Vector<Integer>();
        
        final CountDownLatch latch = new CountDownLatch(20);
        
        // 初始10000个元素
        for (int i=0; i<10000; i++){
            list.add(i);
        }
        
        // 10个线程并发删除，每个线程删除1000个
        for (int i=0; i<10; i++) {
        	new Thread(){
        		@Override
        		public void run(){
        			for (int i=0; i<1000; i++) {
        				list.remove(0);
        				CommonUtil.sleep(new Random().nextInt(10));                    	
        			}
        			latch.countDown();
        		}
        	}.start();
        }
        
        // 10个线程并发添加新元素，每个线程添加1000个
        for (int i=0; i<10; i++) {
        	new Thread(){
        		@Override
        		public void run(){
        			for (int i=0; i<1000; i++) {
        				list.add(10000*i + i);
        				CommonUtil.sleep(new Random().nextInt(10));                        	
        			}
        			latch.countDown();
        		}
        	}.start();
        }
                
        latch.await();
        
        // 如果集合是线程安全的，则最后的元素个数仍然是10000个
        System.out.println(list.size());
        assertTrue(list.size() == 10000);
    }
}