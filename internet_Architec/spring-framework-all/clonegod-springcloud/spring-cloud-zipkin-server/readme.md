## 搭建 Zipkin Server

## 1、HTTP方式
### 加入依赖
		<!-- Zipkin server 服务支持-->
		<dependency>
		    <groupId>io.zipkin.java</groupId>
		    <artifactId>zipkin-server</artifactId>
		</dependency>
		
		<!-- Zipkin UI 界面支持-->
		<dependency>
			<groupId>io.zipkin.java</groupId>
			<artifactId>zipkin-autoconfigure-ui</artifactId>
		</dependency>
		
		
### 激活Zipkin自动配置
	@SpringBootApplication
	@EnableZipkinServer // HTTP方式收集日志
	public class SpringCloudZipkinServerApplication {
	
		public static void main(String[] args) {
			SpringApplication.run(SpringCloudZipkinServerApplication.class, args);
		}
	}
	
	
### 访问Zipkin UI
	http://localhost:10110

---------------------------------------------------------------------------

## 2、Stream方式  - Kafka

## 从Edgware 版本开始，spring-cloud-sleuth-zipkin-stream已经被废弃。需使用zipkin对kafka提供的实现
	spring-cloud-sleuth-zipkin-stream is deprecated and should no longer be used. 
	Please use the OpenZipkin’s Zipkin server and set the environment variables for it.
	
	We recommend using Zipkin’s native support for message-based span sending. 
	Starting from the Edgware release, the Zipkin Stream server is deprecated. 
	In the Finchley release, it got removed.
	

## 添加Maven依赖
		<!-- Zipkin 服务器依赖 -->
		<dependency>
			<groupId>io.zipkin.java</groupId>
			<artifactId>zipkin-server</artifactId>
		</dependency>

		<!-- Zipkin 服务器UI控制器 -->
		<dependency>
			<groupId>io.zipkin.java</groupId>
			<artifactId>zipkin-autoconfigure-ui</artifactId>
		</dependency>
		
		<!-- Zipkin native support for kafka -->
		<dependency>
		    <groupId>io.zipkin.java</groupId>
		    <artifactId>zipkin-autoconfigure-collector-kafka10</artifactId>
		    <version>2.6.1</version>
		</dependency>
		

## 激活ZipkinServer
	@SpringBootApplication
	@EnableZipkinServer
	public class SpringCloudZipkinServerApplication {
	
		public static void main(String[] args) {
			SpringApplication.run(SpringCloudZipkinServerApplication.class, args);
		}
	}

## 配置Zipkin collector 相关参数
	
	application.properties
		# Zipkin native support for kafka 
		zipkin.collector.kafka.bootstrap-servers=localhost:9092
		zipkin.collector.kafka.groupId=zipkinServer01
		zipkin.collector.kafka.topic=zipkin
	
	>>> ZipkinKafka10 Collector AutoConfiguration 
	see more: 
		zipkin-autoconfigure-collector-kafka10-2.6.1.jar/zipkin-server-kafka.yml
		zipkin.autoconfigure.collector.kafka10.ZipkinKafka10CollectorAutoConfiguration
		zipkin.autoconfigure.collector.kafka10.ZipkinKafkaCollectorProperties




