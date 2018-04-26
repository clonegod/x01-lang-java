package juc.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

import juc.ThreadSafe;

/**
 * BoundedExecutor
 * <p/>
 * Using a Semaphore to throttle（限流） task submission
 *	默认的executor在阻塞队列填满后，采用饱和策略来处理新的任务。
 *	这里提出一个新的解决方案：当工作队列填满后，阻塞调用者提交新的任务。---使用信号量对提交到线程池的任务进行限流
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class BoundedExecutor {
    private final Executor exec;
    private final Semaphore semaphore;
    
    /**
     * 
     * @param exec	线程池
     * @param bound	线程池的大小 + 允许加入到阻塞排队的任务数量
     */
    public BoundedExecutor(Executor exec, int bound) {
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command)
            throws InterruptedException {
        semaphore.acquire();
        try {
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        command.run();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            semaphore.release();
        }
    }
}
