package com.asynclife.hessian.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.asynclife.hessian.service.IHelloService;

@Service
public class HelloServiceImpl implements IHelloService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String sayHello(String name) {
		
		logger.info("client call in, paramter: name = {}", name);
		
		return "你好， "+name;
	}

}
