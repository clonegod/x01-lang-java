【多线程编程】
1、线程安全基础知识
	synchronized: 线程同步锁，提供变量操作的“原子性”与更新后值的“可见性”保证。
	volatile: 提供多线程之间变量值的“可见性”，没有“原子性”的保证。可看作轻量级的synchronized实现。
	原子变量-CAS: atomic类系列的对象，其方法支持“原子操作”，但并不保证多次操作的原子性。可使用cas复合操作来确保原子性-compareAndSet。 
		AtomicInteger
		AtomicLong
		
2、线程之间的通信与协调
	wait、notify、notifyAll
	await、signal、signalAll 
	ThreadLocal
	单例模式与多线程

-------------------------------------------------------

3、同步类容器
	Vector		
	HashTable	
	
4、并发类容器
	> 并发Map容器
	ConcurrentHashMap		代替HashTable
	ConcurrentSkipListMap	支持排序的并发类容器，可看作对ConcurrentHashMap的补充
	> 写时复制-当发生写操作时，在副本上进行。
		CopyOnWriteArrayList	代替Vector
		CopyOnWriteArraySet		

5、队列(Queue)与双端队列(Deque)  - 什么场景用哪种queue？运行时切换queue？
主要分为两类；
	> 高性能队列：	Concurrent类型为代表的Queue
		ConcurrentLinkedQueue	高性能并发队列
	
	> 阻塞队列：BlockingQueue类型为代表的Queue
		ArrayBlockingQueue		基于数组的阻塞队列
		LinkedBlockingQueue		基于链表的阻塞队列
		PriorityBlockingQueue	优先级队列
		DelayQueue				延时队列
		SynchronousQueue		高效无阻塞队列，任务直接从生产者传递给消费者
				
	> 双端队列(支持从队头或对尾操作队列)：
		LinkedBlockingDeque		具有阻塞功能的双端队列
		ConcurrentLinkedDeque	具有高并发功能的双端队列
	
6、多线程与设计模式的结合
	Future模式 - 异步任务，可实现多个相互不依赖的子任务并行执行
	Master-Worker模式 - 大任务拆分成若干子任务，并行计算
	生产者-消费者模式
	
-------------------------------------------------------

7、JDK多任务执行框架
	Executors
	ThreadPoolExecutor
	线程池的配置与调优
	
8、java.util.concurrent包下的工具类
	Semaphore
	CountDownLatch
	CyclicBarrier
	Phaser
	
9、重入锁，读写锁的使用，锁优化策略
	Lock
	ReentrantLock
	ReadWriteLock
	
	