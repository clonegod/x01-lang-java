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

import clonegod.springcloud.stream.kafka.stream.producer.MessageProducerBean;

@RestController
public class KafkaProducerController {

	private final KafkaTemplate<String, String> kafkaTemplate;
	
	private final String topic;
	
	private final MessageProducerBean messageProducerBean;

	@Autowired
	public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate, 
									MessageProducerBean messageProducerBean,
									@Value("${app.kafka.topic}") String topic) {
		this.kafkaTemplate = kafkaTemplate;
		this.messageProducerBean = messageProducerBean;
		this.topic = topic;
	}
	
	/**
	 * 通过{@link KafkaTemplate } 发送消息到Kafka
	 * 
	 * @param msg
	 * @return
	 */
	@GetMapping("/kafkaTemplate/{msg}")
	public Object sendMsgByKafkaTemplate(@PathVariable String msg) {
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
	
	/**
	 * 通过{@link MessageProducerBean } 发送消息到Kafka
	 * @param msg
	 * @return
	 */
	@GetMapping("/kafkaStream/{msg}")
	public Object sendMsgByMessageBean(@PathVariable String msg) {
		boolean success = false;
		try {
			success = messageProducerBean.send(msg);
			Map<String, Object> map = new HashMap<>();
			map.put("success", success);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * 通过{@link MessageProducerBean2 } 发送消息到Kafka
	 * @param msg
	 * @return
	 */
	@GetMapping("/kafkaStream2/{msg}")
	public Object sendMsgByMyMessageBean(@PathVariable String msg) {
		boolean success = false;
		try {
			success = messageProducerBean.sendByMySource(msg);
			Map<String, Object> map = new HashMap<>();
			map.put("success", success);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
}
