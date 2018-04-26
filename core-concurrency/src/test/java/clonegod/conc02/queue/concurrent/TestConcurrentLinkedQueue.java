package clonegod.conc02.queue.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 队列特性：FIFO 先进先出
 * 
 * ConcurrentLinkedQueue 特点：
 * 	1、高性能无阻塞
 *  2、无界队列
 */
public class TestConcurrentLinkedQueue {
	
	public static void main(String[] args) {
		ConcurrentLinkedQueue<String> q = new ConcurrentLinkedQueue<String>();
		q.offer("a");
		q.offer("b");
		q.offer("c");
		q.offer("d");
		q.add("e");
		
		System.out.println(q.poll());	//a 从头部取出元素，并从队列里删除
		System.out.println(q.size());	//4
		System.out.println(q.peek());	//b	从头部取出元素，不删除
		System.out.println(q.size());	//4
	}
	
}
