package clonegod.curator.watcher;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 
 * @author Administrator
 *
 */
public class CuratorPathChildrenCache {
	
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
					.build();
		
		//3 建立连接
		cf.start();
		
		//4 建立一个PathChildrenCache缓存,第三个参数为是否接受节点数据内容 如果为false则不接受
		/**
		 * 注意：
		 * 1、监控的path如果不存在，会自动创建
		 * 2、cacheData 必须设置为true才能监控到数据变化
		 * */
		PathChildrenCache cache = new PathChildrenCache(cf, "/super/child", true);
		//5 在初始化的时候就进行缓存监听
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			/**
			 * <B>方法名称：</B>监听子节点变更<BR>
			 * <B>概要说明：</B>新建、修改、删除<BR>
			 * @see org.apache.curator.framework.recipes.cache.PathChildrenCacheListener#childEvent(org.apache.curator.framework.CuratorFramework, org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent)
			 */
			@Override
			public void childEvent(CuratorFramework cf, PathChildrenCacheEvent event) throws Exception {
				switch (event.getType()) {
				case CHILD_ADDED:
					System.out.println("CHILD_ADDED :" + event.getData().getPath() 
							+ ", data=" + new String(event.getData().getData()));
					break;
				case CHILD_UPDATED:
					System.out.println("CHILD_UPDATED :" + event.getData().getPath()
							+ ", data=" + new String(event.getData().getData()));
					break;
				case CHILD_REMOVED:
					System.out.println("CHILD_REMOVED :" + event.getData().getPath()
							+ ", data=" + new String(event.getData().getData()));
					break;
				default:
					break;
				}
			}
		});
		
		//添加子节点
		Thread.sleep(1000);
		cf.create().forPath("/super/child/c1", "c1内容".getBytes());
		Thread.sleep(1000);
		cf.create().forPath("/super/child/c2", "c2内容".getBytes());
		
		// 子节点监听模式下，不支持创建孙节点的创建
		//cf.create().creatingParentsIfNeeded().forPath("/super/child/c2/c22", "c22内容".getBytes());
		
		//修改子节点
		Thread.sleep(1000);
		cf.setData().forPath("/super/child/c1", "c1更新内容".getBytes());
		
		//删除子节点
		Thread.sleep(1000);
		cf.delete().forPath("/super/child/c2");		
		
		//删除本身节点，不被监听
		Thread.sleep(1000);
		cf.delete().deletingChildrenIfNeeded().forPath("/super");
		
		Thread.sleep(1000);
		// 释放资源
		cache.close();
		cf.close();
	}
}
