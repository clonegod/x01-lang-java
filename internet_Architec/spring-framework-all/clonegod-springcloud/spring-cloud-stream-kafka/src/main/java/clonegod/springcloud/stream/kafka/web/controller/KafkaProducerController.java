package clonegod.springcloud.stream.kafka.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {

	private final KafkaTemplate<String, String> kafkaTemplate;
	
	private final String topic;

	@Autowired
	public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate, 
									@Value("${app.kafka.topic}") String topic) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}
	
	/**
	 * 写入消息到Kafka
	 * 
	 * @param msg
	 * @return
	 */
	@GetMapping("/kafka/{msg}")
	public Object sendMsg(@PathVariable String msg) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, msg);
		ListenableFuture<SendResult<String,String>> future = kafkaTemplate.send(record);
		try {
			RecordMetadata metadata = future.get().getRecordMetadata();
			Map<String, Object> map = new HashMap<>();
			map.put("topic", metadata.topic());
			map.put("partition", metadata.partition());
			map.put("offset", metadata.offset());
			return map;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
}
