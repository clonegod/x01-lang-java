package clonegod.zookeeper.shareconfig;

import org.junit.Test;

public class TestSetConfig {
	
	/**
	 * 向zookeeper写入数据
	 */
	@Test
	public void test() throws Exception {
		SetConfig sc = new SetConfig();
		sc.createZookeeper().save();
	}
	
}
