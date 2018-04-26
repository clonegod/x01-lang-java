package clonegod.concurrency.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CommonUtil {
	
	public static void sleep(int millis) {
		try {
			TimeUnit.MILLISECONDS.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static String currentTime() {
		return new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date());
	}
	
	public static String getThreadName() {
		return "【" + Thread.currentThread().getName() + "】 ";
	}
}
