package baeldung.queue.DelayQueue;

/**
 * BlockingQueue 
 * 		Unbounded Queue	无界队列
 * 			BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>();
 * 			注意点：消费者消费资源的速度能够与生产者持平，否则可能会造成资源队列无限填充，最终导致OutOfMemory  
 * 
 * 		Bounded Queue	有界队列
 * 			BlockingQueue<String> blockingQueue = new LinkedBlockingDeque<>(10);
 * 			如果队列满了，可自动阻塞新提交的任务，提供了限流的作用。
 * 	
 * Adding Elements
 * 		add() 	添加元素，如果队列满，则抛异常
 * 		put()	阻塞式添加元素，直到队列有空闲空间并添加成功
 * 		offer()	添加元素，无阻塞立即返回，如成功则返回true，失败，返回false
 * 		offer(timeout)	添加元素，在超时时间内等待，如成功则返回true，失败，返回false
 * 
 * Retrieving Elements
 * 		take()	阻塞式获取元素，直到队列中有元素返回
 * 		poll(timeout)	在阻塞时间内获取，如成功则返回true，失败，返回false
 */
