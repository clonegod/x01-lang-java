package com.clonegod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
		// 可配置更多选项
//		SpringApplication application = new SpringApplication(Application.class);
//		application.addInitializers(initializers);
//		application.addListeners(listeners);
//		application.setAddCommandLineProperties(addCommandLineProperties);
//		application.run(args);
		
	}
	
}
