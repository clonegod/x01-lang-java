package clonegod.rpc.dubbo.service.impl;

import clonegod.rpc.dubbo.service.HelloService;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		return "你好： " + name;
	}


}
