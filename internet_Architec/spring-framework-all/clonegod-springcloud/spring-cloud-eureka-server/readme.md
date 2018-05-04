# Spring Cloud Netflix Eureka

## 前微服务时代

分布式系统基本组成
	服务提供方 	Provider
	服务消费方 	Consumer
	服务注册中心  Registry
	服务路由  Router 	
		- 消费端告诉服务端要调用哪个方法
	服务代理  Broker 	
		- 服务路由通过代理来实现远程方法的调用，还附加额外的功能，如：限流，断流等。比如底层使用netty进行网络通信框架完成客户端与服务端数据的传输。
	通讯协议  Protocal 
		- 服务代理客户端和服务端之间通信的数据载体，比如基于http协议进行传输，基于二进制进行传输(socket)


### 通信协议/数据载体的表现形式
	XML-RPC -> XML 方法描述，方法参数  -> WSDL(webservice定义语言)
	
	webservice -> SOAP协议（HTTP/SMTP） -> 文本协议（头部分，体部分）
	
	REST -> JSON/XML -> 文本协议(HTTP header, body)
	
	Dubbo -> Hession, Java Serializable， thrif, protobuf (二进制)	
	
	
## 高可用架构
	基本原则：消除单点故障，保证服务的可靠性
	
	可用性比率计算
		可用性比率：通过事件来计算（一年或一月）
		比如，一年 99.99%可用性
		可用时间：365 * 24 * 3600 * 99.99%
		不可用时间：365 * 24 * 3600 * 0.01% = 3153.6 秒  < 1小时 
	
		单台机器不可用率：	 1%
		两台机器不可用率：	 1% * 1%
		N台机器不可用率：	 1% ^ N
		
	服务的可靠性
		A -> B -> C -> D
		服务调用的链路越长，服务的可靠性越低
		
		增加机器可以提高服务的可用性
		A1|A2 -> B1|B2|B3 -> C1|C2 -> D1|D2|D3
		
		增加服务调用链会降低可靠性，同时也降低了可用性

## 服务发现
	传统技术
		WebService - UDDI
		
		REST - Hateoas
		
		Java - JMS, JNDI

------------------------

## Eureka 服务端  - 管理注册信息
	
	Netflix Eureka Server
		激活配置：
			@EnableEurekaServer
		
		说明：
		 	Eureka 服务器不需要开启自我注册：
		 		eureka.client.register-with-eureka=false
		 	Eureka 服务器不需要检索服务注册信息：
		 		eureka.client.fetch-registry=false
		
		异常：
		com.netflix.discovery.shared.transport.TransportException: Cannot execute request on any known server
		
		解决办法---关闭Eureka Server的自我注册和配置拉取：
		>>> Indicates whether or not this instance should register its information with eureka server for discovery by others.
		eureka.client.register-with-eureka=false
		
		>>> Indicates whether this client should fetch eureka registry information from eureka server.
		eureka.client.fetch-registry=false
		
		注意：上面两个选项并不是必须设置的，它不会影响Eureka服务的使用。但建议关闭，这样可以减少不必要的异常堆栈。
	
	
### DS Replicas - Eureka Server 的高可用/一主一备

	Eureka	的副本信息，可实现Eureka Server的高可用。
	一般配置2台Eureka Server就可以满足需求。
	
### 客户端注册的信息存储在哪里？

	Eureka Server将客户端注册的信息都保存在内存中。


------------------------------
	
## Eureka 客户端 - 服务注册、服务发现
	
	consumer: 服务消费方
	provider: 服务提供方
	
	Netfilx Eureka Client
		依赖包：spring-cloud-starter-eureka
		
		API:
			EurekaClient
				DiscoveryClient
					Applications
						Application
							InstanceInfo
		激活：
			// @EnableEurekaClient
			@EnableDiscoveryClient
			
			

### RestTemplate + @LoadBalanced

	consumer使用  RestTemplate 实现对provider服务的调用
	
	标记了@LoadBalanced的RestTemplate，可以将provider的应用系统名称替换为真正的ip和端口，而且它还将具有负载均衡的调用效果。
		
	// Annotation to mark a RestTemplate bean to be configured to use a LoadBalancerClient
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}	

	
---------------------

## Consul
	Consule提供与Eureka类似的功能，不过 consul功能更强大一些，广播式服务发现与注册

	