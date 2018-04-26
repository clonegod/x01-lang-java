package com.asynclife.hessian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianExporter;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.asynclife.hessian.service.IHelloService;

@Configuration
public class HessianConfiguration {
	
	@Autowired
	IHelloService helloService; // 注入服务
	
	@Bean(name="/hello")
	public HessianExporter sayHelloServiceExporter() {
		return buildHessianServiceExporter(IHelloService.class, helloService);
	}
	
	/**
	 * 发布服务
	 * @param serverInterface
	 * @param service
	 * @return
	 */
	private HessianExporter buildHessianServiceExporter(Class<?> serverInterface, Object service) {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setServiceInterface(serverInterface);
		exporter.setService(service);
		return exporter;
	}
}
