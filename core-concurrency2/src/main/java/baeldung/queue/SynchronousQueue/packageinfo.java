package baeldung.queue.SynchronousQueue;

/**
 * SynchronousQueue 
 * 	this implementation allows us to exchange information between threads in a thread-safe manner.
 * 	线程间安全地传递数据
 * 	
 * exchange point for a single element between two threads
 * 	两个线程间交换1个元素/数据，不需要中间队列来阻塞暂存数据，以便快速提交任务并执行。
 * 	
 * 生产者线程put成功的前提是：当前有一个消费者线程正在等待take资源。
 * 
 * 
 */
