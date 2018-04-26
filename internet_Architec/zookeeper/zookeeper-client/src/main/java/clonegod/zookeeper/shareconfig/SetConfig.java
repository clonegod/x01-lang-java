package clonegod.zookeeper.shareconfig;

import java.util.HashMap;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
/**
 * 添加共享配置到zookeeper
 * 	1. path: /test/app/conf
 *	2. 需要认证才能读取该znode
 */
public class SetConfig {
	
	// zk client instance
	private ZooKeeper zk;
	
	/**
	 * 创建zookeeper连接客户端
	 * 
	 * @return
	 */
	public SetConfig createZookeeper() {
		Watcher watcher = new Watcher(){
			public void process(WatchedEvent event) {
				System.out.println("Triiger event: " + event.getType().toString());
			}
		};
		
		try {
			zk = new ZooKeeper(Common.ZK_SERVER, 3000, watcher);
			while(zk.getState() != ZooKeeper.States.CONNECTED) {
				Thread.sleep(3000);
			}
			addAuth();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		return this;
	}
	
	/**
	 * 配置认证信息（可选）
	 * @return
	 */
	private void addAuth() {
		zk.addAuthInfo(Common.AUTH_TYPE, Common.AUTH_PASSWORD.getBytes());
	}
	
	// 需要被保存到zookeeper作为共享配置的数据
	private static Map<String,String> sysConfig = new HashMap<String,String>();
	static {
		sysConfig.put(Common.ZNODE_Url, "http://192.168.1.102:9000/api");
		sysConfig.put(Common.ZNODE_Username, "scott");
		sysConfig.put(Common.ZNODE_Password, "tiger");
	}
	
	/**
	 * 保存系统配置数据
	 * Ids.CREATOR_ALL_ACL		该znode需要认证后才能进行操作（读，改，删）
	 * CreateMode.PERSISTENT	持久znode
	 */
	public boolean save() {
		try {
			if(zk.exists(Common.ZNODE_ROOT, true) == null) {
				zk.create(Common.ZNODE_ROOT, "root".getBytes(), 
						Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			
			if(zk.exists(Common.ZNODE_Url, true) == null) {
				zk.create(Common.ZNODE_Url, sysConfig.get(Common.ZNODE_Url).getBytes(), 
						Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			
			if(zk.exists(Common.ZNODE_Username, true) == null) {
				zk.create(Common.ZNODE_Username, sysConfig.get(Common.ZNODE_Username).getBytes(), 
						Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			
			if(zk.exists(Common.ZNODE_Password, true) == null) {
				zk.create(Common.ZNODE_Password, sysConfig.get(Common.ZNODE_Password).getBytes(), 
						Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
			}
			
			return true;
			
		} catch (KeeperException | InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				zk.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new SetConfig().createZookeeper().save();
	}
}
