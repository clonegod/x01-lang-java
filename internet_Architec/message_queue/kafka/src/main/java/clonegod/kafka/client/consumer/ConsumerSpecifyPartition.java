package clonegod.kafka.client.consumer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import clonegod.kafka.client.KafkaProperties;
import kafka.utils.ShutdownableThread;

/**
 * 消费端指定某个分区的消息进行消费
 *
 */
public class ConsumerSpecifyPartition extends ShutdownableThread {


    private final KafkaConsumer<Integer, String> consumer;
    private final String topic;

    public ConsumerSpecifyPartition(String topic) {
        super("KafkaConsumerExample", false);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL_LIST);
        // 消费端所属的组
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "AnotherConsumerGroup");
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
    
    /**
     * 指定某个分区进行消费
     * @param partition 分区编号
     */
    private void setPartitionNo(int partition) {
        TopicPartition p0 = new TopicPartition(this.topic, partition);
        consumer.assign(Arrays.asList(p0));
    }

    @Override
    public void doWork() {
    	// 由于已经指定了固定消费某个分区，因此这里不能进行subscribe订阅 - 订阅是对所有分区进行配置的。
        // consumer.subscribe(Collections.singletonList(this.topic));
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
		ConsumerSpecifyPartition consumerThread = new ConsumerSpecifyPartition(KafkaProperties.TOPIC);
		consumerThread.setPartitionNo(0);
		consumerThread.start();

	}


}
