package clonegod.springcloud.configclient.git;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import clonegod.springcloud.configclient.git.health.MyApplicationHealthIndicator;

@SpringBootApplication
public class SpringCloudConfigClientGitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigClientGitApplication.class, args);
	}
	
	@Bean
	public MyApplicationHealthIndicator healthIndicator() {
		return new MyApplicationHealthIndicator();
	}
	
}
