package zk.usage.select.curator;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * Leader Election
 * 
 * 通过LeaderSelectorListener可以对领导权进行控制， 在适当的时候释放领导权，这样每个节点都有可能获得领导权。
 *
 */
public class LeaderSelectorTest {

    private static final String PATH = "/demo/leader";

    public static void main(String[] args) {

        List<LeaderSelector> selectors = new ArrayList<>();
        List<CuratorFramework> clients = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                CuratorFramework client = getClient();
                final String name = "client#" + i;
                LeaderSelector leaderSelector = new LeaderSelector(client, PATH, new LeaderSelectorListenerAdapter() {
					
					@Override
					public void takeLeadership(CuratorFramework client) throws Exception {
						System.out.println(name + ":I am leader.");
						Thread.sleep(2000);
					}
				});
                leaderSelector.autoRequeue();
                leaderSelector.start();
                
                clients.add(client);
                selectors.add(leaderSelector);
            }
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for(LeaderSelector selector : selectors){
                CloseableUtils.closeQuietly(selector);
            }
            
            for(CuratorFramework client : clients){
            	CloseableUtils.closeQuietly(client);
            }

        }
    }
    
    private final static String CONNECTSTRING="192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";
    
    private static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(CONNECTSTRING)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectionTimeoutMs(3000)
                .namespace("demo")
                .build();
        client.start();
        return client;
    }
}