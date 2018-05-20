package clonegod.uitls;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Throwables;

public final class ThreadUtils {
	
	private ThreadUtils() {}
	
	public static String getCurrentThreadName() {
		return Thread.currentThread().getName();
	}
	
	public static void sleep(int millis) {
		try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e) {
			System.err.println("线程被中断：\n" + Throwables.getStackTraceAsString(e));
		}
	}
	
}
