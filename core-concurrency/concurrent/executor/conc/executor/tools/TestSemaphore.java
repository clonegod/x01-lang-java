package conc.executor.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import conc.util.CommonUtil;

/**
 * Semaphore
 * 	非常适合高并发访问，通过释放信号的方式来控制线程的执行。
 * 	- 比如，使用信号量实现系统限流，只有获取到信号量的线程才能继续执行，否则阻塞等待其它线程是否信号量，或者超时后退出。
 * 	- 另，可利用redis的高性能进行限流: 
 * 		利用expire的特性，对每分钟请求次数进行累计，如果1分钟内没有访问，则自动清除，否则每次请求+1.超过阈值则返回“请求过于频繁”。
 * 
 * 相关：
 * 	PV-page view 
 * 		网站总访问量，页面浏览次数或点击量，页面每刷新1次就算1个pv
 * 	UV-unique visitor
 * 		以独立的用户或客户端ip为单位进行统计，时间维度上0:00-24:00之内相同ip的客户端只记录1次。
 * 	QPS-query per second
 * 		每秒查询数，即系统吞吐量。
 * 		qps很大程度上反应了系统的繁忙程度。每次请求的背后，可能对应着多次磁盘io，多次远程网络调用，多个cpu时间片。
 * 		通过qps，可以直观反应系统的业务情况，一旦当前qps超过预先设定的阈值，可以考虑增加机器对集群进行扩容，以免太大压力导致服务宕机。
 * 		可以通过前端压力测试得到估值。
 * 	RT-response time
 * 		即请求响应时间。这个指标非常关键，直接反应了前端用户的体验，因此如何降低rt时间非常重要。
 * 	其它：
 * 		cpu, 内存，网络，磁盘情况
 * 		数据库层面的指标，某张表每秒执行了多少次 select/update/delete/insert - druid提供了相关统计功能
 */
public class TestSemaphore {
	public static void main(String[] args) {
		// 线程池  
        ExecutorService exec = Executors.newCachedThreadPool();  
        // 只允许最多5个线程同时访问  
        final Semaphore semp = new Semaphore(5);  
        // 模拟20个客户端访问  
        for (int index = 0; index < 20; index++) {  
            final int NO = index;  
            Runnable run = new Runnable() {  
                public void run() {  
                    try {  
                        // 获取许可  
                        while(! semp.tryAcquire(1, 2, TimeUnit.SECONDS)) {
                        	System.err.println("无可用信号量，线程退出，稍后再试:" + NO);
                        	CommonUtil.sleep(1000);
                        }
                        System.out.println("Accessing: " + NO);  
                        //模拟实际业务逻辑
                        Thread.sleep(1000 + (long) (Math.random() * 1000));  
                        // 访问完后，释放  
                        semp.release();  
                    } catch (Exception e) {  
                    	e.printStackTrace();
                    }  
                }  
            };  
            exec.execute(run);  
        } 
        
        try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        System.out.println(semp.getQueueLength());
        
        // 退出线程池  
        exec.shutdown();  
		
	}
}
