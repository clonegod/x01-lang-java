package clonegod.zookeeper.serverlist;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 客户端监控znode，实时更新服务列表
 * 
 */
public class Client implements Watcher{
	
	// zk client instance
	private ZooKeeper zk;
	
	/**
	 * 创建zookeeper连接客户端
	 * 
	 */
	public void initZookeeper() {
		try {
			zk = new ZooKeeper(Common.ZK_SERVER, 3000, this);
			while(zk.getState() != ZooKeeper.States.CONNECTED) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		refreshServerList();
	}
	

	@Override
	public void process(WatchedEvent event) {
		switch(event.getType()) {
			case None: 
				System.out.println("连接zookeeper服务器成功！");
				break;
			case NodeCreated: 
				System.out.println("节点创建成功！");
				break;
			case NodeDataChanged: 
				System.out.println("节点更新成功！");
				break;
			case NodeChildrenChanged: 
				System.out.println("子节点更新成功！");
				refreshServerList();
				break;
			case NodeDeleted: 
				System.out.println("节点删除成功！");
				break;
		}
	}

	public void close() {
		try {
			zk.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	public void refreshServerList() {
		List<String> serviceList = null;
		try {
			serviceList = zk.getChildren(Common.ZNODE_ROOT, true);
			for(String ephNode : serviceList) {
				String address = new String(zk.getData(Common.ZNODE_ROOT + "/" + ephNode, false, new Stat()));
				System.out.println(address);
			}
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
