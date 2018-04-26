package clonegod.rocketmq.quickstart;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

/**
 * Producer配置参数
 *	- 配置参数存储到数据库（根据每台机器的性能进行差异化配置），启动producer时从数据库读取
 */
public class ProducerConfig {
	public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("UNIQUE_PRODUCER_GROUP_NAME");
        producer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        
        // 消息发送失败时，producer的重试次数
        producer.setRetryTimesWhenSendFailed(10);
        
        // 允许发送的消息最大字节数
        producer.setMaxMessageSize(1024 * 1024 * 5);
        
        // 消息多大的时候，对消息进行压缩
        producer.setCompressMsgBodyOverHowmuch(1024 * 1024 * 5);
        
        // 每隔多长时间与broker进行1次心跳检测（发送心跳包到broker）
        producer.setHeartbeatBrokerInterval(1000 * 30);
        
        
	}
}
