## spring cloud config server

## Environment 仓储
	
	{application}	-	配置使用客户端应用程序名称
	{profile}			-  客户端spring.profile.active激活的环境 
	{label}			-  服务端配置文件版本标识


## spring cloud 分布式配置 - Git 实现
	实现步骤：
	1、在Configuration类中，标记@EnableConfigServer
	2、配置git文件目录
		myapp.properties				默认配置/公共配置
		myapp-dev.properties			开发环境
		myapp-sit.properties			集成测试环境 SIT(System Integration Testing)
		myapp-staging.properties		预发布环境
		myapp-prod.properties			生产环境
	3、服务端配置（配置版本仓库）
			
	4、客户端配置
		
	
		
	
	
	

## spring cloud 分布式配置 - zookeeper 实现

	TODO



## 动态配置属性 Bean





## 健康指标 - Actuator
















