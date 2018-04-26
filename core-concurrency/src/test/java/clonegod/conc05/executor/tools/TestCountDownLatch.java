package clonegod.conc05.executor.tools;

import java.util.concurrent.CountDownLatch;

import clonegod.concurrency.util.CommonUtil;

/**
 * 1个线程X，等待其它N个线程发出通知（比如N个线程完成初始化工作）后，该线程X才继续向下执行。
 * 
 */
public class TestCountDownLatch {

	public static void main(String[] args) {
		final CountDownLatch countDown = new CountDownLatch(2);
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(CommonUtil.getThreadName() + "线程等待其他线程处理完成...");
					countDown.await(); // t1线程等待其它线程完成初始化，才能继续执行
					System.out.println(CommonUtil.getThreadName() + "线程继续执行...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		},"thread-await-0");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(CommonUtil.getThreadName() + "\t线程进行初始化操作...");
					Thread.sleep(3000);
					System.out.println(CommonUtil.getThreadName() + "\t线程初始化完毕，通知t1线程继续...");
					countDown.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "thread-init-1");
		
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(CommonUtil.getThreadName() + "\t线程进行初始化操作...");
					Thread.sleep(4000);
					System.out.println(CommonUtil.getThreadName() + "\t线程初始化完毕，通知t1线程继续...");
					countDown.countDown();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "thread-init-2");
		
		t1.start();
		t2.start();
		t3.start();

	}
	
}
