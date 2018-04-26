package com.asynclife.hessian.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asynclife.hessian.service.IHelloService;

@Service
public class LocalService {
	
	@Autowired
	IHelloService helloService; // 注入远程服务的Proxy
	
	public String call(String name) {
		return helloService.sayHello(name);
	}
	
}
