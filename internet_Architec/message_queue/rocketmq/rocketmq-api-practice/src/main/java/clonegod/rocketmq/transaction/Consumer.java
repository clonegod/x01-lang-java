package clonegod.rocketmq.transaction;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

public class Consumer {
	
	public static final String CONSUMER_GROUP_NAME = "CONSUMER_transaction";
	public static final String TOPIC_NAME = "Topic-transaction";
	
	public void start() {
        try {
        	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        	consumer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
			consumer.subscribe(TOPIC_NAME, "*");
			consumer.registerMessageListener(new Listener());
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
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
					System.out.println(String.format("%s - Consumer收到消息：topic=%s, msgBody=%s, tags=%s", 
							Thread.currentThread().getName(), topic, msgBody, tags));
				} catch (Exception e) {
					e.printStackTrace();
					return ConsumeConcurrentlyStatus.RECONSUME_LATER; 
				}
         	
         	// 确认消息已完成消费
             return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
		
	}

    public static void main(String[] args) throws InterruptedException, MQClientException {
    	new Consumer().start();
        System.out.println("Transaction Consumer Started.");
    }
}
