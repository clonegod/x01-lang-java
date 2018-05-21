
## Spring Kafka 相关的类
	KafkaAutoConfiguration	springboot对kafka提供的自动装配器
	KafkaTemplate		spring对kafka原生客户端封装的模板类


## Spring Kafka 配置
	spring.kafka.bootstrap-servers=localhost:9092

## Spring Kafka 配置(生产端-Producer)
	# Kafka 生产者
	#spring.kafka.producer.bootstrap-servers=localhost:9092
	spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
	spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
	
## Spring Kafka 配置(消费端-Consumer)
	# Kafka 消费者
	#spring.kafka.consumer.bootstrap-servers=localhost:9092
	spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
	spring.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer	
	spring.kafka.consumer.group-id=kafka_consumer_group_01

## 预先启动相关服务
	zookeeper 		先启动zookeeper - 默认端口：2181
	kafka server		再启动kafka服务器 - 默认端口：9092

	
---------------------------

## Spring Cloud Stream Binder: Kafka
	注意：
	1、spring-cloud-stream 的 binder有多种实现方式，kafka仅仅是其中的一种。
	2、spring-cloud-stream 是基于Spring Integration: 
		The Spring Cloud Stream project builds on Spring Integration, 
		where Spring Integration is used as an engine for message-driven microservices.


#### MQ 消息的组成
	消息头	Headers
	消息体	Body/Payload
	
## 配置消息驱动Bean
	@Component
	@EnableBinding(Source.class)
	public class MessageProducerBean {
	
		@Autowired
		@Qualifier(Source.OUTPUT) // 限定Bean的名称（如果有多个的话，以示区分）
		private MessageChannel messageChannel;
		
		
		/**
		 * 发送消息
		 */
		public boolean send(String message) {
			// 通过消息管道发送消息
			boolean sendResult = messageChannel.send(MessageBuilder.withPayload(message).build());
			return sendResult;
		}
		
	}

## 配置消息binding
	spring.cloud.stream.bindings.output.destination=${app.kafka.topic}


---------------

## 扩展-自定义Source
	# 声明接口
	public interface MySource {
	
		String MyOUTPUT = "myoutput";
	
		@Output(MySource.MyOUTPUT)
		MessageChannel output();
	}
	
### 在@EnableBinding中，加入自定义的Source
	@Component
	@EnableBinding({Source.class, MySource.class}) // 绑定一个消息管道（Source会注册一个与它关联的Channel到容器中）
	public class MessageProducerBean {
	
		@Autowired
		@Qualifier(Source.OUTPUT) // 限定Bean的名称（如果有多个的话，以示区分）
		private MessageChannel messageChannel;
		
		
		
		@Autowired
		@Qualifier(MySource.MyOUTPUT) // 限定Bean的名称（如果有多个的话，以示区分）
		private MessageChannel myMessageChannel;
		
		
		/**
		 * 发送消息
		 */
		public boolean send(String message) {
			// 通过消息管道发送消息
			boolean sendResult = messageChannel.send(MessageBuilder.withPayload(message).build());
			return sendResult;
		}
		
		/**
		 * 发送消息
		 */
		public boolean sendByMySource(String message) {
			// 通过消息管道发送消息
			boolean sendResult = myMessageChannel.send(MessageBuilder.withPayload(message).build());
			return sendResult;
		}
		
	}


### 配置消息binding
	# 默认提供的source: 名称为output
	spring.cloud.stream.bindings.output.destination=${app.kafka.topic}
	
	# 自定义的source: 名称为myoutput
	spring.cloud.stream.bindings.myoutput.destination=${app.kafka.topic}





