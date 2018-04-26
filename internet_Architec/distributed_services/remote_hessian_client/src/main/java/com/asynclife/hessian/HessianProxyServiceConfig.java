package com.asynclife.hessian;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.asynclife.hessian.service.IHelloService;

@Configuration
public class HessianProxyServiceConfig {

	@Bean(name="helloService")
	public HessianProxyFactoryBean createHelloServiceProxy() {
		return buildHessianProxyFactoryBean(IHelloService.class, "hello");
	}
	
	/**
	 * 创建远程服务的Proxy
	 * @param serviceInterface
	 * @param serviceName
	 * @return
	 */
	private HessianProxyFactoryBean buildHessianProxyFactoryBean(
			Class<?> serviceInterface, String serviceName) {
		
		HessianProxyFactoryBean hpf = new HessianProxyFactoryBean();
		hpf.setServiceInterface(serviceInterface);
		hpf.setServiceUrl("http://localhost:80/"+serviceName);
		
		return hpf;
	}
	
}
