package clonegod.conc05.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import clonegod.concurrency.util.CommonUtil;

/**
 *  ScheduledThreadPool
 *  	执行延迟任务，循环调度任务的线程池
 *  底层：
 *  	使用DelayedWorkQueue实现延迟执行任务
 *  
 *  spring对定时任务的支持：
 *  	提供 @Scheduler注解。对定时任务进行扩展：可使用cron表达式或参数进行配置
 *
 */
public class TestScheduledJob {
	
    public static void main(String args[]) throws Exception {
    
    	Command command = new Command();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        int initialDelay = 3;
        int delay = 1;
        ScheduledFuture<?> scheduleTask = scheduler.scheduleWithFixedDelay(command, initialDelay, delay, TimeUnit.SECONDS);
        
        CommonUtil.sleep(5000);
        System.out.println("check task status: done=" + scheduleTask.isDone());
        
        CommonUtil.sleep(100);
        boolean cancelled = scheduleTask.cancel(true);
        System.out.println("cancel task, success=" + cancelled);
        
        // shutdown thread pool immediately
        scheduler.shutdownNow();
    }
    
    static class Command extends Thread {
        public void run() {
            System.out.println("run");
        }
    }
}