package remote.jmx.taskMonitor;

import java.lang.management.ManagementFactory;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import remote.jmx.taskMonitor.mbean.ThreadPoolStatus;
import remote.jmx.taskMonitor.task.FetchTask;
import remote.jmx.taskMonitor.threadpool.TrackingThreadPool;

/**
 *  TrackingThreadPool - 继承Java标准的ThreadPoolExecutor，增加额外的任务统计信息
 *  
 *  ThreadPoolStatusMBean - 对外暴露的接口，用于获取线程池的相关任务执行情况
 *  
 *  FetchTask - 任务类，访问外部URL，获取网页内容，提交给TrackingThreadPool执行
 *  
 *  本例的进一步扩展：
 *  	如果任务的重量级足够，那么甚至可以再进一步，在每个任务提交时都为它注册一个 MBean （然后在任务完成时再取消注册）。
 *  	然后可以用管理接口查询每个任务的当前状态、运行了多长时间，或者请求取消任务。
 *  
 *  参考：http://www.ibm.com/developerworks/cn/java/j-jtp09196/
 */

public class TestDriver {
	
	private static TrackingThreadPool trackingPool;
	public static void main(String[] args) throws Exception {
		
		// 线程池
		trackingPool = new TrackingThreadPool(
					10, 
					20, 
					3000, TimeUnit.SECONDS, 
					new LinkedBlockingQueue<Runnable>(50));
		
		init();
		
		startTask();
	}

	private static void init() throws Exception  {
		// 初始化MBeanServer
		ThreadPoolStatus status = new ThreadPoolStatus(trackingPool);
		//MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer(); // 不支持jconsole
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer(); // 支持jconsole
		ObjectName objName = ObjectName.getInstance("myapp:type=webserver,name=port 8088");
		
		// 将被托管MBean注册到MBServer中
		mBeanServer.registerMBean(status, objName);
	}

	private static void startTask() throws InterruptedException {
		int count = Integer.MAX_VALUE;
		String name = "白宫";
		String url = "https://www.whitehouse.gov/";
		while(--count > 0) {
			TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));;
			trackingPool.execute(new FetchTask(name, url));
		}
		System.out.println("task over!");
	}

}
