package clonegod.zookeeper.shareconfig;

public class Common {
	
	/** zookeeper服务器地址 */
	public static final String ZK_SERVER = "192.168.1.201:2181,"
											+ "192.168.1.202:2181,"
											+ "192.168.1.203:2181";
	
	/** 连接zookeeper使用的认证方式 */
	public static final String AUTH_TYPE = "digest";
	public static final String AUTH_PASSWORD = "123456";
	
	
	/** 保存到zookeeper中的系统配置 */
	public static final String ZNODE_ROOT 		= 		"/test/app/conf";
	public static final String ZNODE_Url 		= 		ZNODE_ROOT+"/url";
	public static final String ZNODE_Username 	= 		ZNODE_ROOT+"/username";
	public static final String ZNODE_Password 	= 		ZNODE_ROOT+"/password";
	
}
