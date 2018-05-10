package org.spring.cloud.feign.consumer.web.controller;

import java.util.Collection;

import org.spring.cloud.feigh.api.domain.Person;
import org.spring.cloud.feigh.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Person Client - Controller 
 *
 */
@RestController
public class PersonClientController implements PersonService {
	
	// PersonService 被标记为@FeignClient，springcloud将会为其创建一个Feign的代理对象
	private PersonService personService;
	
	@Autowired
	public PersonClientController(PersonService personService) {
		super();
		this.personService = personService;
	}

	// --- 小心：接口中参数前面的注解不能被继承下来，所以这里需要再次声明@RequestBody
	@Override
	public boolean save(@RequestBody Person person) {
		return personService.save(person);
	}

	@Override
	public Collection<Person> findAll() {
		return personService.findAll();
	}

	
}
