package clonegod.curator.cluster;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class TestSetConfigs {

	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;//ms 
	
	static final String PARENT_PATH = "/configs";
	
	public static void main(String[] args) throws Exception {
		
		//1 重试策略：初试时间为1s 重试10次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
		
		//2 通过工厂创建连接
		CuratorFramework cf = CuratorFrameworkFactory.builder()
					.connectString(CONNECT_ADDR)
					.sessionTimeoutMs(SESSION_OUTTIME)
					.retryPolicy(retryPolicy)
					.build();
		//3 开启连接
		cf.start();

		//4 创建节点
		if(cf.checkExists().forPath(PARENT_PATH) == null) {
			cf.create().withMode(CreateMode.PERSISTENT).forPath(PARENT_PATH);
		}
		
		Thread.sleep(1000);
		if(cf.checkExists().forPath(PARENT_PATH + "/server1") == null) {
			cf.create().creatingParentsIfNeeded()
			.withMode(CreateMode.PERSISTENT)
			.forPath(PARENT_PATH + "/server1", "http://io.app1".getBytes());
		}
		
		Thread.sleep(1000);
		if(cf.checkExists().forPath(PARENT_PATH + "/server2") == null) {
			cf.create().creatingParentsIfNeeded()
			.withMode(CreateMode.PERSISTENT)
			.forPath(PARENT_PATH + "/server2", "http://io.app2".getBytes());
		}
		
		//6 修改节点
		Thread.sleep(1000);
		cf.setData().forPath(PARENT_PATH + "/server1", "http://io.app1/V5".getBytes());
		String ret3 = new String(cf.getData().forPath(PARENT_PATH + "/server1"));
		System.out.println(ret3);
		
		Thread.sleep(1000);
		cf.setData().forPath(PARENT_PATH + "/server2", "http://io.app2/V5".getBytes());
		String ret4 = new String(cf.getData().forPath(PARENT_PATH + "/server2"));
		System.out.println(ret4);
		
	}
}
