package com.clonegod.validation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationController {

	/**
	 * 通过spring自动完成对请求参数的验证 - 底层使用Hibernate validator实现
	 */
	@PostMapping("/validation/bean/save")
	public Bean saveUser(@Validated
						 @RequestBody Bean bean) {
		return bean;
	}
	
}
