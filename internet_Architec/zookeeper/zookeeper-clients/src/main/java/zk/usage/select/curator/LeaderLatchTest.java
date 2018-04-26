package zk.usage.select.curator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

/**
 * Leader Latch
 * 
 * 随机从候选着中选出一台作为leader，选中之后除非调用close()释放leadship，否则其他的后选择无法成为leader。其中spark使用的就是这种方法。
 * 
 */
public class LeaderLatchTest {

    private static final String PATH = "/demo/leader";

    public static void main(String[] args) {

        List<LeaderLatch> latchList = new ArrayList<>();
        List<CuratorFramework> clients = new ArrayList<>();
        try {
        	Random rand = new Random();
        	// 启动了10个client，程序会随机选中其中一个作为leader。通过注册监听的方式来判断自己是否成为leader。调用close()方法释放当前领导权。
            for (int i = 0; i < 10; i++) {
                CuratorFramework client = getClient();
                clients.add(client);

                final LeaderLatch leaderLatch = new LeaderLatch(client, PATH, "client#" + i);
                leaderLatch.addListener(new LeaderLatchListener() {
                    @Override
                    public void isLeader() {
                        System.out.println(leaderLatch.getId() +  ":I am leader. I am doing jobs!");
                        try {
                        	// 自动释放leader角色
                        	TimeUnit.SECONDS.sleep(rand.nextInt(5));
							leaderLatch.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
                    }

                    @Override
                    public void notLeader() {
                        System.out.println(leaderLatch.getId() +  ":I am not leader. I will do nothing!");
                    }
                });
                latchList.add(leaderLatch);
                leaderLatch.start();
            }
            
            // 选举10秒后结束
            TimeUnit.SECONDS.sleep(10);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	for(LeaderLatch leaderLatch : latchList){
        		CloseableUtils.closeQuietly(leaderLatch);
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