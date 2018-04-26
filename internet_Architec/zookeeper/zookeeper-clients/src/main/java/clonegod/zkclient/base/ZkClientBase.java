package clonegod.zkclient.base;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

public class ZkClientBase {

	/** zookeeper地址 */
	static final String CONNECT_ADDR = "192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;//ms 
	
	
	public static void main(String[] args) throws Exception {
		ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), 5000);
		//1. create and delete方法 
		zkc.createEphemeral("/temp"); /** 封装创建节点的类型 */
		zkc.createPersistent("/super/c1/c1-1/c1-1-1", true); /** 支持递归创建znode */
		
		Thread.sleep(6000);
		
		zkc.delete("/temp");
		zkc.deleteRecursive("/super"); /** 支持递归删除 */
		
		//2. 设置path和data 并且读取子节点和每个节点的内容
		zkc.createPersistent("/super", "1234"); /** 支持直接传入字符串 */
		zkc.createPersistent("/super/c1", "c1内容");
		zkc.createPersistent("/super/c2", "c2内容");
		List<String> list = zkc.getChildren("/super");
		for(String p : list){
			System.out.println("children: " + p);
			String rp = "/super/" + p;
			String data = zkc.readData(rp);
			System.out.println("节点为：" + rp + "，内容为: " + data);
		}
		
		//3. 更新和判断节点是否存在
		zkc.writeData("/super/c1", "c1新内容");
		String data = zkc.readData("/super/c1");
		System.out.println("value="+data);
		System.out.println(zkc.exists("/super/c1"));
		
		//4.递归删除/super内容
		zkc.deleteRecursive("/super");
		
		zkc.close();
	}
}
