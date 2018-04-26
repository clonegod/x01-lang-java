package clonegod.java8.concurrency;

public class Test01BeforeJava5Thread {
	
	/**
	 * 最原始的多线程写法
	 * 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		CompletableRunnable runnable = new CompletableRunnable();
		
		Thread subThread = new Thread(runnable, "sub");
		
		subThread.start();
		
		// main等待subThread执行结束后，才继续执行 --- 线程串行执行
		subThread.join(0); 
		
		System.out.printf("[Thread - %5s] - Starting...\n", 
				Thread.currentThread().getName());
		
		System.out.printf("[Thread - %5s] - runnable completed: %s\n", 
				Thread.currentThread().getName(), runnable.isCompleted());
		
	}
	
	private static class CompletableRunnable implements Runnable {
		
		private boolean completed; // 引入变量，描述任务执行状态
		
		public boolean isCompleted() {
			return completed;
		}

		@Override
		public void run() {
			// 使用synchronized修饰符来控制线程同步
			synchronized (this) {
				System.out.printf("[Thread - %5s] - Hello World\n", 
						Thread.currentThread().getName());
				completed = true;
			}
		}
		
	}
}
