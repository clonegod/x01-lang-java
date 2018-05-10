package org.spring.cloud.feign.provider.web.controller;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.spring.cloud.feigh.api.domain.Person;
import org.spring.cloud.feigh.api.service.PersonService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Person Service - 服务提供方
 *	说明：
 *		使用Feign实现基于HTTP的RPC远程调用时，需要客户端与服务端都遵守统一的“接口契约”
 *		1、对客户端而言，必须实现相关接口声明，比如PersonService
 *		2、对服务提供方而言，则不是必须的。
 *			服务方可以实现接口的方式来提供契约一致性保证。（推荐）
 *			服务方也可以不实现接口，能保证两边的路由path能匹配上就行。
 */
@RestController
public class PersonServiceController implements PersonService {
	
	private static final Map<Long, Person> persons = new ConcurrentHashMap<>();
	
	// --- 小心：接口中参数前面的注解不能被继承下来，所以这里需要再次声明@RequestBody
	@Override
	public boolean save(@RequestBody Person person) {
		System.out.println("Invoke save...");
		return persons.putIfAbsent(person.getId(), person) == null;
	}

	@Override
	public Collection<Person> findAll() {
		System.out.println("Invoke findAll...");
		return persons.values();
	}

}
