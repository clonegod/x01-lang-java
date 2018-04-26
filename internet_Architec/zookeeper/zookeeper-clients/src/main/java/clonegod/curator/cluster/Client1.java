package clonegod.curator.cluster;

/**
 * 客户端1 监听zookeeper上/configs 的子节点
 *
 */
public class Client1 {

	public static void main(String[] args) throws Exception{
		CuratorWatcher watcher = new CuratorWatcher();
		
		System.in.read();
		
		watcher.close();
	}
}
