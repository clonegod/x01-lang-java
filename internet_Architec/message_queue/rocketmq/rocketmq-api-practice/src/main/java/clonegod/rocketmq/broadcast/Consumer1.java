package clonegod.rocketmq.broadcast;

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
 * 增加积分的业务端
 *
 */
public class Consumer1 {
	
	// 集群消费时，消费端的GroupName要相同
	public static final String CONSUMER_GROUP_NAME = "CONSUMER_broadcast";
	public static final String TOPIC_NAME = "TOPIC-Broadcast";
	
	public void start() {
        try {
        	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        	consumer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        	consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        	// 订阅Topic，可以指定Tag对消息进行二次过滤
			consumer.subscribe(TOPIC_NAME, "Tag1 || Tag2 || Tag3");
			
			/** consumer以广播消费模式订阅消息 */
			consumer.setMessageModel(MessageModel.BROADCASTING);
			
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
					System.out.println(String.format("%s - Consumer1收到消息：topic=%s, msgBody=%s, tags=%s", 
							Thread.currentThread().getName(), topic, msgBody, tags));
					System.out.println("客户已付款，增加积分");
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
    	new Consumer1().start();
        System.out.println("Consumer1 Started.");
    }
}
