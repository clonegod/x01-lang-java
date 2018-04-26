package clonegod.zookeeper.watcher;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * 测试各种事件类型的触发
 * 
 */
public class ZooKeeperWatcher2 implements Watcher {

	/** 定义原子变量 */
	AtomicInteger seq = new AtomicInteger();
	/** 定义session失效时间 */
	private static final int SESSION_TIMEOUT = 10000;
	/** zookeeper服务器地址 */
	private static final String CONNECTION_ADDR = "192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	/** zk父路径设置 */
	private static final String PARENT_PATH = "/p";
	/** zk子路径设置 */
	private static final String CHILDREN_PATH = "/p/c";
	/** 进入标识 */
	private static final String LOG_PREFIX_OF_MAIN = "【Main】";
	/** zk变量 */
	private ZooKeeper zk = null;
	/** 信号量设置，用于等待zookeeper连接建立之后 通知阻塞程序继续向下执行 */
	private CountDownLatch connectedSemaphore = new CountDownLatch(1);

	/**
	 * 创建ZK连接
	 * @param connectAddr ZK服务器地址列表
	 * @param sessionTimeout Session超时时间
	 */
	public void createConnection(String connectAddr, int sessionTimeout) {
		try {
			zk = new ZooKeeper(connectAddr, sessionTimeout, this);
			System.out.println(LOG_PREFIX_OF_MAIN + "开始连接ZK服务器");
			connectedSemaphore.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 收到来自Server的Watcher通知后的处理。
	 */
	@Override
	public void process(WatchedEvent event) {
		
		System.out.println("进入 process 。。。。。event = " + event);
		
		if (event == null) {
			return;
		}
		
		// 连接状态
		KeeperState keeperState = event.getState();
		// 事件类型
		EventType eventType = event.getType();
		// 受影响的path
		String path = event.getPath();
		
		String logPrefix = "【Watcher-" + this.seq.incrementAndGet() + "】";

		System.out.println(logPrefix + "收到Watcher通知");
		System.out.println(logPrefix + "连接状态:\t" + keeperState.toString());
		System.out.println(logPrefix + "事件类型:\t" + eventType.toString());

		if (KeeperState.SyncConnected == keeperState) {
			// 成功连接上ZK服务器
			if (EventType.None == eventType) {
				System.out.println(logPrefix + "成功连接上ZK服务器");
				connectedSemaphore.countDown();
			} 
			//创建节点
			else if (EventType.NodeCreated == eventType) {
				System.out.println(logPrefix + "节点创建");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			//更新节点
			else if (EventType.NodeDataChanged == eventType) {
				System.out.println(logPrefix + "节点数据更新");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			//更新子节点
			else if (EventType.NodeChildrenChanged == eventType) {
				System.out.println(logPrefix + "子节点变更（只通知子节点发生了变化，具体是新增还是删除事件则是不确定的）");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
			//删除节点
			else if (EventType.NodeDeleted == eventType) {
				System.out.println(logPrefix + "节点 " + path + " 被删除");
			}
			else ;
		} 
		else if (KeeperState.Disconnected == keeperState) {
			System.out.println(logPrefix + "与ZK服务器断开连接");
		} 
		else if (KeeperState.AuthFailed == keeperState) {
			System.out.println(logPrefix + "权限检查失败");
		} 
		else if (KeeperState.Expired == keeperState) {
			System.out.println(logPrefix + "会话失效");
		}
		else ;

		System.out.println("--------------------------------------------");

	}

	/**
	 * <B>方法名称：</B>测试zookeeper监控<BR>
	 * <B>概要说明：</B>主要测试watch功能<BR>
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		//建立watcher
		ZooKeeperWatcher2 instance = new ZooKeeperWatcher2();
		//创建连接
		instance.createConnection(CONNECTION_ADDR, SESSION_TIMEOUT);
		//System.out.println(zkWatch.zk.toString());
		
		// 清除历史数据
		if(instance.zk.exists(PARENT_PATH, false) != null) {
			instance.zk.delete(PARENT_PATH, -1);
		}

		// ---------------------------- 父节点：创建、修改
		// 注册监听
		instance.zk.exists(PARENT_PATH, true);
//		instance.zk.getData(PARENT_PATH, true, null); // 对于未创建的节点，不能使用getData设置监听
		// type:NodeCreated path:/p
		instance.zk.create(PARENT_PATH, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		// 注册监听
//		instance.zk.exists(PARENT_PATH, true);
		instance.zk.getData(PARENT_PATH, true, null);
		// type:NodeDataChanged path:/p
		instance.zk.setData(PARENT_PATH, "parent".getBytes(), -1);
		
		// ---------------------------- 子节点：创建、修改、删除
		/** 要监听子节点的变化，必须通过getChildren来注册监听 */
		/* 下面两种方式，不能用来注册监听子节点的变更，需要使用getChildren才能监听到子节点的变化！
		instance.zk.getData(PARENT_PATH, true, null);
		instance.zk.exists(PARENT_PATH, true);
		*/
		// 注册监听
		instance.zk.getChildren(PARENT_PATH, true);
		// type:NodeChildrenChanged path:/p
		instance.zk.create(CHILDREN_PATH, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		// 注册监听
		instance.zk.getChildren(PARENT_PATH, true);
		// 子节点数据变化不会触发父节点的监听事件
		instance.zk.setData(CHILDREN_PATH, "child".getBytes(), -1);
		
		// 注册监听
		instance.zk.getChildren(PARENT_PATH, true);
		// type:NodeChildrenChanged path:/p
		instance.zk.delete(CHILDREN_PATH, -1);
		
		// --- 删除根节点
		// 注册监听
		//instance.zk.exists(PARENT_PATH, true);
		instance.zk.getData(PARENT_PATH, true, null);
		// type:NodeDataChanged path:/p
		instance.zk.delete(PARENT_PATH, -1);

		TimeUnit.SECONDS.sleep(2);
	}

}