
## Spring Kafka 相关的类
	KafkaAutoConfiguration	自动装配器
	KafkaTemplate				


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
	
