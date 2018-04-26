package clonegod.zookeeper.serverlist;

import org.junit.Test;

public class ClientTest {
	
	@Test
	public void test() throws Exception {
		Client client = new Client();
		client.initZookeeper();
		
		Thread.sleep(9999);
	}
}
