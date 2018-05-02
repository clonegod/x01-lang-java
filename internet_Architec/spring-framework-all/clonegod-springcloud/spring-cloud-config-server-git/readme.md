## spring cloud config server

## Environment 仓储
	
	{application}
		-	客户端配置的应用程序名称 - spring.application.name，比如: myapp
	
	{profile}	
		-  客户端spring.profile.active激活的环境，比如：prod
	
	{label}	
		-  服务端配置文件版本标识，比如 : master


## spring cloud 分布式配置 - 使用 Git 作为配置数据的仓储
	
	实现步骤：
	1、在Configuration类中，标记@EnableConfigServer
	
		@SpringBootApplication
		@EnableConfigServer  // 启用ConfigServer
		public class SpringCloudConfigServerGitApplication {
		
			public static void main(String[] args) {
				SpringApplication.run(SpringCloudConfigServerGitApplication.class, args);
			}
		}
	
	2、本地创建一个专用的配置目录，存储相关应用的配置数据
		比如，myapp应用，不同环境的配置文件如下：
			myapp.properties				默认配置/公共配置
			myapp-dev.properties			开发环境
			myapp-sit.properties			集成测试环境 SIT(System Integration Testing)
			myapp-staging.properties		预发布环境
			myapp-prod.properties			生产环境
		
		添加配置文件到git
			$ cd E:\tmp
			$ mkdir config-repo
			$ cd config-repo
			$ git init .
			$ git add -A .
			$ git commit -m "Add myapp.properties"
			
	
	3、服务端配置（配置版本仓库的地址，指向配置.git目录的上一级）
			spring.cloud.config.server.git.uri=file:///E:/tmp/config-repo
			
			# spring.cloud.config.server.zookeeper.uri=zookeeper://
	
			>>> 配置完成后，浏览器可直接访问HTTP接口查看配置信息： 
			http://localhost:9090/myapp/master/			-> myapp.properties
			http://localhost:9090/myapp/prod/master/		-> myapp-prod.properties	
	
			
	4、客户端配置，参考 spring-cloud-config-client-git
			spring.cloud.config.uri
			spring.cloud.config.name
			spring.cloud.config.profile
			spring.cloud.config.label
			

	
## spring cloud 分布式配置 - zookeeper 实现

	TODO



## 动态配置属性 Bean





## 健康指标 - Actuator
















