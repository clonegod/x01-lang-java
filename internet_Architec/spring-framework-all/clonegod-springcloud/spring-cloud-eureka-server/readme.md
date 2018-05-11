# Spring Cloud Netflix Eureka
	
	Eureka是高可用的，不是高一致性的。
	当业务规模很大，并且要求强一致性时，Eureka就不是很适用了。
	
## 前微服务时代

### 分布式系统基本组成
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
## Eureka 高可用

### Eureka 客户端高可用
	>> 高可用注册中心集群- 配置多个Eureka Server的地址
		# 注：此处可将Eureka Server的地址配置为域名，通过Nginx反向代理到后端的Eureka Server，可简化配置
		配置项：
			eureka.client.service-url.defaultZone = \
				http://localhost:9090/eureka,http://localhost:9091/eureka
		
		配置源码 : EurekaClientConfigBean
			eureka.client.service-url 映射的字段为serviceUrl，类型是Map。
			Map的key被设计为zone，默认是"defaultZone", value是需要配置的Eureka注册服务器URL，value可以是多值，用逗号分隔
			
			比如，可以按地区来配置serviceUrl，根据地区配置最近的服务主机，以减少服务调用的网络延迟。
			--> eureka.client.region=BJ 
		
		注意：	
		1、如果Eureka客户端应用配置了多个Eureka注册服务器，那么默认情况下只有第一个可用的Eureka服务器，存在注册信息。
		2、如果第一台可用的Eureka服务器Down掉了，那么Eureka客户端应用将会选择下一台可用的Eureka服务器进行注册。
		
	>> 获取注册信息时间间隔
		Eureka 客户端需要获取Eureka服务器上相关服务的的注册信息，以实现服务调用。
		
		Eureka 客户端： EurekaClient
		单个应用信息：Application
		单个应用实例：InstanceInfo
		
		当Eureka客户端需要调用某个具体服务时，比如user-service-consumer调用user-service-provider, user-service-provider实际对应的是Application，而Application则关联了若干个InstanceInfo
		
		如果应用user-service-provider发生了变化，那么user-service-consumer是需要感知的。
		比如，user-service-provider机器从10台降到了5台，那么，作为调用方user-service-consumer是需要知道这个变化情况。
		可是这个变化过程，可能存在一定的延迟，因此可以通过调整刷新注册信息时间间隔来减少错误。
		
		配置项：
			eureka.client.registry-fetch-interval-seconds=5 （默认30s）
	
	>> 实例信息复制时间间隔
		指的是客户端信息上报到Eureka服务器的时间间隔。
		当Eureka客户端应用上报的频率约高，那么Eureka服务器的应用状态管理一致性就越高。
	
	>> 实例ID 
		instanceId - 即服务的ID，比如 USER-SERVICE-PROVIDER，USER-SERVICE-CONSUMER
			从Eureka Server Dashboard上可以看到具体某个应用的实例信息。
			比如实例： USER-SERVICE-PROVIDER，对应的值为：hqh-PC:user-service-provider:7071 , hqh-PC:user-service-provider:7070
		
		源码：EurekaInstanceConfigBean
			实例ID的调整配置项（三个维度：主机名+serviceId+端口）：
			eureka.instance.instance-id=${COMPUTERNAME}:${spring.application.name}:${server.port}
		
		另外， serviceId - 即service provider的名称，比如 serviceId = user-service-provider
	
	>> 实例端点映射URL
		源码：EurekaInstanceConfigBean
		
		# 修改Eureka DashBoard上显示实例状态信息所映射的url
		# 默认是链接到/info
		eureka.instance.status-page-url-path=/health


### Eureka 服务端高可用

	>> 构建Eureka服务器相互注册（以2个节点为例）
	>> 通过profile来配置2台Eureka Server相互注册
	
	# application-peer1.properties(port=9090, 向9091同步数据)
		server.port=9090
		eureka.client.register-with-eureka=true
		eureka.client.fetch-registry=true
		eureka.client.service-url.defaultZone = http://localhost:9091/eureka
	# 启动
		java -jar spring-cloud-eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
	
	# application-peer2.properties(port=9091, 向9090同步数据)
		server.port=9091
		eureka.client.register-with-eureka=true
		eureka.client.fetch-registry=true
		eureka.client.service-url.defaultZone = http://localhost:9090/eureka
	# 启动
		java -jar spring-cloud-eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2


----------------------------------

## Spring RestTemplate
	>>> HTTP消息装换器：HttpMessageConvertor
		可根据需要自义定实现消息转换器，比如
			编码问题
			切换序列化/反序列化协议
			
	>>> HTTP Client 适配工厂：ClientHttpRequestFactory
		RestTemplate 底层采用的http通信框架可以指定具体实现：
			Spring 实现
				SimpleClientHttpRequestFactory
			HttpClient
				HttpComponentsClientHttpRequestFactory
			OkHttp
				OkHttp3ClientHttpRequestFactory
				OkHttpClientHttpRequestFactory
	
	>>> HTTP 请求拦截器：ClientHttpRequestInterceptor
	

## Eureka Client 整合 Netflix Ribbon

	RestTemplate增加一个LoadBalancerInterceptor，调用Netflix 中的LoadBalander实现，根据 Eureka 客户端应用获取目标应用 IP+Port 信息，轮训的方式调用。
	
	spring-cloud-starter-eureka 会自动依赖 spring-cloud-starter-ribbon
	目的是：service consumer调用远程服务时，可以使用ribbon来做负载均衡（默认情况下，会以轮询方式调用server provider）
	
	RestTemplate结合@LoadBalanced注解时，会使用Ribbon作为请求代理，实现负载均衡的功能。
	
	# LoadBalancer 解析服务地址，并完成服务调用
	org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient.execute(String, LoadBalancerRequest<T>)
	
	# 替换 serviceId 为真正的主机名+端口的服务地址
	com.netflix.loadbalancer.LoadBalancerContext.reconstructURIWithServer(Server, URI)


#### 实际请求客户端
	LoadBalancerClient	
		RibbonLoadBalancerClient

#### 负载均衡上下文
	LoadBalancerContext
		RibbonLoadBalancerContext

#### 负载均衡器 
	ILoadBalancer
		BaseLoadBalancer
		DynamicServerListLoadBalancer
		ZoneAwareLoadBalancer
		NoOpLoadBalancer

#### 负载均衡规则
	核心规则接口 IRule
		随机规则：RandomRule
		最佳可用规则：BestAvailableRule
		轮训规则：RoundRobinRule
		重试实现：RetryRule
		客户端配置：ClientConfigEnabledRoundRobinRule
		可用性过滤规则：AvailabilityFilteringRule
		RT权重规则：WeightedResponseTimeRule
		规避区域规则：ZoneAvoidanceRule


#### PING 策略
	核心策略接口 
		IPingStrategy
	PING 接口 
		IPing
			NoOpPing
			DummyPing
			PingConstant
			PingUrl ---> Discovery Client 实现

---------------------
## Consul
	Consul 提供与Eureka类似的功能，不过 consul功能更强大一些，广播式服务发现与注册
	
	Consul vs. Zookeeper, Eureka
		https://www.consul.io/intro/vs/zookeeper.html
		https://www.consul.io/intro/vs/eureka.html
		
