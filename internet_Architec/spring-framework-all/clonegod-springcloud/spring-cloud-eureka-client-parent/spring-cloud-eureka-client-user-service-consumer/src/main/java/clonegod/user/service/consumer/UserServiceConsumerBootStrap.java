package clonegod.user.service.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceConsumerBootStrap {

	/**
	 * 用户服务消费端 引导类
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserServiceConsumerBootStrap.class, args);
	}
	
	
	/**
	 * RestTemplate 标记了@LoadBalanced注解后，将扩展为具有负载均衡效果的客户端调用
	 */
	// Annotation to mark a RestTemplate bean to be configured to use a LoadBalancerClient
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
