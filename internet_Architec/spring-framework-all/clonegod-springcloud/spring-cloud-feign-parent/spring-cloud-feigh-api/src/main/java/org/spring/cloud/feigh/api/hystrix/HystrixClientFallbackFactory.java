package org.spring.cloud.feigh.api.hystrix;

import java.util.Collection;
import java.util.Collections;

import org.spring.cloud.feigh.api.domain.Person;
import org.spring.cloud.feigh.api.service.PersonService;
import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class HystrixClientFallbackFactory implements FallbackFactory<PersonService> {
	@Override
	public PersonService create(Throwable cause) {
		return new PersonService() {
			@Override
			public boolean save(Person person) {
				System.out.println("fallback - save(Person person) ; reason was: " + cause.getMessage());
				return false;
			}

			@Override
			public Collection<Person> findAll() {
				System.out.println("fallback - findAll(); reason was: " + cause.getMessage());
				return Collections.EMPTY_LIST;
			}
		};
	}
}