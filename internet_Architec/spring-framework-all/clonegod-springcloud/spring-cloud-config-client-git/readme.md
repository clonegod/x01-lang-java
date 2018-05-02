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
			git server 支持更新通知的功能？
			
		
	
		