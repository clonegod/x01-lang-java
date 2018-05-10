### >>> 小马哥说：
	程序员：关注功能性的实现
	架构师：关注非功能性的实现 - 从代码角度看，系统结构是否合理，线程是否安全，应用是否稳定，系统的极限在什么地方，数据分布式的一致性等

# spring-cloud 技术体系

## 预备知识
	
	构建系统：  Maven / Gradle
	基础框架：SpringFramework + Spring Boot

## Features 核心功能
    
	Distributed/versioned configuration         分布式配置管理: git 、snv、zookeeper
    Service registration and discovery          服务注册与发现: Eureka
    Routing                                     路由
    Service-to-service calls                    服务调用（RPC）: Feign
    Load balancing                              负载均衡: Ribbon	
    Circuit Breakers                            短路/熔断: Hystrix
    Distributed messaging                       分布式消息: ActiveMQ / JMS 的进一步抽象


    
## Spring Cloud 技术整合
	eureka		- 服务注册与发现
	ribbon		- 负载均衡
	hytrix		- 熔断器、短路、限流
	feign		- 基于HTTP的RPC调用
	zuul		- 网关
	security - OAuth2
	sleuth		- 监控
	stream		- Messaging
    
-------------------------

##### Config Server - 配置中心：

	配置中心集中管理各个服务的配置信息
	按三个维度关联到配置：
			${application} + ${profile} + ${label}
		application: 应用名称，如user-service-provider
		profile: 配置环境，如production
		label: 版本信息，如git的master分支	

##### Eureka - 服务注册与发现 :

	Eureka Server： 
		提供服务注册与管理（服务健康状态检查）的功能。注册信息是保存在内存中的。
		Eureka Server 可以部署多个实例，实现高可用。
	Eureka Client： 
		从Eureka Server 获取服务地址，通过LoadBalancer转换后得到真正的服务地址，最后将请求发送到真正的service provider上。
		
		Eureka Client 是Eureka的客户端
			对于service-provider而言，由它将服务地址注册到Eureka Server上
			对于service-consumer而言，由它从Eureka Server上获取相关的服务地址信息

	服务调用：
		service consumer 调用 service consumer 直接使用 Spring RestTemplate + @LoadBalanced实现服务调用和负载均衡的功能。
		实现方式为：基于HTTP协议进行的通信，不是二进制类型的RPC通信。

##### Ribbon - 负载均衡器
	服务调用的负载均衡：
		Eureka默认集成Ribbon，并使用Ribbon作为负载均衡的实现。
		service provider 服务一般会存在多个实例，Eureka client在调用服务时，需要根据负载均衡策略从中选择一个实例进行调用。

##### Hystrix - 服务熔断器 / 限流
	服务短路，服务熔点的目的：保护服务的正常运行

	Spring Cloud Hystrix 常用限流的功能	


-------------------------
	
## Spring Cloud 学习路线

### 1、Spring Cloud Config Client & Server
##### Spring Cloud Config Client - 从配置中心获取配置信息

	Spring Framework的事件监听：
		Environment、以及 Spring Boot 配置相关的事件和监听器
		如ApplicationEnvironmentPreparedEvent和ConfigFileApplicationListener
	
	Bootstrap 配置属性（bootstrap.properties）：
		解密 Bootstrap 配置属性与 Spring Framework / Spring Boot 配置架构的关系
		介绍如何调整 Bootstrap 配置文件路径、覆盖远程配置属性、自定义 Bootstrap 配置以及自定义 Bootstrap 配置属性源

		## bootstrap 上下文配置
		# 配置服务器的URI
		spring.cloud.config.uri=http://localhost:9090/
		# 客户端应用程序的名称: {application}
		spring.cloud.config.name=myapp
		# 客户端应用程序激活的profile: ${profile}
		spring.cloud.config.profile=prod
		# 在git中的分支标识: ${label}
		spring.cloud.config.label=master

	Environment 端点：
		介绍/env 端点的使用场景，并且解读其源码
		通过/env 端点，可验证客户端获取到的配置是否正确

	客户端主动刷新配置：
		/refresh
		
