[Spring Cloud Sleuth](http://cloud.spring.io/spring-cloud-static/spring-cloud-sleuth/1.3.3.RELEASE/single/spring-cloud-sleuth.html)

## Google Dapper
	Sleuth基于Google Dapper论文进行实现


## Spring Cloud Sleuth
	监控链路跟踪：一个请求到返回响应，所经过的所有服务，以及服务的响应时间（RT值）；
	预警：对链路中出现的异常，及时通知到开发人员；
	日志排查：处理请求失败，需要快速定位到是链路中哪个环节出现了异常；

## 引入Maven依赖
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-sleuth</artifactId>
	</dependency> 

## 应用日志发生的变化
	当应用的classpath下存在spring-cloud-starter-sleuth时，日志会发生变化。 

>	MDC: Mapped Diagnostic Context (映射诊断上下文)

	整体流程：
		sleuth会自动装配一个名为TraceFilter组件，该组件会调整当前日志系统(slf4j)的MDC，
		在记录日志的时候，会自动增加相关的链路跟踪数据到输出的日志中。	
	INFO [spring-cloud-sleuth,65500ff0479b72e6,65500ff0479b72e6,false] 5648 
	日志中新增部分的说明：	
		spring-cloud-sleuth,	应用程序的名称，由spring.applicaiton.name决定
		65500ff0479b72e6,		traceId，整个链路唯一
		65500ff0479b72e6,		spanId，发生特定操作所在系统的id
		false	是否日志可以输出到Zipkin



### SpanLogger 日志扩展的实现
	org.springframework.cloud.sleuth.log.Slf4jSpanLogger
	
	@Override
	public void logStartedSpan(Span parent, Span span) {
		MDC.put(Span.SPAN_ID_NAME, Span.idToHex(span.getSpanId()));
		MDC.put(Span.SPAN_EXPORT_NAME, String.valueOf(span.isExportable()));
		MDC.put(Span.TRACE_ID_NAME, span.traceIdString());
		log("Starting span: {}", span);
		if (parent != null) {
			log("With parent: {}", parent);
			MDC.put(Span.PARENT_ID_NAME, Span.idToHex(parent.getSpanId()));
		}
	}
	
	...


***

![trace-id](images/trace-id.png)
![zipkin-ui](images/zipkin-ui.png)
![zipkin-error-traces](images/zipkin-error-traces.png)

## Zipkin 整合
	1、启动1个Zipkin Server，具体配置参考spring-cloud-zipkin-server。
	2、在需要向Zipkin Server上报数据的项目中，加入Zipkin的客户端。
		
		
#### 相关应用端口信息：
		config-server 10000
		eureka-server 12345
		person-client  8080
		person-service 9090
		spring-cloud-zuul 7070
		spring-cloud-sleuth	6060
		zipkin-server 10110

### 1、HTTP - 简单，但是效率不高，受网络影响比较
##### 添加Zipkin 客户端依赖：
		<!-- Zipkin 客户端 -->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>



##### 访问后台接口，触发记录日志
	访问http://localhost:11010
	记录的日志会上提交到zipkin server上，之后从zipkin ui上便可以查看相关的链路信息

### 2、Stream - Kafka

### 3、Logger 本地日志（最可靠）