package org.spring.cloud.feign.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PersonServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonServiceProviderApplication.class, args);
	}
}
