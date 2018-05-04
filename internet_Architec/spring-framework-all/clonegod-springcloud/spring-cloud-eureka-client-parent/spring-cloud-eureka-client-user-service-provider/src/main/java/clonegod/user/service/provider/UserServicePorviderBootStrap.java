package clonegod.user.service.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@SpringCloudApplication

// 等效于

//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker

@SpringBootApplication
@EnableDiscoveryClient
public class UserServicePorviderBootStrap {
	/**
	 * 用户服务提供端 引导类
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserServicePorviderBootStrap.class, args);
	}
}
