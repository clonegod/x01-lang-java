## java并发编程的演化

	synchronized		线程同步，确保多线程安全操作共享资源。moniter_enter, monitor_leave
	volatile			保证修改在多线程间的可见性，但没有线程同步的功能。只有设置的新值不依赖于历史值时，才能使用volatile。
	immutable object	每次修改对象状态就需要构建一个新的对象。新对象具有不可变性，因此多线程并发是安全的。
	
## concurrent			并发编程，无阻塞的并发，CAS
		cas		AtomicInteger AtomicLong
		lock 	ReentrantLock ReentrantReadWriteLock
		
		
## 并发常用构建
		CountdownLatch	1个线程等待其它N个线程完成某个操作后，才继续执行
		ConcurrentHashMap	读取时不用锁，写入时只需要锁要修改桶/Segement
			Segment[] - HashEntry[] - HashEntry next
		CopyOnWriteArrayList	
			通过写时复制（数据快照）来实现线程安全性。适用于读多写少的情况，如果修改次数大于读取次数，未必能获得较好性能。
		Queue
			ArrayBlockingQueue
			LinkedBlockingQueue
			LinkedTransferQueue	
				当接收线程处于等待状态时，transfer才能将任务对象交给它。可以调控上游线程池生产任务对象的速度。
## 任务建模
		Callable
		Future
		FutureTask
		
## 执行器框架/线程池
		Executor
		ScheduleThreadPoolExecutor
		
		ForkJoinPool ForkJoinTask	
			多任务并行框架，当任务可拆分为若干个子任务时，可以使用该框架，其它情况不适用。
	
	
## ForkJoin 
 	- tasks to be performed concurrently on a multiprocessor system
 	- work-stealing
 	- RecursiveTask (which can return a result) or RecursiveAction


## ForkJoinPool
	
	Worker threads can execute only one task at the time, but the ForkJoinPool doesn’t create a separate thread for every single subtask.
	
	Instead, each thread in the pool has its own double-ended queue (or deque, pronounced deck) which stores tasks.
	 
 
## Work Stealing Algorithm
	Simply put – free threads try to “steal” work from deques of busy threads.
	
	By default, a worker thread gets tasks from the head of its own deque. 
	When it is empty, the thread takes a task from the tail of the deque of another busy thread or from the global entry queue,
	since this is where the biggest pieces of work are likely to be located.
	
	This approach minimizes the possibility that threads will compete for tasks. 
	It also reduces the number of times the thread will have to go looking for work, 
	as it works on the biggest available chunks of work first.
 
##  Guidelines 
	Using the fork/join framework can speed up processing of large tasks
 	
	1、Use as few thread pools as possible 
 		– in most cases, the best decision is to use one thread pool per application or system
	
	2、 Use the default common thread pool, if no specific tuning is needed
 
	3、Use a reasonable threshold for splitting ForkJoingTask into subtasks
 	
	4、Avoid any blocking in your ForkJoingTasks
 	
 
 
 将任务拆分为多个小任务，充分利用多核处理器进行并行计算，以加快任务的执行，提供应用程序的性能
 
 The fork/join framework is an implementation of the ExecutorService interface that helps you take advantage of multiple processors. 
  It is designed for work that can be broken into smaller pieces recursively. 
  The goal is to use all the available processing power to enhance the performance of your application.
 
 As with any ExecutorService implementation, the fork/join framework distributes tasks to worker threads in a thread pool. 
 The fork/join framework is distinct because it uses a work-stealing algorithm. 
 Worker threads that run out of things to do can steal tasks from other threads that are still busy.
 
 
 The center of the fork/join framework is the ForkJoinPool class, an extension of the AbstractExecutorService class. 
 ForkJoinPool implements the core work-stealing algorithm and can execute ForkJoinTask processes.
 
 
## 【Basic Use】
 	The first step for using the fork/join framework is to write code that performs a segment of the work. 
 			if (my portion of the work is small enough) // 当任务足够小/足够简单的时候，不再拆分任务，直接完成这个任务
			  do the work directly
			else
			  split my work into two pieces				// 否则，将任务拆分为2个子任务，提交到线程池中
			  invoke the two pieces and wait for the results
 
 
 
## 【具体用法】
 	1、自定义算法：using the fork/join framework to implement custom algorithms for tasks to be performed concurrently on a multiprocessor system
  
 	2、Java SE 提供的fork/join框架工具：
 		> Arrays.parallelSort()
 		One such implementation, introduced in Java SE 8, is used by the java.util.Arrays class for its parallelSort() methods. 
 		These methods are similar to sort(), but leverage concurrency via the fork/join framework. 
 		Parallel sorting of large arrays is faster than sequential sorting when run on multiprocessor systems. 
	
		> stream.parallel
		Another implementation of the fork/join framework is used by methods in the java.util.streams package, 
		which is part of Project Lambda scheduled for the Java SE 8 release.		
