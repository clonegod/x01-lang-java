package org.spring.cloud.feign.consumer.ribbon.rule;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

public class MyRibbonRule extends AbstractLoadBalancerRule {

	ConcurrentHashMap<String, Server> serverMapping = new ConcurrentHashMap<>();
	
	Random rand;

    public MyRibbonRule() {
        rand = new Random();
    }

    /**
     * Randomly choose from all living servers
     * 
     * key - 可以在自定义Filter中，将业务字段作为key，放到RequestContext，之后就能从choose方法中取到该业务字段值。再根据这个值来选择负载均衡的server。
     */
    // If you put any object into the RequestContext with a key FilterConstants.LOAD_BALANCER_KEY, 
    // it will be passed to the choose method of IRule implementation. 
    public Server choose(ILoadBalancer lb, Object key) {
    	System.out.printf("MyRibbonRule / choose: %s \n", Thread.currentThread().getName());
    	
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                /*
                 * No servers. End regardless of pass, because subsequent passes
                 * only get more restrictive.
                 */
                return null;
            }

            int index = rand.nextInt(serverCount);
            server = upList.get(index);

            if (server == null) {
                /*
                 * The only time this should happen is if the server list were
                 * somehow trimmed. This is a transient condition. Retry after
                 * yielding.
                 */
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }

	@Override
	public Server choose(Object key) {
		return choose(getLoadBalancer(), key);
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
		// TODO Auto-generated method stub
		
	}

}
