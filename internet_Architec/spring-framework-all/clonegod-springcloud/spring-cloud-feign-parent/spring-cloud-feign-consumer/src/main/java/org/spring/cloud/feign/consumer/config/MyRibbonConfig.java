package org.spring.cloud.feign.consumer.config;

import org.spring.cloud.feign.consumer.ribbon.rule.MyRibbonRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;

@Configuration
public class MyRibbonConfig {

	@Bean
	public IRule createMyRule() {
		return new MyRibbonRule();
	}
	
	
}
