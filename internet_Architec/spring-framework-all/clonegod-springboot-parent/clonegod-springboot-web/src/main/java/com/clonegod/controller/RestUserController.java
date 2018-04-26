package com.clonegod.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clonegod.api.User;

/**
 * Spring Web Mvc - REST 
 *
 */
@RestController
public class RestUserController {

	@GetMapping("/rest/user/{id}")
	public User getUser(@RequestHeader("accept") String accept,
						@PathVariable Long id, 
						@RequestParam(required=false) String name) {
		
		// MIME type -> spring 将根据请求类容类型，选择最匹配的MessageConverter来生成响应内容
		// Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
		System.out.println("Accept:" + accept);
		
		return User.builder().id(id).name(name).build();
	}
		
	
	/**
	 * 接收客户端发送的properties格式内容，返回json格式内容给客户端。
	 * 由自定义HttpMessageConverter进行实现： UserPropertiesHttpMessageConvertor
	 * 
	 * 发送请求是的accept和content-type头，一定要设置正确，否则无法使用到自定义的MessageConverter
	 * 
	 * consumes="application/properties+user"  客户端发送的内容类型为 application/properties+user
	 * produces="application/json"			   	向客户端响应的内容类型为application/json
	 */
	@PostMapping(path="/rest/user/prop/to/json",
			consumes="application/properties+user", // content-type
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE) // accept
	public User postPropertiesReturnJson(@RequestBody User user) {
		return user;
	}
	
	
	/**
	 * 接收客户端发送的json格式数据，返回properties格式内容给客户端。
	 * 由自定义HttpMessageConverter进行实现： UserPropertiesHttpMessageConvertor
	 * 
	 * 发送请求是的accept和content-type头，一定要设置正确，否则无法使用到自定义的MessageConverter
	 * 
	 */
	@PostMapping(path="/rest/user/json/to/prop",
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, 
			produces="application/properties+user")
	public User postJsonReturnProperties(@RequestBody User user) {
		return user;
	}
	
}
