package juc.executor;

import java.util.*;
import java.util.concurrent.*;

/**
 * TrackingExecutor
 * <p/>
 * ExecutorService that keeps track of cancelled tasks after shutdownNow
 * 
 * @author Brian Goetz and Tim Peierls
 */
// 自定义的Executor，内部组合一个ExecutorService，对外暴露一个execute方法。在该方法中对被执行任务进行中断状态监测：
// 当线程池被关闭关闭时，在finally中将当前任务记录到一个列表中。
public class TrackingExecutor extends AbstractExecutorService {
    private final ExecutorService exec;
    
    private final Set<Runnable> tasksCancelledAtShutdown =
            Collections.synchronizedSet(new HashSet<Runnable>()); // 保存由shutdownNow()而导致被中断的任务，这些任务将被重新加入到任务队列中

    public TrackingExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    public void shutdown() {
        exec.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    public boolean isShutdown() {
        return exec.isShutdown();
    }

    public boolean isTerminated() {
        return exec.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit)
            throws InterruptedException {
        return exec.awaitTermination(timeout, unit);
    }

    public List<Runnable> getCancelledTasks() {
        if (!exec.isTerminated())
            throw new IllegalStateException(/*...*/);
        return new ArrayList<Runnable>(tasksCancelledAtShutdown);
    }

    /**
     * 记录被中断的任务
     */
    public void execute(final Runnable runnable) {
        exec.execute(new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } finally {
                    if (isShutdown()
                            && Thread.currentThread().isInterrupted())
                        tasksCancelledAtShutdown.add(runnable);
                }
            }
        });
    }
}
