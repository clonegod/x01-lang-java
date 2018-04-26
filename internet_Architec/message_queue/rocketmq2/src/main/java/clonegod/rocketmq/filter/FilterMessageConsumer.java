package clonegod.rocketmq.filter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
 import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
 import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
 import org.apache.rocketmq.common.message.MessageExt;
 import java.util.List;
    
 public class FilterMessageConsumer {
    
     public static void main(String[] args) throws Exception {
         // Instantiate message consumer
         DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Group-Consumer-Filter");
         consumer.setNamesrvAddr("192.168.1.201:9876");
         
         // only subsribe messages have property a, also a >=0 and a <= 3
         consumer.subscribe("TopicTestFilter", MessageSelector.bySql("seqNo BETWEEN 0 AND 3"));
         
         // Register message listener
         consumer.registerMessageListener(new MessageListenerConcurrently() {
             @Override
             public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext context) {
            	 processMsg(messages);
                 return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
             }
         });
         // Launch consumer
         consumer.start();
         System.out.printf("Filter Consumer Started.%n");
     }
     
 	private static void processMsg(List<MessageExt> msgs) {
		for(MessageExt msg : msgs ) {
			System.out.printf(Thread.currentThread().getName() 
					+ " Receive New Messages: " + new String(msg.getBody()) + "%n");
		}
	}
 }