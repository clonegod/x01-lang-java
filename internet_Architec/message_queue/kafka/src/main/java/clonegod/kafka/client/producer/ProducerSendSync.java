package clonegod.kafka.client.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import clonegod.kafka.client.KafkaProperties;

/**
 * 生产者同步发送消息
 * 
 */
public class ProducerSendSync extends Thread {

    private final KafkaProducer<Integer, String> producer;
    private final String topic;

    public ProducerSendSync(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaProperties.KAFKA_SERVER_URL_LIST);
        props.put("client.id", "DemoProducer2"); // 生产者的标识
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    public void run() {
        int messageNo = 1;
        while (true) {
            String messageStr = "Message_" + messageNo;
            // Send synchronously
            try {
            	RecordMetadata metadata = 
                producer.send(new ProducerRecord<>(topic,
                    messageNo,
                    messageStr)).get();
                System.out.println("Sent message: (" + messageNo + ", " + messageStr + "), partition=" + metadata.partition());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            ++messageNo;
            
            try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
    }
    
	public static void main(String[] args) {
		ProducerSendSync producerThread = new ProducerSendSync(KafkaProperties.TOPIC);
		producerThread.start();
	}

}
