package zk.usage.lock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 分布式系统中锁的应用
 * 模拟两个客户端并发修改USER的数据
 * 	- 使用zookeeper来实现分布式锁，控制多线程并发更新同一个用户的数据。
 * 
 * 该方案低效，不适用！
 * 
 */
public class ZookeeperLock {

	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	
	/** session超时时间 */
	static final int SESSION_TIMEOUT = 2000;//ms 
	
	static final User USER = new User("alice", 100);
	
	public static void main(String[] args) throws Exception{
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		executor.execute(new Client());
		executor.execute(new Client());
		
		executor.shutdown();
		
	}
	
	/**  客户端 */
	static class Client implements Runnable {
		final CountDownLatch connectedSemaphore = new CountDownLatch(1);
		ZooKeeper zk = null;
		public void run() {
			try {
				zk = new ZooKeeper(CONNECT_ADDR, SESSION_TIMEOUT, new Watcher(){
					@Override
					public void process(WatchedEvent event) {
						//获取事件的状态
						KeeperState keeperState = event.getState();
						EventType eventType = event.getType();
						//如果是建立连接
						if(KeeperState.SyncConnected == keeperState){
							if(EventType.None == eventType){
								//如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
								connectedSemaphore.countDown();
								System.out.println(Thread.currentThread().getName() + ": zk 建立连接成功");
							}
						}
					}
				});

				//进行阻塞
				connectedSemaphore.await();
				synchronized (ZookeeperLock.class) {
					if(zk.exists("/test", false) == null) {
						zk.create("/test", "test folder".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
					if(zk.exists("/test/lock", false) == null) {
						zk.create("/test/lock", "test/lock folder".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
				}
				while(true) {
					// 如果节点不存在，则尝试创建临时节点
					if(zk.exists("/test/lock/"+USER.getName(), false) == null) {
						try {
							// 创建临时节点 - 如果节点已存在，会抛异常
							System.out.println(Thread.currentThread().getName() + "尝试创建临时节点，time=" + System.currentTimeMillis());
							String ret = zk.create("/test/lock/"+USER.getName(), "user lock folder".getBytes(), 
									Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
							
							System.out.println(Thread.currentThread().getName() + "：创建临时节点成功---获取到分布式锁" + ret);
							USER.setBalance(USER.getBalance() + 100);
							System.out.println(USER);
							break;
						} catch (Exception e) {
							Thread.sleep(1000);
							System.err.println(Thread.currentThread().getName() + 
									": 临时节点已存在， 说明有其它客户端正在更新同一个用户的数据，等待1s后重试");
						}
					} else {
						Thread.sleep(1000); 
						System.err.println(Thread.currentThread().getName() + 
								": 临时节点已存在，等待1s后重试");
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					// 断开与zookeeper的连接，session失效，zookeeper上创建的临时节点被删除
					zk.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class User {
		public String name;
		public double balance;
		public User(String name, double balance) {
			super();
			this.name = name;
			this.balance = balance;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getBalance() {
			return balance;
		}
		public void setBalance(double balance) {
			this.balance = balance;
		}
		@Override
		public String toString() {
			return "User [name=" + name + ", balance=" + balance + "]";
		}
	}
	
}
