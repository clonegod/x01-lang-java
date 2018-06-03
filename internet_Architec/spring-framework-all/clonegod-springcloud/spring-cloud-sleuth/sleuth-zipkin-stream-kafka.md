# Spring Cloud Sleuth - Stream - Kafka
	sleuth生成链路调用的跟踪日志，将消息以steam方式进行提交
	流程：
		应用产生的日志被发送到Kafka
		zipkin Server从kafka获取日志信息
		zipkin Server UI 显示链路调用信息
	
## 添加Maven依赖
		<!-- Sleuth with Zipkin via RabbitMQ or Kafka -->
	   <dependency>
	       <groupId>org.springframework.cloud</groupId>
	       <artifactId>spring-cloud-starter-zipkin</artifactId>
	   </dependency>
	   <dependency>
	       <groupId>org.springframework.kafka</groupId>
	       <artifactId>spring-kafka</artifactId>
	   </dependency>

## 配置application.properties
	## 配置Zipkin服务器的地址（http方式） --- 采用stream方式时，注释掉此项
	#spring.zipkin.base-url=http://${zipkin.server.host}:${zipkin.server.port}
	
	## 配置Zipkin服务器的地址（stream方式）
	spring.zipkin.sender.type=kafka
	spring.kafka.bootstrap-servers=localhost:9092
	
	## 配置span的采样频率（使用zipkin时配置此项）
	spring.sleuth.sampler.percentage=0.8


## Zipkin Server 配置 Stream，参考 spring-cloud-zipkin-server
