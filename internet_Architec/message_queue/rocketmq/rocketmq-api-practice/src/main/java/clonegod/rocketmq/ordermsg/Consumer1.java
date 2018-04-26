package clonegod.rocketmq.ordermsg;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

public class Consumer1 {
	
	// 集群消费时，消费端的GroupName要相同
	public static final String CONSUMER_GROUP_NAME = "CONSUMER_ordering";
	public static final String TOPIC_NAME = "Topic-ordering";
	
	public void start() {
        try {
        	DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        	consumer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        	
        	consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        	// 订阅Topic，可以指定Tag对消息进行二次过滤
			consumer.subscribe(TOPIC_NAME, "*");
			
			// 设置消费端的线程池数量
			// 消费线程池的最小值：默认10
			consumer.setConsumeThreadMin(10);
			// 消费线程池的最大值：默认20
			consumer.setConsumeThreadMax(20);

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
	 * 有序消息的底层实现:
	 * 	1、producer的保证：orderId按队列个数取模，选择将消息推送到某个队列，实现具有相同orderId的消息被放到同一个队列中；
	 * 	2、broker端的保证：将消息按顺序发送给consumer；
	 *  3、consumer端的保证：消费线程与队列是具有绑定关系的，一个队列的数据仅能被一个专门的线程来消费。
	 *  基于以上保证，对于相同orderId的消息，由于分配在了同一个队列，在consumer端又是由单线程处理该队列上的消息，因此，确保了消息的顺序消费。
	 *  另外，在consumer端通过线程池并发消费消息，实现了不同orderId消息被并发消费，相同orderId的消息被有序消费！
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
    	new Consumer1().start();
        System.out.println("Consumer1 Started.");
    }
}
