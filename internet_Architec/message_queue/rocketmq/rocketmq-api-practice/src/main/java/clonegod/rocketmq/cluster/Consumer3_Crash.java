package clonegod.rocketmq.cluster;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragelyByCircle;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * 模拟consumer集群消费中的某个consumer宕机
 *	- 如果宕机，broker将会把消息推送给同组的其它consumer处理
 */
public class Consumer3_Crash {
	
	public void start() {
        try {
        	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Consumer1.CONSUMER_GROUP_NAME);
        	consumer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        	consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        	// 订阅Topic，可以指定Tag对消息进行二次过滤
			consumer.subscribe(Consumer1.TOPIC_NAME, "Tag1 || Tag2 || Tag3");
			// 集群消费模式
			consumer.setMessageModel(MessageModel.CLUSTERING);
			// 消息分配策略-轮流发送到集群到每个consumer
			consumer.setAllocateMessageQueueStrategy(new AllocateMessageQueueAveragelyByCircle());
			// 注册消息处理器
			consumer.registerMessageListener(new Listener());
			// 启动consumer实例
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	/**
	 * MessageListenerConcurrently - 消费端并发消费（消息无序消费的场景）
	 */
	class Listener implements MessageListenerConcurrently {

		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());
         	try {
         		// 正常情况下，broker每次仅向每个消费者线程推送1条消息
         		MessageExt msg = msgs.get(0); 
         		String topic = msg.getTopic();
					String msgBody = new String(msg.getBody(), StandardCharsets.UTF_8);
					String tags = msg.getTags();
					System.out.println(String.format("%s - Consumer3收到消息：topic=%s, msgBody=%s, tags=%s", 
							Thread.currentThread().getName(), topic, msgBody, tags));
					
					System.err.println("模拟消息处理中，consumer发生宕机！！！");
					System.out.println("broker检测到consumer宕机后，会将消息推送给同组的其它consumer处理");
					Thread.sleep(30000);
					System.exit(1);
					
				} catch (Exception e) {
					e.printStackTrace();
					// 消息处理异常时，告诉broker稍后重发消息。broker 内部重发机制： 1s 10s 30s 60s ...
					return ConsumeConcurrentlyStatus.RECONSUME_LATER; 
				}
         	
         	// 确认消息已完成消费
             return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
		
	}

    public static void main(String[] args) throws InterruptedException, MQClientException {
    	new Consumer3_Crash().start();
        System.out.println("Consumer3 Started.");
    }
}
