package com.asynclife.clonegod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class App {
	
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication();
		app.setWebEnvironment(true);
		SpringApplication.run(App.class, args);
		
		System.out.println("App start running...");
	}
}
