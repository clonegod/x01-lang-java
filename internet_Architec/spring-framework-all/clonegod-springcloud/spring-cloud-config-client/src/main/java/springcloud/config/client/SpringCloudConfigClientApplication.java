package springcloud.config.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringCloudConfigClientApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpringCloudConfigClientApplication.class, args);
		
		SpringApplication application = new SpringApplication(SpringCloudConfigClientApplication.class);
		application.setWebEnvironment(true);
//		application.setBanner(new ImageBanner(image));
		application.run(args);
		
	}
}
