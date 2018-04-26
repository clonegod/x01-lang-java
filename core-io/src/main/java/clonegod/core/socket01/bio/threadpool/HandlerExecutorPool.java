package clonegod.core.socket01.bio.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 服务端维护一个线程池，用来处理客户端请求
 *
 */
public class HandlerExecutorPool {

	private ExecutorService executor;
	
	public HandlerExecutorPool(int maxPoolSize, int queueSize){
		this.executor = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(),
				maxPoolSize, 
				120L, 
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize));
	}
	
	public void execute(Runnable task){
		this.executor.execute(task);
	}

	public void shutdown() {
		this.executor.shutdownNow();
	}
	
}
