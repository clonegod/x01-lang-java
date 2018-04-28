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
			