##### Spring Cloud Config Server - 提供配置管理的功能，可基于git/svn/zookeeper等实现
	基本使用：
		介绍@EnableConfigServer、Environment 仓储

	分布式配置官方实现：
		介绍 Spring 官方标准分布式配置实现方式：Git实现 和 文件系统实现
		Git作为配置仓储（本地git仓库为例）：
			spring.cloud.config.server.git.uri=file:///E:/tmp/config-repo

	动态配置属性 Bean：
		介绍@RefreshScope基本用法和使用场景，并且说明其中的局限性(简单属性的刷新，适用于简单场景：如，开关变量，threshold值的更新等)

	健康指标：
		介绍 Spring Boot 标准端口（/health）以及 健康指标（Health Indicator）
	
	健康指标自定义实现：
		实现分布式配置的健康指标自定义实现（自定义收集服务端的相关状态数据）

### 2、Spring Cloud Netflix Eureka
	前微服务时代：
		介绍前微服务时代，服务发现和注册在 SOA 甚至是更早的时代的技术实现和实施方法，如 WebService 中的UDDI、REST 中的 HEATOAS
    
	高可用架构：
		简介高可用架构的基本原则 - 消除单点故障，保证服务的可靠性

	Eureka 客户端：
		介绍 Spring Cloud Discovery 结合 Netflix Eureka 客户端的基本使用方法，包括服务发现激活、Eureka 客户端注册配置 以及 API 使用等
	
	Eureka 服务器：
		介绍 Eureka 服务器作为服务注册中心的搭建方法，以及內建 Dashboard 基本运维手段	
    
    

### 3、Spring Cloud Netflix Ribbon
	Eureka 高可用：
		实战 Eurkea 客户端/服务器高可用

	RestTemplate：
		Spring Framework HTTP 组件 RestTemplate 的使用方法，结合 ClientHttpRequestInterceptor 实现简单负载均衡客户端
	
	@LoadBalanced:
		RestTemplate 标记了@LoadBalanced注解后，将扩展为具有负载均衡效果的客户端调用

	整合 Netflix Ribbon：
		作为 Spring Cloud 客户端负载均衡实现 ，Netflix Ribbon 提供了丰富的组件，包括负载均衡器、负载均衡规则、PING 策略等，实现客户端负载均衡


### 4、Spring Cloud Hystrix
	核心理念 - 服务短路(CircuitBreaker)：
		介绍服务短路的名词由来、目的，以及相关的类似慨念
		触发条件、处理手段以及客户端和服务端实现方法

	Spring Cloud Hystrix Client：
		作为服务端服务短路实现，介绍 Spring Cloud Hystrix 常用限流的功能
	
	Spring Cloud Hystrix DashBoard：
		说明健康指标以及数据指标在生产环境下的现实意义
	
	整合 Netflix Turbine

	生产准备特性：
		介绍聚合数据指标 Turbine 、Turbine Stream，以及整合 Hystrix Dashboard


### 5、Spring Cloud Feign
	核心理念：
		回顾远程服务调用（RPC）的核心理念，介绍接口定义语言（IDL）以及服务存根（Stubs）以及通讯协议，如二进制协议 RMI、文本协议 REST 等
	
	Spring Cloud Feign ：
		介绍声明式客户端REST实现 Spring Cloud Feign的使用方式（如@EnableFeignClients 、 @FeignClient)，结合 Eureka 构建分布式服务应用
	
	整合支持：
		Spring Cloud Feign 整合 Hystrix 以及 Ribbon


### 6、Spring Cloud Zuul
	核心概念：介绍服务网关使用场景、服务能力、依赖关系、架构以及类型
	Ribbon 整合
	Hystrix 整合


### 7、Spring Cloud Stream
	Kafka
	Spring Kafka
	Spring Boot Kafka
	Spring Cloud Stream，Kafka 绑定实现


### 8、Spring Cloud Sleuth

	分布式应用跟踪

	ZipKin 整合

