package com.clonegod.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true) // CGLIB
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
