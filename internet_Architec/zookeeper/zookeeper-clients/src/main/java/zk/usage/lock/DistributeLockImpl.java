package zk.usage.lock;

import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 使用zookeeper原生api实现一个简化版的分布式锁！
 *	- 注意：生产环境建议使用curator提供的分布锁。
 */
public class DistributeLockImpl {
	/** zookeeper地址 */
	private static final String CONNECT_ADDR = "192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	/** session超时时间 */
	private static final int SESSION_TIMEOUT = 5000;//ms 
	
	private static final String ROOT_LOCKS = "/LOCKS";
	private String lockID;

	private ZooKeeper zk;
	private CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private CountDownLatch lockCountDownLatch = new CountDownLatch(1);
	
	public DistributeLockImpl() {
		try {
			zk = new ZooKeeper(CONNECT_ADDR, SESSION_TIMEOUT, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					if(KeeperState.SyncConnected == event.getState()) {
						if(EventType.None == event.getType()){
							//如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
							connectedSemaphore.countDown();
							System.out.println(Thread.currentThread().getName() + ": zk 建立连接成功");
						}
					}
				}
			});
			connectedSemaphore.await();
			
			// 创建LOCKS根节点
			synchronized (DistributeLockImpl.class) {
				if(zk.exists(ROOT_LOCKS, false) != null) {
					zk.delete(ROOT_LOCKS, -1);
				}
				zk.create(ROOT_LOCKS, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean lock() {
		
		try {
			// LOCKS/0000000001
			lockID= zk.create(ROOT_LOCKS+"/", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println(Thread.currentThread().getName() + "->成功创建了lock节点[" + lockID + "], 开始竞争锁");
			
			List<String> childrenNodes = zk.getChildren(ROOT_LOCKS, true);
			// 排序
			SortedSet<String> sortedNodes = new TreeSet<>();
			childrenNodes.forEach((child) -> sortedNodes.add(ROOT_LOCKS + "/" + child));
			
			String firstNode = sortedNodes.first();
			if(lockID.equals(firstNode)) {
				// 表示当前节点就是最小节点
				System.out.println(Thread.currentThread().getName() + "->成功获取到锁，lock节点为["+lockID+"]");
				return true;
			}
			
			SortedSet<String> preNodes = sortedNodes.headSet(lockID);
			if(! preNodes.isEmpty()) {
				String preLockID = preNodes.last();
				zk.exists(preLockID, new LockWatch(lockCountDownLatch)); // 监控相邻的前一个节点
				lockCountDownLatch.await(SESSION_TIMEOUT, TimeUnit.MILLISECONDS);
				// 下面两种情况，都认为客户端成功获取到锁：
				// 1. 等待会话超时---暗示前面节点锁定资源的时间不应该超过SESSION_OUTTIME的值
				// 2. 前面的节点被释放
				System.err.println(Thread.currentThread().getName() + "->成功获取锁：[" + lockID + "]" );
			}
			
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean unlock() {
		System.out.println("\t"+Thread.currentThread().getName() + "->开始释放锁：[" + lockID + "]");
		try {
			zk.delete(lockID, -1);
			System.out.println("\t"+Thread.currentThread().getName() + "->成功删除节点：[" + lockID + "]");
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	private static class LockWatch implements Watcher {
		private CountDownLatch latch;
		
		public LockWatch(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void process(WatchedEvent event) {
			if(event.getType() == EventType.NodeDeleted) {
				latch.countDown();
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(10);
		Random rand = new Random();
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for(int i=0; i<10; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					DistributeLockImpl lock = null;
					try {
						lock = new DistributeLockImpl();
						
						latch.countDown();
						latch.await(); // 等待所有线程一起执行
						
						lock.lock();
						Thread.sleep(rand.nextInt(50));
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if(lock != null) {
							lock.unlock();
						}
					}
				}
			});
		}
		
		executor.awaitTermination(SESSION_TIMEOUT, TimeUnit.MICROSECONDS);
		executor.shutdown();
	}
	
}
