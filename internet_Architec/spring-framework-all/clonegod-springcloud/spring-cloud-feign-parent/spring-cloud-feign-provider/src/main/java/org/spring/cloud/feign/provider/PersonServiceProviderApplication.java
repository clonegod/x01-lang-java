package org.spring.cloud.feign.provider;

import org.spring.cloud.feigh.api.web.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableEurekaClient
@Import(WebConfig.class)
public class PersonServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonServiceProviderApplication.class, args);
	}
}
