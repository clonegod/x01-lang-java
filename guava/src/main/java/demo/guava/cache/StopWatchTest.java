package demo.guava.cache;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.common.base.Stopwatch;

public class StopWatchTest {
	static Logger log = Logger.getAnonymousLogger();
	
	public static void main(String[] args) {
		Stopwatch stopwatch = Stopwatch.createStarted();
		  doSomething();
		  stopwatch.stop(); // optional

		  Duration duration = stopwatch.elapsed();
		  
		  log.info("duration:" + duration.getNano());
		  
		  log.info("time: " + stopwatch); // formatted string like "12.3 ms"
	}

	private static void doSomething() {
		try {
			TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
