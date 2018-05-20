package queue.blocking.deque;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * ConcurrentLinkedDeque
 * 	高并发 + 双端队列  + 无界队列
 *
 */
public class TestConcurrentLinkedDeque {
	
	public static void main(String[] args) {
		ConcurrentLinkedDeque<String> concDeque = new ConcurrentLinkedDeque<>();
		concDeque.add("c");
		concDeque.addFirst("b");
		concDeque.addFirst("a");
		concDeque.addLast("end");
		
		System.out.println(concDeque.pollFirst());
		System.out.println(concDeque.pollFirst());
		System.out.println(concDeque.pollFirst());
		System.out.println(concDeque.getLast());
		
	}
}
