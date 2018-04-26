package thread.basic;

import java.util.concurrent.CountDownLatch;

import util.CommonUtil;

/**
 *  线程中断相关的几个关键点
 *  如何中断一个线程？
 *  	1. 由其它线程来中断线程t: otherThreadInstance.interrupt()
 *  	2. 自己中断自己：Thread.currentThread().interrupt()
 *  	
 *  怎样检查线程中断状态？
 *  	1. Thread.interrupted() --- 检查中断状态，并会重置清楚中断状态。即，当某个线程被中断之后，第一次调用该方法返回true，并且会将中断状态重置为false
 *  	2. Thread.currentThread().isInterrupted() --- 仅仅检查中断状态，不会修改重置清除中断状态。
 *  
 *  怎样处理中断异常？
 *  	1. 直接抛出异常，不对中断异常进行捕获：线程任务代码不知道怎么处理中断，则将InterruptedException抛出到上层调用者，由调用者决定如何处理中断异常；
 *  	2. 捕获中断异常：线程运行代码知道如何处理中断，则捕获到InterruptedException后，重新恢复中断状态，然后在代码中根据中断状态执行对应的异常处理；
 *  
 *  其它：
 *  	1. Object.wait(),Thread.sleep()等阻塞函数，会监测线程中断，它们对中断响应是：清除中断状态，抛出InterruptedException，表示阻塞操作因为中断而提前结束。
 *  	2. 捕获到InterruptedException后， 在catch块中主动中断自己，即调用Thread.currentThread().interrupt()来恢复中断状态
 *  	3. 在try块中，检查线程的中断状态，如果中断状态为true，可以选择退出当前任务（推荐）；或者，也可以忽略中断----根据实际场景以及具体情况来决定是否中断就接收任务。
 *  	
 *	关于线程中断的一篇文章：
 *	https://www.ibm.com/developerworks/cn/java/j-jtp05236.html
 */
public class Test14ThreadInterrupt {
	
	static CountDownLatch cdl = new CountDownLatch(1);
	
	public static void main(String[] args) throws Exception {
		testThreadInterruptStopTask();
//		testThreadInterruptContinueTask();
	}
	
	static void testThreadInterruptStopTask() throws Exception {
		Thread t = new Thread(new ObeyInterruptTask());
		t.start();
		CommonUtil.threadSleep(1000);
		t.interrupt();
		cdl.await();
	}
	
	static void testThreadInterruptContinueTask() throws Exception {
		Thread t = new Thread(new IngoreInterruptTask());
		t.start();
		CommonUtil.threadSleep(1000);
		t.interrupt();
		cdl.await();
	}
	
	static class ObeyInterruptTask implements Runnable {

		@Override
		public void run() {
			for(;;) {
				try {
					// 由于在catch块中调用isInterrupted()方法来检测中断状态，不会清楚中断状态，所以此处可以检测到线程中断状态为true。
					// 因此，发生中断之后，循环将会break退出！
					if(Thread.currentThread().isInterrupted()) {
						System.err.println("当前线程已被中断，结束循环");
						break;
					}
					Thread.sleep(100);
					System.out.println(Thread.currentThread().getName() + " running...");
				} catch (InterruptedException e) {
					e.printStackTrace();
					
					// Restore the interrupted status ---- 接收到中断异常后，主动中断自己，以便恢复中断状态
					Thread.currentThread().interrupt();
					
					// 仅仅检测当前线程的中断状态，不会清除中断状态
					System.out.println("Is current thread has been interrupted? " + Thread.currentThread().isInterrupted());
				}
			}
		}
	}
	
	
	static class IngoreInterruptTask implements Runnable {
		@Override
		public void run() {
			for(;;) {
				try {
					// 由于在catch块中调用了Thread.interrupted()方法，重置清空了中断状态，所以此处检测线程中断状态都会返回false。
					// 因此，发生中断之后，循环仍然会继续无限执行下去！
					if(Thread.currentThread().isInterrupted()) {
						System.err.println("当前线程已被中断，结束循环");
						break;
					}
					Thread.sleep(100);
					System.out.println(Thread.currentThread().getName() + " running...");
				} catch (InterruptedException e) {
					e.printStackTrace();
					
					// Restore the interrupted status ---- 接收到中断异常后，主动中断自己，以便恢复中断状态
					Thread.currentThread().interrupt();
					
					// 获取线程当前的中断状态，并清除线程的中断状态
					System.out.println("Is current thread has been interrupted? " + Thread.interrupted());
				}
			}
		}
	}
	
	
	
}
