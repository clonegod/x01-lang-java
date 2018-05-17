package clonegod.kafka.client.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import clonegod.kafka.client.KafkaProperties;

/**
 * 生产者异步发送消息
 * 
 */
public class ProducerSendAsync extends Thread {

    private final KafkaProducer<Integer, String> producer;
    private final String topic;

    public ProducerSendAsync(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaProperties.KAFKA_SERVER_URL_LIST);
        props.put("client.id", "DemoProducer1"); // 生产者的标识
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    public void run() {
        int messageNo = 1;
        while (true) {
            String messageStr = "Message_" + messageNo;
            long startTime = System.currentTimeMillis();
            // Send asynchronously
            producer.send(new ProducerRecord<>(topic,
            		messageNo,
            		messageStr), new SendMsgCallBack(startTime, messageNo, messageStr));
            ++messageNo;
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
    
	public static void main(String[] args) {
		ProducerSendAsync producerThread = new ProducerSendAsync(KafkaProperties.TOPIC);
		producerThread.start();
	}

}

