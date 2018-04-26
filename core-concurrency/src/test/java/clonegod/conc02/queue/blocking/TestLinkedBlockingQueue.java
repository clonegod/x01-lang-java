package clonegod.conc02.queue.blocking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  LinkedBlockingQueue
 *  	基于链表的阻塞队列，与ArrayBlockingQueue类似，内部维护一个链表来存储元素。
 *  特点：
 *  	1、无界队列-链表结构.
 *  	2、有界队列-可以设置列队的容量上限，不指定则是无界的。
 *  	3、支持高效处理并发数据，因为其内部采用了分离锁（读写分离两个锁），从而实现生产者与消费者操作的完全并行
 *	适用场景：
 *		并发量不是很高，消费者线程足以应付生产者提交的任务，可以不限制队列的容量，生产者提交多少任务就缓存多少任务，由消费者逐一处理
 *		该场景下需确保生产者不会提交大量的任务，不会造成大量任务的堆积，队列可以缓冲所有提交的任务，不拒绝任务。
 *		例如，10-5点的车流浪已经过了高峰期，不需要限流。---linkedList无界队列，有多少存多少。
 */
public class TestLinkedBlockingQueue {

	public static void main(String[] args) {
		//阻塞队列
		LinkedBlockingQueue<String> q = new LinkedBlockingQueue<String>();
		q.offer("a");
		q.offer("b");
		q.offer("c");
		q.offer("d");
		q.offer("e");
		q.add("f");
		System.out.println(q.size());
		
		for (Iterator<String> iterator = q.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			System.out.println(string);
		}
		
		// drainTo 从队列一次取多个元素，尝试取指定个数，如果队列元素不够，则有多少返回多少
		List<String> list = new ArrayList<String>();
		int maxElements = 100;
		System.out.println(q.drainTo(list, maxElements));
		System.out.println("After drainTo list, queue size=" + q.size());
		System.out.println("After drainTo list, list size=" + list.size());
		for (String string : list) {
			System.out.println(string);
		}
	}
}
