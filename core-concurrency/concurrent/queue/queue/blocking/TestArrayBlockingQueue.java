package queue.blocking;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 * ArrayBlockingQueue
 * 	基于数组的阻塞队列实现，在ArrayBlockingQueue内部，维护一个定长的数组来缓存队列中的元素。
 * 
 * 特点：
 * 	1、有界-数组结构：需要指定队列长度，是有界队列。当队列满时阻塞生产者，队列空时阻塞消费者。
 *  2、 内部没有实现读写分离，意味着生产者和消费者不能完全并行。
 *
 * 适用场景：
 * 	系统并发非常高，生产者线程产生大量任务，此时需要一个有界阻塞队列来进行限流，减轻消费端处理任务的压力。
 * 	例如，8-9点，车流浪高峰期时，需要进行限流。---Array 有界数组。
 * 
 */
public class TestArrayBlockingQueue {

	public  void testPutAndAdd() throws InterruptedException {
		final int capacity = 5;
		final boolean fair = true; // 添加和删除元素严格按照FIFO顺序操作
		ArrayBlockingQueue<String> array = new ArrayBlockingQueue<String>(capacity, fair);
		array.put("a"); // put 如果队列满，则一直等待
		array.put("b");
		array.put("c");
		array.add("d"); // add 如果队列满，则抛异常
		array.add("e");
//		array.add("f");
		System.out.println(array.offer("a", 3, TimeUnit.SECONDS)); // offer 如果队列满，返回false
	}
}
