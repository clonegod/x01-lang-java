package clonegod.kafka.client.consumer;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import clonegod.kafka.client.KafkaProperties;
import kafka.utils.ShutdownableThread;

/**
 * 消费端自动确认消息消费（一旦消息发送给consumer就认为消费成功，不关心消费端是否成功处理消息）
 * 	消息确认---即提交当前消费消息的offset偏移量
 * 已被消费的消息由于提交了offset到 kafka broker，因此被确认的offset所对应的消息将不会再被同组的consumer消费到.
 *
 */
public class ConsumerAutoCommit  extends ShutdownableThread {

    private final KafkaConsumer<Integer, String> consumer;
    private final String topic;

    public ConsumerAutoCommit(String topic) {
        super("KafkaConsumerExample", false);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL_LIST);
        // 消费端所属的组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer1");
        // 自动提交消息offset
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 自动提交的时间间隔
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // 设置心跳时间
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        // 设置offset偏移量的重置策略
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // or latest
        // 设置key和value的反序列化对象
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
    }

    @Override
    public void doWork() {
        consumer.subscribe(Collections.singletonList(this.topic));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        for (ConsumerRecord<Integer, String> record : records) {
            System.out.println("["+ record.partition() +"]" + 
            			"Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
        }
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public boolean isInterruptible() {
        return false;
    }
    
	public static void main(String[] args) {
		ConsumerAutoCommit consumerThread = new ConsumerAutoCommit(KafkaProperties.TOPIC);
		consumerThread.start();

	}

}
