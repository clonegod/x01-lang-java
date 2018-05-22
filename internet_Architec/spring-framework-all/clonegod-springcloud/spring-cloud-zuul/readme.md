# Spring Cloud Zuul - 网关

## 网关的几种实现方案
	nginx + lua：
		高性能web服务器，通过反向代理实现负载均衡，结合lua脚本可实现复杂的路由控制。
	netty：
		对高并发支持很好。
	zuul : 
		使用场景：企业内部的服务可以使用zuul作为服务网关。
		zuul不适用于外网的网关，原因是：处理静态文件的效率不高（静态文件通过nginx处理更佳）。
		
## Zuul 基本使用
	
##### 添加依赖
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-zuul</artifactId>
	</dependency>
	
###### 激活
	@EnableZuulProxy

-----------------

## Zuul 整合Ribbon
##### 配置ribbon不使用eureka注册中心的方式
	application.properties
	
		spring.application.name=spring-cloud-zuul

		server.port=7070
		
		## Zuul 基本配置模式
		# zuul.routes.${app-name}:/${app-url-prefix}/**	
		
		## Zuul 配置 person-service 服务调用
		# 所有匹配/person-service的请求，将交给ribbon进行转发调用（由person-service.ribbon.listOfServers所配置的服务器）
		zuul.routes.person-service=/person-service/**
		
		
		## Ribbon不使用Eureka的配置方式，则需要手动配置所有相关的服务地址
		ribbon.eureka.enable=false
		# 配置 person-service的负载均衡服务器列表 
		person-service.ribbon.listOfServers=http://localhost:9090
	
	
##### 调用链路
	zuul -> person-service	(person-service由spring-cloud-feign-provider提供服务)
	
	直接访问后端person-serviec服务
		http://localhost:9090/person/list
	
	通过zuul访问后端person-service服务
		http://localhost:7070/person-service/person/list
	
-----------------
	
## Zuul 整合Eureka

##### 添加依赖
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>

##### 配置Eureka服务注册与发现的客户端
	## Ribbon使用Eureka的配置方式
	# 配置Eureka Server 的地址，用于客户端注册
	eureka.client.service-url.defaultZone = \
		http://localhost:12345/eureka
	
	说明：
		目标应用在eureka上的serviceId=person-service
		而zuul.routes.person-service的app-name配置的就是'person-service'，两者是完全匹配的
		所以zuul从eureka上根据应用名称'person-service'就能获取到具体服务的请求地址，进而通过ribbon完成请求


##### 调用链路
	zuul -> person-service
	
	直接访问后端person-serviec服务
		http://localhost:9090/person/list
	
	通过zuul访问后端person-service服务
		http://localhost:7070/person-service/person/list


-----------------

## Zuul 整合Hystrix
	参考 spring-cloud-hystrix-client

-----------------

## Zuul 整合Feign
	
	1、先启动person-client，并注册到Eureka上
	
	2、在zuul中配置person-client的服务路由（根据person-client从eureka获取到服务地址）
		zuul.routes.person-client=/person-client/**
		
##### 相关服务端口
		eureka 端口：12345
		person-provider 端口：9090
		person-client 端口：8080
		zuul 端口：7070

	
##### 调用链路(服务调用的链路不断增长)
	zuul -> person-client -> person-service
	
	通过zuul访问后端person-service服务（先请求person-client，再由person-client请求person-serivce）
		http://localhost:7070/person-client/person/list
	

-----------------

## Zuul 整合Config Server
	
	目的：
		将服务路由的配置放到Config Server上，可实现服务的动态更新。
		比如，新增服务，不需要重启Zuul就可以完成服务上线。

### 改造 spring-cloud-config-server-git (使用git作为Configer Server的仓储)
	将 spring-cloud-config-server-git也配置为Eureka的客户端，向Eureka进行注册
	目的是让Zuul可以从配置中心获取相关服务的路由配置。

	【第1步】
	1、添加Eureka客户端依赖
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-starter-eureka</artifactId>
			</dependency>
		
	2、激活Eureka客户端
			@EnableDiscoveryClient
	
	3、配置Eureka服务地址
			eureka.client.service-url.defaultZone = http://localhost:12345/eureka
	
 	
	【第2步】 为spring-cloud-zuul增加配置文件
	zuul.properties
	zuul-test.properties
	zuul-prod.properties

	>>> zuul.properties（仅配置 person-service）
	## Zuul 配置 person-service 服务调用
	zuul.routes.person-service=/person-service/**
	
	# 配置person-client服务的路由
	#zuul.routes.person-client=/person-client/**
	
	
	>>> zuul-test.properties（仅配置 person-client）
	## Zuul 配置 person-service 服务调用
	#zuul.routes.person-service=/person-service/**
	
	# 配置person-client服务的路由
	zuul.routes.person-client=/person-client/**


	>>> zuul-prod.properties（配置 person-client, person-service）
	## Zuul 配置 person-service 服务调用
	zuul.routes.person-service=/person-service/**
	
	# 配置person-client服务的路由
	zuul.routes.person-client=/person-client/**
	
	【第3步】 测试配置是否生效 
	http://localhost:10000/zuul/defalut
	http://localhost:10000/zuul/test
	http://localhost:10000/zuul/prod
	

### 配置Zuul，使其从Config Server获取服务的配置信息

##### Zuul 增加配置客户端依赖
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-config</artifactId>
	</dependency>


##### 创建bootstrap.properties
	## bootstrap 上下文配置
	
	# 配置Eureka Server 的地址，用于客户端注册
	eureka.client.service-url.defaultZone = \
		http://localhost:12345/eureka
	
	# 配置服务器的URI（非直连方式）
	# 通过Eureka注册中心来获取Config Server的地址（前提是config server向Eureka进行了注册）
	spring.cloud.config.discovery.enabled=true
	# 配置 config server 应用程序的名称
	spring.cloud.config.discovery.serviceId=My-Config-Server-Git
	
	
	# 客户端应用程序的名称: {application}
	spring.cloud.config.name=zuul
	
	# 客户端应用程序激活的profile: ${profile}
	spring.cloud.config.profile=prod
	
	# 在git中的分支标识: ${label}
	spring.cloud.config.label=master


## applicaiton.properties
	# 说明：1. eureka的服务地址移到了bootstrap.properties中；2. 网关服务地址路由移到了config server中
	spring.application.name=spring-cloud-zuul
	server.port=7070
	management.security.enabled=false


##### 验证
	访问  http://localhost:7070/env，验证是否从Config Server获取配置信息成功
	
	  "configService:configClient": {
	    "config.client.version": "39f1ffabbb0920c0ad05f47f10027cdb5e93255c"
	  },
	  "configService:file:///E:/tmp/config-repo/zuul-prod.properties": {
	    "zuul.routes.person-client": "/person-client/**",
	    "zuul.routes.person-service": "/person-service/**"
	  },
	  "configService:file:///E:/tmp/config-repo/zuul.properties": {
	    "zuul.routes.person-service": "/person-service/**"
	  },
	
	
	通过Zuul访问person-client
		http://localhost:7070/person-client/person/list
		
	通过Zuul访问person-service
		http://localhost:7070/person-service/person/list

 	