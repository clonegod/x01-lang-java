package clonegod.thread02.communication;

import java.util.ArrayList;
import java.util.List;
/**
 * t2 线程不再主动轮询结果，改为等待通知
 * 
 * 线程通知-notify   （wait,notify必须在synchronized绑定的同步锁上调用）
 * 不足的地方：notify发出通知后，被通知的线程仍然需要等待发出通知的线程执行完毕后，才能继续执行。---因为它们都竞争同一个锁。
 */
public class TestListAdd2 {
	private volatile static List<String> list = new ArrayList<>();	
	
	public void add(){
		list.add("string");
	}
	public int size(){
		return list.size();
	}
	
	public static void main(String[] args) {
		
		final TestListAdd2 list2 = new TestListAdd2();
		
		final Object lock = new Object();
		
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (lock) {
						System.out.println("t1启动..");
						for(int i = 0; i <10; i++){
							list2.add();
							System.out.println("当前线程：" + Thread.currentThread().getName() + "添加了一个元素..");
							Thread.sleep(500);
							if(list2.size() == 5){
								lock.notify(); // notify不释放锁-唤醒同一个锁上处于wait状态的线程
								System.out.println("已经发出通知..t1 notify t2");
							}
						}						
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}, "t1");
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (lock) {
					System.out.println("t2启动..");
					if(list2.size() != 5){
						try {
							System.out.println("t2 wait..");
							lock.wait(); // 释放锁
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					/** 虽然t2被t1发出的notify唤醒了，但只有等待t1线程释放锁之后，t2线程才能获取到锁继续向下执行 */
					System.out.println("当前线程：" + Thread.currentThread().getName() + "收到通知线程停止..");
					throw new RuntimeException();
				}
			}
		}, "t2");
		
		t2.start();
		
		t1.start();
		
	}
	
}
