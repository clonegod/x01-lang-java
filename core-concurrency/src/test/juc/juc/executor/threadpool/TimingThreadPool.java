package juc.executor.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.logging.*;

/**
 * TimingThreadPool
 * <p/>
 * Thread pool extended with logging and timing	---- 扩展的线程池，可以记录处理任务数和统计任务运行时间
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimingThreadPool extends ThreadPoolExecutor {


    public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	// beforeExecute将值存入ThreadLocal，使得afterExecute可以取得该值
    private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
    
    private static final Logger log = Logger.getLogger("TimingThreadPool");
    
    // 追踪已处理的任务数
    private final AtomicLong numTasks = new AtomicLong();
    
    // 追踪处理时间的总和
    private final AtomicLong totalTime = new AtomicLong();

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.info(String.format("Thread %s: start %s", t, r));
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            log.info(String.format("Thread %s: end %s, time=%dns",
                    t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            log.info(String.format("Terminated: avg time per task=%dns",
                    totalTime.get() / numTasks.get()));
        } finally {
            super.terminated();
        }
    }
    
}
