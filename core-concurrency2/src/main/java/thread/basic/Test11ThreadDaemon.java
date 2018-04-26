package thread.basic;

/**
 * 当JVM中运行的都是守护线程时，JVM将会自动退出!
 *	后台线程：开启后，与前台线程共同争抢cpu资源，当所有前台线程都结束后，后台线程将自动结束。
 *
 * 通过控制主线程的有效运行时间，可以间接控制后台线程的生命周期。
 * 只要让主线程在某个时间段后结束，后台线程也会自动结束运行！
 */
public class Test11ThreadDaemon {
	public static void main(String[] args) throws Exception {
		
		Thread backThread = new Thread(new Runnable() {
			public void run() {
				while(true)
					System.out.println("守护线程运行中...");
			}
		});
		
		// 必须在start线程前面设置daemon
		backThread.setDaemon(true);
		
		backThread.start();
		
		long mills = 1000; // 通过控制主线程的运行时间，来控制后台线程的执行时间。
		
		Thread.sleep(mills);
		
	}
}
