package zk.usage.select.curator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import com.google.common.collect.Lists;

/**
 * Leader Election
 * 
 * 通过LeaderSelectorListener可以对领导权进行控制， 在适当的时候释放领导权，这样每个节点都有可能获得领导权。
 *
 */
public class CuratorMasterSelector {
	
	private final static String CONNECTSTRING="192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
	
	private final static String LEADER_PATH = "/demo/leader";
	
	public static void main(String[] args) throws InterruptedException {
		List<CuratorFramework> clients = Lists.newArrayList();
		List<LeaderSelector> selectors = Lists.newArrayList();
		
		try {
			for(int i=0; i<10; i++) {
				CuratorFramework client = CuratorFrameworkFactory.builder()
						.connectString(CONNECTSTRING)
						.retryPolicy(new ExponentialBackoffRetry(1000, 3))
						.sessionTimeoutMs(6000)
						.connectionTimeoutMs(3000)
						.namespace("select-master")
						.build();
				client.start();
				
				final String clientId =  "client"+i;
				LeaderSelector selector = new LeaderSelector(client, LEADER_PATH, new LeaderSelectorListenerAdapter() {
					@Override
					public void takeLeadership(CuratorFramework client) throws Exception {
						System.out.println(clientId+": i am leader");
						TimeUnit.SECONDS.sleep(3);
					}
				});
				
				selector.autoRequeue(); // 确保此实例在释放领导权后还可能获得领导权。
				selector.start();
				System.out.println(clientId + ": start select");
				
				clients.add(client);
				selectors.add(selector);
			}
			
			// 选举10s
			TimeUnit.SECONDS.sleep(10);
		} finally {
			selectors.forEach(x -> CloseableUtils.closeQuietly(x));
			clients.forEach(x -> CloseableUtils.closeQuietly(x));
		}
	}
}
