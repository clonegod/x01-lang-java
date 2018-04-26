package clonegod.rocketmq.quickstart;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * Consumer配置参数
 *  - 配置参数存储到数据库（根据每台机器的性能进行差异化配置），启动Consumer时从数据库读取
 */
public class ConsumerConfig {
	public static void main(String[] args) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("CONSUMER_GROUP_NAME");
		
		// 设置Namesrv地址
		consumer.setNamesrvAddr("ip1:port1;ip2:port2");
		
		/** Push类型consumer */
		// consumer - consumer启动后，默认从队列的什么位置开始消费，默认CONSUME_FROM_LAST_OFFSET
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		
		// consumer - 消费线程池的最小，最大线程数
		consumer.setConsumeThreadMin(10);
		consumer.setConsumeThreadMax(20);
		
		// consumer - 批量消费，一次消费多少条消息，默认1条
		consumer.setConsumeMessageBatchMaxSize(1);
		
		/** Pull类型consumer */
		DefaultMQPushConsumer pullconsumer = new DefaultMQPushConsumer("CONSUMER_GROUP_NAME");
		// 1次最大拉取多少条消息，默认32条
		pullconsumer.setPullBatchSize(32);
		// 拉取消息，本地队列缓存的消息最大数
		pullconsumer.setPullThresholdForQueue(1000);
		// 消息拉取线程每隔多久拉取1次消息，默认0
		pullconsumer.setPullInterval(0);
		
	}
}
