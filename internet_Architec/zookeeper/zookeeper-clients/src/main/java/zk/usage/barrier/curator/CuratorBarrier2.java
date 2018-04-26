package zk.usage.barrier.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorBarrier2 {

	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;//ms 
	
	static DistributedBarrier barrier = null;
	
	public static void main(String[] args) throws Exception {
		
		for(int i = 0; i < 5; i++){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
						CuratorFramework cf = CuratorFrameworkFactory.builder()
									.connectString(CONNECT_ADDR)
									.sessionTimeoutMs(SESSION_OUTTIME)
									.retryPolicy(retryPolicy)
									.build();
						cf.start();
						
						/** 
						 * 分布式系统中的线程：同时启动任务的执行 
						 */
						barrier = new DistributedBarrier(cf, "/super");
						System.out.println(Thread.currentThread().getName() + "设置barrier!");
						barrier.setBarrier();	//设置
						// Blocks until the barrier node comes into existence
						barrier.waitOnBarrier(); //等待
						
						System.out.println("---------同时开始执行程序----------" + System.currentTimeMillis());
						System.out.println(Thread.currentThread().getName() + "执行任务");
						Thread.sleep((long) (Math.random() * 10000));
						
						System.out.println("---------不是同时结束----------"+ System.currentTimeMillis());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			},"t" + i).start();
		}

		Thread.sleep(5000);
		barrier.removeBarrier();	//释放
		
		
	}
}
