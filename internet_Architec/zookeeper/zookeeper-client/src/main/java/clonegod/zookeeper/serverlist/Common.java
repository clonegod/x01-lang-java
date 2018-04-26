package clonegod.zookeeper.serverlist;

public class Common {
	
	/** zookeeper服务器地址 */
	public static final String ZK_SERVER = "192.168.1.201:2181,"
											+ "192.168.1.202:2181,"
											+ "192.168.1.203:2181";
	
	
	/** 保存到zookeeper中的系统配置 */
	public static final String ZNODE_ROOT 		= 		"/test/app/serverlist";
	
}
