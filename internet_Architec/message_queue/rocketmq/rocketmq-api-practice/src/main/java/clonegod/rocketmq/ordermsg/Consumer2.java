package clonegod.rocketmq.ordermsg;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

public class Consumer2 {
	
	public void start() {
        try {
        	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Consumer1.CONSUMER_GROUP_NAME);
        	consumer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        	
        	consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        	// 订阅Topic，可以指定Tag对消息进行二次过滤
			consumer.subscribe(Consumer1.TOPIC_NAME, "*");
			
			// 注册消息处理器
			consumer.registerMessageListener(new Listener());
			
			// 启动consumer实例
			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	/**
	 * MessageListenerOrderly - 消费端并发且有序消费
	 */
	class Listener implements MessageListenerOrderly {

		 @Override
         public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
             context.setAutoCommit(true);
             
             try {
            	 for(MessageExt msg : msgs) {
            		 System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msg 
            				 + ", content=" + new String(msg.getBody()));
            		 Thread.sleep(10);
            	 }
			} catch (Exception e) {
				e.printStackTrace();
				return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
			}

             return ConsumeOrderlyStatus.SUCCESS;
         }
	}

    public static void main(String[] args) throws InterruptedException, MQClientException {
    	new Consumer2().start();
        System.out.println("Consumer2 Started.");
    }
}
