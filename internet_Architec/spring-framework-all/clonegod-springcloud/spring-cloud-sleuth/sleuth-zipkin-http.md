### 1、HTTP - 简单，但是效率不高，受网络影响比较
##### 配置Sleuth集成Zipkin依赖：
		<!-- Sleuth  -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-sleuth</artifactId>
		</dependency>
		<!-- Zipkin 客户端 -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>

##### 测试HTTP方式上报日志
	http://localhost:6060
	访问后台接口，触发记录日志
	记录的日志通过http方式，将日志发送到zipkin server，之后从zipkin ui上便可以查看相关的请求链路。

-----
## 改造sleuth (上报日志到zipkin，从eureka获取zuul的调用地址)
	1、增加Eureka客户端依赖
		<!-- Eureka 客户端 -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
		
	2、激活Eureka客户端，调整配置
		@SpringBootApplication
		@EnableDiscoveryClient
		public class SpringCloudSleuthApplication {
		
			public static void main(String[] args) {
				SpringApplication.run(SpringCloudSleuthApplication.class, args);
			}
			
			@Bean
			@LoadBalanced
			public RestTemplate restTemplate() {
				return new RestTemplate();
			}
		}
		
	3、调用zuul
			/**
			 * 完整调用链路:
			 * 	浏览器
			 * 		-> sleuth 
			 * 		-> zuul 
			 * 		-> person-client 
			 * 		-> person-service 	
			 */
			@GetMapping("/to/zuul")
			public Object toZuul() {
				String zuulServiceName = "spring-cloud-zuul"; 	// zuul在eureka上注册 的名称
				String personServiceName = "person-client"; 	// person-client在eureka上注册 的名称
				String url = "http://" + zuulServiceName + "/" + personServiceName + "/person/list";
				logger.info("to zuul, url={}", url);
				Object value = restTemplate.getForObject(url, Object.class);
				return value;
			}
		
## 改造zuul (上报日志到zipkin)
	由于zuul中没有controller接口，没地方打印日志，因此忽略
	
	
## 改造person-client、person-service (上报日志到zipkin)
	1、增加zipkin依赖（在 person-api中依赖，然后传递依赖）
		<!-- Zipkin 客户端 -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>
		
	2、增加zipkin配置
		## Zipkin 服务器配置
		zipkin.server.host=localhost
		zipkin.server.port=10110
		
		## 配置Zipkin服务器的地址
		spring.zipkin.base-url=http://${zipkin.server.host}:${zipkin.server.port}
	
	3、配置一个通用的Interceptor，用于打印日志，触发日志上报（person-api）
		person-client,person-service通过@Import导入
		