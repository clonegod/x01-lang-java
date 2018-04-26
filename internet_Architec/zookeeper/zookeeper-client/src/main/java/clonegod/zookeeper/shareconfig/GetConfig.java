package clonegod.zookeeper.shareconfig;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * 客户端监控znode，实时更新共享配置
 * 
 */
public class GetConfig implements Watcher{
	
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
			auth();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 认证
	 * @return
	 */
	private void auth() {
		zk.addAuthInfo(Common.AUTH_TYPE, Common.AUTH_PASSWORD.getBytes());
	}
	
	/**
	 * 当znode发生变化时，读取最新配置，更新SharedConfig
	 * 	
	 * @return 
	 */
	public ShareData reload() {
		try {
			String url = new String(zk.getData(Common.ZNODE_Url, true, null));
			String username = new String(zk.getData(Common.ZNODE_Username, true, null));
			String password = new String(zk.getData(Common.ZNODE_Password, true, null));
			ShareData cd = ShareData.getInstance().url(url).username(username).password(password);
			cd.printDatas();
			return cd;
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException(e);
		} 
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
				reload();
				break;
			case NodeChildrenChanged: 
				System.out.println("子节点更新成功！");
				reload();
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

}
