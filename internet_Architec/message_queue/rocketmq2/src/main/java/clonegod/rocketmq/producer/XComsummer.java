package clonegod.rocketmq.producer;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 消费者
 * 	-接收Broker推送的消息并处理
 *
 * 注意：需要在消费者中解决重复消费的问题！！！
 */
public class XComsummer {
	
	public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Group-Consumer-X");
        consumer.setNamesrvAddr("192.168.1.201:9876");
        
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("TopicTest", "TagA || TagC || TagD");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, 
					ConsumeConcurrentlyContext context) {
				processMsg(msgs);
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
        });

        consumer.start();

        System.out.printf("Consumer Started......%n");
        
    }
	
	private static void processMsg(List<MessageExt> msgs) {
		for(MessageExt msg : msgs ) {
			System.out.printf(Thread.currentThread().getName() 
					+ " Receive New Messages: " + new String(msg.getBody()) + "%n");
		}
	}
}
