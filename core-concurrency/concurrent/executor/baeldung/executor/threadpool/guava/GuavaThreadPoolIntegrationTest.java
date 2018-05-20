package baeldung.executor.threadpool.guava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GuavaThreadPoolIntegrationTest {

	/**
	 * execution of a task in the same thread. 
	 * executor在同一个线程中运行任务
	 */
    @Test
    public void whenExecutingTaskWithDirectExecutor_thenTheTaskIsExecutedInTheCurrentThread() {

        Executor executor = MoreExecutors.directExecutor();

        AtomicBoolean executed = new AtomicBoolean();

        executor.execute(() -> {
            try {
            	assertEquals("main", Thread.currentThread().getName());
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executed.set(true);
        });

        assertTrue(executed.get());
    }

    /**
     * Listening Decorators
     * 	使用监听器来处理异步任务的结果：将多个任务的异步结果包装为一个ListenableFuture
     */
    @Test
    public void whenJoiningFuturesWithAllAsList_thenCombinedFutureCompletesAfterAllFuturesComplete() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        
        // wrap the ExecutorService
        ListeningExecutorService listeningExecutorService = 
        		MoreExecutors.listeningDecorator(executorService);
        
        // receive ListenableFuture instances upon task submission
        ListenableFuture<String> future1 = listeningExecutorService.submit(() -> "Hello");
        ListenableFuture<String> future2 = listeningExecutorService.submit(() -> "World");

        // 将所有异步任务的结果汇总，进行集中处理。如果有一个任务失败，则立即返回失败
        // combine several ListenableFuture instances in a single ListenableFuture 
        // that completes upon the successful completion of all the futures combined.
        String greeting = Futures.allAsList(future1, future2)
        						.get()
        						.stream()
        						.collect(Collectors.joining(" "));
        
        assertEquals("Hello World", greeting);

    }

}
