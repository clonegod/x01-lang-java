package clonegod.zookeeper.locks;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 基于EPHEMERAL_SEQUENTIAL类型的特点，实现分布式锁的功能：
 * 	分布式环境下多线程对共享资源的有序访问，基于事件通知的机制（异步）。
 * -> client将按创建ephemeral节点的先后顺序获得对应的编号，编号靠前的先执行。
 */
public class DistributeLock implements Watcher, Runnable {
	/** zookeeper服务器地址 */
	public static final String ZK_SERVER = "192.168.1.201:2181,"
											+ "192.168.1.202:2181,"
											+ "192.168.1.203:2181";
	
	
	/** 保存到zookeeper中的系统配置 */
	public static final String ZNODE_ROOT 		= 		"/test/app/locks";
	
	private static final int SESSION_TIMEOUT = 3000;  
	
	// zk client instance
	private ZooKeeper zk;
	
	private String thisPath;
	private String waitPath;
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	@Override
	public void process(WatchedEvent event) {
		if(event.getState() == KeeperState.SyncConnected) {
			System.out.println(Thread.currentThread().getName() + " 连接zk成功！");
			latch.countDown();
		}
		// 发生了waitPath的删除事件  
        if (event.getType() == EventType.NodeDeleted && event.getPath().equals(waitPath)) {
        	checkLockSuccess();
        }
	}
	
	@Override
	public void run() {
		initZookeeper();
	}
	
	/**
	 * 创建zookeeper连接客户端
	 * 
	 */
	public void initZookeeper() {
		try {
			zk = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, this);
			
			latch.await();
			
			createMyPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * 创建EPHEMERAL_SEQUENTIAL类型的节点
	 */
	private void createMyPath() throws Exception {
		if(zk.exists(ZNODE_ROOT, true) == null) {
			zk.create(ZNODE_ROOT, "locks".getBytes(), 
					Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
		thisPath = zk.create(ZNODE_ROOT + "/subNode", null, 
				Ids.OPEN_ACL_UNSAFE,  
                CreateMode.EPHEMERAL_SEQUENTIAL);
		
		List<String> childrenNodes = zk.getChildren(ZNODE_ROOT, false);  
		Collections.sort(childrenNodes);  
		  
		String thisNode = thisPath.substring((ZNODE_ROOT + "/").length());
		int index = childrenNodes.indexOf(thisNode);  
		if (index == 0) {  // only your self waiting for locks
			getLockSuccess();
		} else {  
			// 获得排名比thisPath前1位的节点  
			this.waitPath = ZNODE_ROOT + "/" + childrenNodes.get(index - 1);  
			// 在waitPath上注册监听器, 当waitPath被删除时, zookeeper会回调监听器的process方法  
			zk.getData(waitPath, true, new Stat());
		}  
	}

	/**
	 * 再次确认被删除的节点是否为第一个节点：
	 * 假设某个client在获得锁之前挂掉了（非第1个节点）, 
	 * 由于client创建的节点是ephemeral类型的, 
	 * 因此这个节点也会被删除, 
	 * 从而导致排在这个client之后的client提前获得了锁. 
	 * 此时会存在多个client同时访问共享资源.
	 */
	private void checkLockSuccess() {
		try {
			// 确认thisPath是否真的是列表中的最小节点  
			List<String> childrenNodes = zk.getChildren(ZNODE_ROOT, false);  
			Collections.sort(childrenNodes);  
			String thisNode = thisPath.substring((ZNODE_ROOT + "/").length());  
			int index = childrenNodes.indexOf(thisNode);  
			if (index == 0) {
				getLockSuccess();  
			} else {  
			    // 说明waitPath是由于出现异常而挂掉的，这里需要设置新的waitPath  
			    waitPath = ZNODE_ROOT + "/" + childrenNodes.get(index - 1);
			    // 重新注册监听, 并判断此时waitPath是否已删除  
			    if (zk.exists(waitPath, true) == null) {  
			    	getLockSuccess();
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getLockSuccess() {
		try {  
            System.out.println(Thread.currentThread().getName() + ": gain lock: " + thisPath);  
            processShareResource();
        } finally {  
            System.out.println(Thread.currentThread().getName() + ": 处理共享资源 finished: " + thisPath);  
            try {
				zk.delete(this.thisPath, -1); // 释放锁：将thisPath删除, 监听thisPath的client将获得通知  
			} catch (InterruptedException | KeeperException e) {
				e.printStackTrace();
			}
        }  
	}


	/**
	 * 模拟处理共享资源的时间开销
	 */
	private void processShareResource() {
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void close() {
		try {
			zk.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
