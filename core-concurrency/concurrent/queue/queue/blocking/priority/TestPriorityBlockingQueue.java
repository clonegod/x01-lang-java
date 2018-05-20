package queue.blocking.priority;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * PriorityBlockingQueue
 *	基于优先级的阻塞队列，存入队列中的元素必须实现Comparable接口，以决定不同任务的优先级顺序。
 *
 * 底层优先级的处理：
 * 	每次take取元素的时候，实时找出优先级最高的那个元素。--搜索优先级最高的元素并返回。	
 * 	返回1个元素后，剩余元素可能是完全有序的，也可能是部分有序的。具体还需参考源码采用的算法逻辑。
 * 
 * 特点：
 * 	1、添加元素时，不对优先级进行排序。即加入元素时不排序。
 * 	2、获取元素时，寻找优先级最高的元素并返回。
 * 	3、无界队列。
 */
public class TestPriorityBlockingQueue {
	
	public static void main(String[] args) throws Exception{
		
		PriorityBlockingQueue<Task> q = new PriorityBlockingQueue<Task>();
		
		Task t1 = new Task(1, "任务1");
		Task t2 = new Task(2, "任务2");
		Task t3 = new Task(3, "任务3");
		Task t4 = new Task(4, "任务4");
		Task t5 = new Task(5, "任务5");
		
		q.add(t5);
		q.offer(t4);
		q.offer(t3, 1, TimeUnit.SECONDS);
		q.put(t2);
		q.add(t1);
		
		while(! q.isEmpty()) {
			Task t = q.poll();
			System.out.println("task:" + t);
			System.out.println(q);
			System.out.println("====================");
		}
		
	}
}
