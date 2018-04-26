package clonegod.zkclient.watcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

public class ZkClientSubscribeChildChanges {

	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;//ms 
	
	
	public static void main(String[] args) throws Exception {
		ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 5000);
		
		/** zkclient对事件监听按不同类型进行了封装 */
		//对父节点添加监听子节点变化。
		zkc.subscribeChildChanges("/super", new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println("parentPath: " + parentPath + ", exists=" + zkc.exists(parentPath));
				System.out.println("currentChilds: " + currentChilds);
			}
		});
		
		zkc.createPersistent("/super");
		Thread.sleep(1000);
		
		zkc.createPersistent("/super" + "/" + "c1", "c1内容");
		Thread.sleep(1000);
		
		zkc.createPersistent("/super" + "/" + "c2", "c2内容");
		Thread.sleep(1000);		
		
		zkc.writeData("/super" + "/" + "c2", "c2新内容");
		Thread.sleep(1000);		
		
		zkc.delete("/super/c2");
		Thread.sleep(1000);	
		
		zkc.deleteRecursive("/super");
		
		TimeUnit.SECONDS.sleep(1);
		
		zkc.close();
	}
}
