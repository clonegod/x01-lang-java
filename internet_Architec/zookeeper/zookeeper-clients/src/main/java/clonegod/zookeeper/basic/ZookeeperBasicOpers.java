package clonegod.zookeeper.basic;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperBasicOpers {

	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	
	/** session超时时间 */
	static final int SESSION_OUTTIME = 2000;//ms 
	
	/** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
	static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	public static void main(String[] args) throws Exception{
		
		ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher(){
			@Override
			public void process(WatchedEvent event) {
				// 连接状态
				KeeperState keeperState = event.getState();
				// 事件类型
				EventType eventType = event.getType();
				//如果是建立连接
				if(KeeperState.SyncConnected == keeperState){
					if(EventType.None == eventType){
						//如果建立连接成功，则发送信号量，让后续阻塞线程(主线程)向下执行
						connectedSemaphore.countDown();
						System.out.println("zk 建立连接成功");
					}
				}
			}
		});

		//进行阻塞---zookeeper客户端与服务器建立连接是异步的，因此需要等待连接成功后，才能调用其它的api接口进行操作！
		connectedSemaphore.await();
		
		/**-----------------------------------------连接已建立，开始对zookeeper进行增删改查操作----------------------------*/
		
		// -------删除节点
		// 判断节点是否存在 - 不存在，返回null；已存在，返回节点的状态数据
		if(zk.exists("/testRoot", false) != null) {
			if(zk.exists("/testRoot/children", false) != null) {
				zk.delete("/testRoot/children", -1);
			}
			zk.delete("/testRoot", -1); // -1 表示忽略版本号，对任意版本进行操作
		}
		
		String ret = "";
		
		// -------创建节点
		//创建父节点 - 如果节点已经存在，则抛异常
		ret = zk.create("/testRoot", "testRoot".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(ret);
		
		//创建子节点 ---- 必须要父节点已经存在的前提下，才能创建子节点。创建成功则返回节点完整路径，如果节点已经存在，则抛异常
		ret = zk.create("/testRoot/children", "children data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(ret);
		
		// -------查询节点信息
		//获取节点洗信息
		byte[] data = zk.getData("/testRoot", false, null);
		System.out.println(new String(data));
		
		// 获取子节点列表
		List<String> children = zk.getChildren("/testRoot", false);
		System.out.println(Arrays.toString(children.toArray()));
		
		// -------修改节点信息
		//修改节点的值
		zk.setData("/testRoot", "modify data root".getBytes(), -1);
		data = zk.getData("/testRoot", false, null);
		System.out.println(new String(data));
		
		/*
		//判断节点是否存在 - 不存在，返回null；已存在，返回节点的状态数据
		System.out.println(zk.exists("/testRoot/children", false));
		
		//删除节点
		zk.delete("/testRoot/children", -1);
		System.out.println(zk.exists("/testRoot/children", false));
		*/
		
		// -------关闭连接
		zk.close();
	}
	
}
