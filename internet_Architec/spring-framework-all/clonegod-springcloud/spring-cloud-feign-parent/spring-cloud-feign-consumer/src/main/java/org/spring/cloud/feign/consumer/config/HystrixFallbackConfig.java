package org.spring.cloud.feign.consumer.config;

import org.spring.cloud.feigh.api.hystrix.HystrixClientFallback;
import org.spring.cloud.feigh.api.hystrix.HystrixClientFallbackFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 导入HystrixFallback相关的类，导入并发布为配置Bean
 */
@Import(value= {
		HystrixClientFallback.class,
		HystrixClientFallbackFactory.class
})
@Configuration
public class HystrixFallbackConfig {
	
}
