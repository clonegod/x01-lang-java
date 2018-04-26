package clonegod.zookeeper.serverlist;

import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 应用启动后在zk的服务列表节点下创建一个临时的有序节点，维护服务的地址。
 * 	节点类型选择：EPHEMERAL_SEQUENTIAL，可实现服务宕机后自动从服务列表中移除。
 * 
 */
public class AppServer implements Runnable {
	
	// zk client instance
	private ZooKeeper zk;
	
	public AppServer() {
		init();
	}	
	
	
	/**
	 * 初始化zookeeper客户端
	 */
	private void init() {
		Watcher watcher = new Watcher(){
			public void process(WatchedEvent event) {
				System.out.println("Triiger event: " + event.getType().toString());
			}
		};
		
		try {
			zk = new ZooKeeper(Common.ZK_SERVER, 3000, watcher);
			while(zk.getState() != ZooKeeper.States.CONNECTED) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 注册服务地址到zk上
	 */
	public boolean registry() throws Exception {
		try {
			if(zk.exists(Common.ZNODE_ROOT, true) == null) {
				zk.create(Common.ZNODE_ROOT, "serverlist".getBytes(), 
						Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			// 子节点的类型设置为临时有序节点-EPHEMERAL_SEQUENTIAL，子节点的名称后面会自动加上一串自增的数字
			zk.create(Common.ZNODE_ROOT+"/server", 
					getServicePath().getBytes("UTF-8"), 
					Ids.OPEN_ACL_UNSAFE, 
					CreateMode.EPHEMERAL_SEQUENTIAL);
			return true;
			
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 模拟同一个服务的不同地址
	 */
	private String getServicePath() {
		int port = new Random().nextInt(1000) + 1024;
		String service = String.format("http://localhost:%d/api", port);
		System.out.println(Thread.currentThread().getName() + " registry: " + service);
		return service;
	}


	@Override
	public void run() {
		try {
			while(true) {
				registry();
				if(Math.random() > 0.7) {
					break;
				}
				Thread.sleep(1000);
			}
			System.out.println(Thread.currentThread().getName() + " exit.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				zk.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
