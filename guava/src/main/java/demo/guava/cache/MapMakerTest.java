package demo.guava.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import com.google.common.base.Stopwatch;
import com.google.common.collect.MapMaker;

public class MapMakerTest {

	static Logger log = Logger.getAnonymousLogger();
	
	/**
	 * 通过MapMarker创建具有WeakReference特性的ConcurrentMap
	 * 
	 * 某些场景下需要利用WeakReference来设计缓存
	 * 
	 */
	public static void main(String[] args) throws InterruptedException {
		// treat the MapMaker class as a provider of the most basic caching functionality.
		  ConcurrentMap<Request, Stopwatch> timers = 
				  new MapMaker()
			       .concurrencyLevel(4) //  sets the amount of concurrent modifcations allow in the map
			       .weakKeys()
			       .makeMap();
		  
		  Request request = new Request();
		  
		  Stopwatch stopwatch = Stopwatch.createStarted();
		  
		  timers.putIfAbsent(request, stopwatch);
		  
		  Thread.sleep(500);
		  
		  log.info("time: " + timers.get(request)); // formatted string like "12.3 ms"
		  
	}
	
	static class Request {
		
	}
}
