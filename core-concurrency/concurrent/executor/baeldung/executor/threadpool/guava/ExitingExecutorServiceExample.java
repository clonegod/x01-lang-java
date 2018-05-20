package baeldung.executor.threadpool.guava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.MoreExecutors;


/**
 * 使用Guava提供的扩展线程池实现
 * - 基于已有的线程池进行功能增强：
 * 		在VM退出时，防止线程池中线程长时间执行没有退出，从而导致关闭VM的动作被挂起的问题。
 */
public class ExitingExecutorServiceExample {

    public static void main(String... args) {

        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        
        // 在强制关闭VM前，设置一个超时时间，如果超时后仍然有其它前台任务线程在运行，则强制退出VM。---确保关闭VM的操作不会被长时间挂起！
        final ExecutorService executorService = 
        		MoreExecutors.getExitingExecutorService(executor, 
        				100, TimeUnit.MILLISECONDS);

        executorService.submit((Runnable) () -> {
            while (true) {
            	System.out.println(System.currentTimeMillis());
//            	CommonUtil.threadSleep(50);
            }
        });

    }

}
