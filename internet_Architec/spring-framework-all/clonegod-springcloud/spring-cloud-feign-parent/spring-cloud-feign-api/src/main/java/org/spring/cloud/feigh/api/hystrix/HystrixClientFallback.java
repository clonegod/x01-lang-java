package org.spring.cloud.feigh.api.hystrix;

import java.util.Collection;
import java.util.Collections;

import org.spring.cloud.feigh.api.domain.Person;
import org.spring.cloud.feigh.api.service.PersonService;
import org.springframework.context.annotation.Configuration;

/**
 * 当Feign调用发生异常时，回退到该默认实现，返回默认值给服务调用方
 *
 */
@Configuration
public class HystrixClientFallback implements PersonService {

	@Override
	public boolean save(Person person) {
		System.err.println("-----------HystrixClientFallback on save ...");
		return false;
	}

	@Override
	public Collection<Person> findAll() {
		System.err.println("-----------HystrixClientFallback on findAll ...");
		return Collections.EMPTY_LIST;
	}

}
