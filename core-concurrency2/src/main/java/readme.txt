java并发编程的进化史
	runnable
	synchronized		线程同步，确保多线程安全操作共享资源。moniter_enter, monitor_leave
	volatile			保证修改在多线程间的可见性，防止指令重排序，没有线程同步的功能。适用于设置的新值不依赖于历史值时使用volatile。
	immutable object	每次修改对象状态就需要构建一个新的对象。新对象具有不可变性，因此多线程并发是安全的。
	
	[JDK5]
	concurrent			并发编程，无阻塞的并发，CAS
		cas		AtomicInteger AtomicLong
		lock 	ReentrantLock ReentrantReadWriteLock
		并发常用构建
			CountdownLatch	1个线程等待其它N个线程完成某个操作后，才继续执行
			ConcurrentHashMap	读取时不用锁，写入时只需要锁要修改桶/Segment
				Segment[] - HashEntry[] - HashEntry next
			CopyOnWriteArrayList	通过写时复制（数据快照）来实现线程安全性。常用于非关键任务中。适用于读多写少的情况，如果修改次数大于读取次数，未必能获得较好性能。
			BlockingQueue
				ArrayBlockingQueue
				LinkedBlockingQueue
				SynchronousQueue
				
		任务建模
			Callable
			Future
			FutureTask
			
		 执行器框架/线程池
			Executor
			ScheduleThreadPoolExecutor
	
	[JDK7]
		LinkedTransferQueue	当接收线程处于等待状态时，transfer才能将任务对象交给它。可以调控上游线程池生产任务对象的速度。
		ForkJoinPool ForkJoinTask	分支合并框架，任务可拆分为不同子任务时，可以使用该框架，其它情况不适用。
		
=============================================================================================	

Java Concurrency – Synchronizers

help manage a set of threads that collaborate with each other. Some of these include:
	CyclicBarrier	所有线程都达到barrier，才能继续执行后续代码。线程数固定，但是可重复使用cyclicBarrier
	Phaser		分阶段由不同线程完成任务，每个阶段可设置不同的线程，phaser可重用，每个阶段可动态设置线程数
	CountDownLatch	1个线程等待其它线程执行完成后，该线程才能继续向下执行。
	Exchanger
	Semaphore	信号量，通过信号获取来实现对共享资源的访问。可实现互斥访问，限流访问，协调线程的交替执行等功能。
	SynchronousQueue 生产者线程put成功的前提是：当前有一个消费者线程正在等待take资源，因此使用该队列时必须能够保证随时都有可用的空闲消费者线程，否则将会导致生产者线程put时阻塞。Executors.newCachedPool()内部就是使用的SynchronousQueue队列，CachedPool可以保证有足够的消费者线程可用。


