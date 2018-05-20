package queue.blocking.deque;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * LinkedBlockingDeque
 * 
 * 	阻塞队列 + 双端队列
 * 
 * 特点：
 * 	1、阻塞队列
 * 	2、可以指定队列最大容量，如果不指定则为无界队列
 * 	3、支持从队列头部或尾部进行入队和出队。（工作窃取是否就是采用这种队列实现的呢？）
 *
 */
public class TestLinkedBlockingDeque {

	public static void main(String[] args) {
		final int capacity = 10;
		LinkedBlockingDeque<String> dq = new LinkedBlockingDeque<String>(capacity);

		dq.addFirst("a");
		dq.addFirst("b");
		dq.addFirst("c");
		dq.addFirst("d");
		dq.addFirst("e");
		dq.addLast("f");
		dq.addLast("g");
		dq.addLast("h");
		dq.addLast("i");
		dq.addLast("j");
		//dq.offerFirst("k");
		System.out.println("查看头元素：" + dq.peekFirst());
		System.out.println("获取尾元素：" + dq.pollLast());
		Object [] objs = dq.toArray();
		for (int i = 0; i < objs.length; i++) {
			System.out.println(objs[i]);
		}
		
	}
}
