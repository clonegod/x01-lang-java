## 构建spring cloud 配置客户端

## 实现步骤
	
	1、引入依赖包
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		
	2、新建bootstrap.properties，配置configServer的地址，以及{applicaiton},{profile},{label}
		# 配置服务器的URI
		spring.cloud.config.uri=http://localhost:9090/
		
		# 客户端应用程序的名称: {application}
		spring.cloud.config.name=myapp
		
		# 客户端应用程序激活的profile: ${profile}
		spring.cloud.config.profile=prod
		
		# 在git中的分支标识: ${label}
		spring.cloud.config.label=master
		
	3、启动服务，查看日志输出
		Fetching config from server at: http://localhost:9090/
		...
		
	4、当config server上的配置信息发生修改后，客户端需要实时同步数据更新
		a, 客户端定时请求config server，实现配置数据的刷新；
			可以调用client端的本地接口/refresh实现刷新功能。
		b,	config server 回调通知客户端，客户端再请求config server更新配置信息；
			git hook 更新通知
			
		
## 动态配置属性 Bean
	
	@RefreshScope - 指定可动态刷新的Bean的范围
	只有被该注解标记后，对应Bean下的相关属性才可能被动态刷新为最新配置（config server上配置）
    通过调用/refresh Endpoint控制客户端配置的更新，一般用于实现：开关、阈值等功能的动态变更场景。
	
	RefreshEndpoint - refresh 端点
	org.springframework.cloud.endpoint.RefreshEndpoint.refresh()
	
	ContextRefresher - 刷新上下文环境配置
	org.springframework.cloud.context.refresh.ContextRefresher


## 健康指标 - 应用程序各个方面的健康指标，比如diskSpace，memoryUsage, configServer等
	
	/health	
		endpoints.health.sensitive=false
		management.security.enabled=false
		
	HealthEndpoint
		1个HealthEndpoint可以聚合多个HealthIndicator
		
	HealthIndicator	
        1个HealthIndicator表示一个功能/方面的健康状态的统计
        

## Actuator - 为生产而准备的特性---在线监控与管理系统的运行状态/参数
	/actuator 
		spring boot 激活actuator，必须添加 Hateoas 的依赖
		<dependency>
			<groupId>org.springframework.hateoas</groupId>
			<artifactId>spring-hateoas</artifactId>
		</dependency>
	

#### what's HATEOAS ?
	HATEOAS 可以理解为 REST 服务API的发现入口
	类似 /users, /withdraw 等，提供所有rest api的统一访问入口
	

-------------
	
https://blogs.sap.com/2017/11/08/centralized-configuration-of-spring-boot-applications-using-sap-cloud-platform/
	