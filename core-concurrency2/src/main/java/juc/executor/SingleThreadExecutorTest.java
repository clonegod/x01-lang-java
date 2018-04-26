package juc.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * 本例有二个知识点：
 * 1. Executors.newSingleThreadExecutor()返回的是一个被包装过的线程池：FinalizableDelegatedExecutorService；
 * 2. FinalizableDelegatedExecutorService对象被GC时会通过finalize()调用线程池的shutdown()，从而确保了不会造成内存泄露的问题；
 * 
 * why would the executor instance be eligible for GC while submit() was executing in the first place?
 * 
 * Executors.newSingleThreadExecutor() 与其它线程池的区别
 * 	Executors.newSingleThreadExecutor()返回的是一个ThreadPoolExecutor的一个包装器对象/代理：FinalizableDelegatedExecutorService
 * 	关键--->该FinalizableDelegatedExecutorService会在自身实例被GC的时候调用finalize()
 *  而finalize()会调用executor的shutdown()，从而引起线程池的关闭。
 *	
 *	在这个例子中，由于executor是在for循环中不断创建的，每一次循环都创建新的的FinalizableDelegatedExecutorService对象
 *	当每次循环结束后，FinalizableDelegatedExecutorService对象都不再被其它对象所引用，所以将被GC线程回收，一旦GC回收该对象，就会调用该对象的finalize()
 *	finalize()再调用内部引用的executor的shutdown()，于是线程池被关闭。
 *
 *	为什么在循环过程中会抛出java.util.concurrent.RejectedExecutionException异常呢？	
 *		线程池的关闭是因为FinalizableDelegatedExecutorService对象被GC垃圾回收了，从而引起线程池的关闭！！！
 *		所以submit()正在提交任务时，线程池是有可能已经处理关闭状态了，所以会出现RejectedExecutionException。
 *	
 *	问题相关的一篇文章：
 *		https://www.farside.org.uk/201309/learning_from_bad_code
 */
public class SingleThreadExecutorTest {
	
	public static void main(String[] args) {
		Callable<Long> callable = new Callable<Long>() {
		      public Long call() throws Exception {
		        // Allocate, to create some memory pressure.
		        byte[][] bytes = new byte[1024][];
		        for (int i = 0; i < 1024; i++) {
		          bytes[i] = new byte[1024];
		        }

		        return 42L;
		      }
		    };
		    for (;;) {
		      Executors.newSingleThreadExecutor().submit(callable);
		    }
	}
}
