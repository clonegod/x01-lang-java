package clonegod.zookeeper.shareconfig;

import org.junit.Test;

public class TestGetConfig {
	/**
	 * 从zookeeper读取数据
	 * 	zkCli访问需要认证： [zk: localhost:2181(CONNECTED) 1] addauth digest 123456
	 */
	@Test
	public void test() throws Exception {
		GetConfig client = new GetConfig();
		client.initZookeeper();
		client.reload();
		
		Thread.sleep(9999999);
		
		client.close();
	}
	
}
