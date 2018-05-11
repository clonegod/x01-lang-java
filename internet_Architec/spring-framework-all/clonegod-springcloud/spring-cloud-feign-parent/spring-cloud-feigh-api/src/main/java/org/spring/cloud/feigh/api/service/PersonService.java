package org.spring.cloud.feigh.api.service;

import java.util.Collection;

import org.spring.cloud.feigh.api.domain.Person;
import org.spring.cloud.feigh.api.hystrix.HystrixClientFallback;
import org.spring.cloud.feigh.api.hystrix.HystrixClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Annotation for interfaces declaring that: 
// 		a REST client with that interface should be created (e.g. for autowiring into another component). 
// value 配置为服务提供方在application.properties中所配置的应用名称 - spring.application.name
@FeignClient(value="person-service", 
//			fallback = HystrixClientFallback.class, 
			fallbackFactory=HystrixClientFallbackFactory.class)
public interface PersonService {

	/**
	 * 保存Person
	 * @param person
	 * @return	保存成功，返回true，否则false
	 */
	@PostMapping("/person/save")
	public boolean save(@RequestBody Person person);
	
	/**
	 * 获取所有Person的集合
	 * @return 
	 */
	@GetMapping("/person/list")
	public Collection<Person> findAll();
	
}
