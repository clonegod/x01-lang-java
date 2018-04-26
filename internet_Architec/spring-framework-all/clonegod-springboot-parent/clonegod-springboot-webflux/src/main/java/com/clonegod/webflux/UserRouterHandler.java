package com.clonegod.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.clonegod.api.User;
import com.clonegod.respository.UserRepository2;

import reactor.core.publisher.Mono;

@Configuration
public class UserRouterHandler {

	private final UserRepository2 userRepository;

	@Autowired
	public UserRouterHandler(UserRepository2 userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	/**
	 * 在 Spring Web MVC 中 使用@RequestBody
	 * 在 Spring Web Flux 中 使用ServerRequest
	 */
	public Mono<ServerResponse> save(ServerRequest serverRequest) {
		// Mono<User> 表示 0-1个值，类似与Java8中的Optional<User>
		// Flux<User> 表示 0-N个值，类似java中的List<User>
		Mono<User> userMono = serverRequest.bodyToMono(User.class);
		
		Mono<Boolean> boolMono = userMono.map(userRepository::save);
		
		System.out.printf("[Thread: %s] UserRouterHandler starts saving user\n", 
        		Thread.currentThread().getName());
		
		return ServerResponse.ok().body(boolMono, Boolean.class);
	}
	
}
