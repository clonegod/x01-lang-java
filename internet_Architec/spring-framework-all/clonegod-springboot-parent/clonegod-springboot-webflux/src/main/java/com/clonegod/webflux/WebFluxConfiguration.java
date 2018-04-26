package com.clonegod.webflux;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.clonegod.api.User;
import com.clonegod.service.UserService;

import reactor.core.publisher.Flux;


//使用  Web Flux

@Configuration
public class WebFluxConfiguration {
	
	private final UserService userService;
	
	@Autowired
	public WebFluxConfiguration(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 函数式路由映射 - RouterFunctionMapping
	 */
    @Bean
    public RouterFunction<ServerResponse> routerFunctionAllUsers(){
    	// 初始化Bean就会执行下面的逻辑
        Collection<User> users = userService.findAll();
        Flux<User> userFlux = Flux.fromIterable(users);
//        Mono<Collection<User>> mono = Mono.just(users);
        return route(RequestPredicates.path("/all-users"),
                request -> ServerResponse.ok().body(userFlux, User.class));
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunctionUsers(){
        Collection<User> users = userService.findAll();
        Flux<User> userFlux = Flux.fromIterable(users);
//        Mono<Collection<User>> mono = Mono.just(users);
        return route(RequestPredicates.path("/users"),
                request -> ServerResponse.ok().body(userFlux,User.class));
    }
    
    
    @Bean
    public RouterFunction<ServerResponse> saveUser(UserRouterHandler userHandler) {
    	return route(POST("/user/save"), userHandler::save);
    }
    
}
