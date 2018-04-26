package command1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 从工作队列中获取任务，执行任务
 */
public class Worker {
	
	private static final ExecutorService executor = Executors.newFixedThreadPool(10);
	
	private CommandQueue queue;
	
	public Worker(CommandQueue queue) {
		this.queue = queue;
	}

	public void doWork() throws Exception {
		while(true) {
			final Command c = queue.takeCommand();
			if(c == null) {
				continue;
			}
			executor.execute(new Runnable() {
				@Override
				public void run() {
					c.execute();
				}
			});
		}
	}
	
	public void shutdown() {
		executor.shutdown();
	}
}
