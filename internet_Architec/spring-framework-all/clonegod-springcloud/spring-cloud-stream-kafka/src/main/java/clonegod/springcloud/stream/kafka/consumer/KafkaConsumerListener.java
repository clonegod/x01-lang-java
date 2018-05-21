package clonegod.springcloud.stream.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka 消费端监听器
 *
 */
@Component
public class KafkaConsumerListener {
	
	@KafkaListener(topics= {"${app.kafka.topic}"})
	public void onMessage(String message) {
		System.err.println("----------- Kafka消费端接听器，接收到消息：" + message);
	}
	
}
