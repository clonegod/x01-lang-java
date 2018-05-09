package juc.executor.completionService;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 线程任务
 *
 */
class Task implements Callable<Result> {

	public Result call() throws Exception {
		Random rand = new Random();
		long seconds = rand.nextInt(5);
		
        Result rs = new Result();
        rs.executeThreadName = Thread.currentThread().getName();
        rs.randNumber = seconds;
        
        TimeUnit.SECONDS.sleep(seconds);  
        
        return rs;
	}  
	  
}  

