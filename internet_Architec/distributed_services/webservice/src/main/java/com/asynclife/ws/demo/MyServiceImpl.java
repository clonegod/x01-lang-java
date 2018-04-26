package com.asynclife.ws.demo;

import javax.jws.WebService;

/**
 * SIB : Service Implements Bean 服务实现Bean
 *	服务接口的实现类
 */
@WebService(endpointInterface="com.asynclife.ws.demo.IMyService")
public class MyServiceImpl implements IMyService {

	public long add(int num1, int num2) {
		return num1 + num2;
	}

}
