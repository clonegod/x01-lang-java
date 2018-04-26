package clonegod.curator.base;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

public class CuratorBase {
	
	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;//ms 
	
	public static void main(String[] args) throws Exception {
		
		//1 重试策略：初试时间为1s 衰减重试10次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
		
		//2 通过工厂创建连接
		CuratorFramework cf = CuratorFrameworkFactory.builder()
					.connectString(CONNECT_ADDR)
					.sessionTimeoutMs(SESSION_OUTTIME)
					.retryPolicy(retryPolicy)
//					.namespace("super")
					.build();
		//3 开启连接
		cf.start();
		
		System.out.println(States.CONNECTED);
		System.out.println(cf.getState());
		
		/** --------新加、删除 */
		//4 建立节点 指定节点类型（不加withMode默认为持久类型节点）、路径、数据内容
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c1","c1内容".getBytes());
		//5 删除节点
		cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
		
		/** --------读取、修改*/
		//创建节点
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c1","c1内容".getBytes());
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/super/c2","c2内容".getBytes());
		//读取节点
		Stat stat = new Stat();
		String ret1 = new String(cf.getData().storingStatIn(stat).forPath("/super/c2")); // 读取节点中存储的数据并将节点状态存储到给定的stat对象中
		System.out.println(ret1);
		System.out.println("stat=" + stat);
		//修改节点
		cf.setData().forPath("/super/c2", "修改c2内容".getBytes());
		String ret2 = new String(cf.getData().forPath("/super/c2"));
		System.out.println(ret2);	
		
		/** --------绑定回调函数 + 线程池处理异步结果 */
		ExecutorService pool = Executors.newCachedThreadPool(); // 处理异步回调结果的线程池
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
			.inBackground(new BackgroundCallback() {
				@Override
				public void processResult(CuratorFramework cf, CuratorEvent ce) throws Exception {
					System.out.println("code:" + ce.getResultCode());
					System.out.println("type:" + ce.getType());
					System.out.println("线程为:" + Thread.currentThread().getName());
				}
			}, pool)
			.forPath("/super/c3","c3内容".getBytes());
		
		Thread.sleep(2000);
		
		
		/**
		 * 事务功能---curator特有的功能
		 * 
		 */
		try {
			cf.inTransaction().delete().forPath("/super/c1") // 成功
				.and()
				.setData().forPath("/super/c99", "22222".getBytes()) // 失败，最终事务提交失败
				.and()
				.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// 读取子节点getChildren方法
		List<String> list = cf.getChildren().forPath("/super");
		list.forEach((x) -> System.out.println(x)); 
		
		// 判断节点是否存在checkExists方法
		stat = cf.checkExists().forPath("/super/c3");
		System.out.println(stat);
		
		Thread.sleep(1000);
		cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
		
		pool.shutdown();
		cf.close();
	}
}
