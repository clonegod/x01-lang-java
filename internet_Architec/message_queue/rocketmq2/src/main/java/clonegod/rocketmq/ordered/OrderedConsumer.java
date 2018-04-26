package clonegod.rocketmq.ordered;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
/**
 * 测试消息的有序接收：
 * 	1. 先启动3个客户端进行消息的消费
 *  2. 分别查看3个客户端收到的消息，是否按消息编号进行消费的（Broker是否按有序的方式推送消息到Consumer）
 *  3. 不同批次下的具有相同业务号的消息，应该有序到达Consumer（业务号相同，批次号递增）
 * 
 * @author clonegod@163.com
 *
 */
public class OrderedConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("Group-Consumer-Order-Msg");
        consumer.setNamesrvAddr("192.168.1.201:9876");
        
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("OrderedTopicTest", "TagA || TagC || TagD");

        consumer.registerMessageListener(new MessageListenerOrderly() {

            AtomicLong consumeTimes = new AtomicLong(0);
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
                context.setAutoCommit(false);
//                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
                processMsg(msgs);
                this.consumeTimes.incrementAndGet();
                // 模拟不同场景：根据处理消息的个数，返回不同的消息处理结果
//                if ((this.consumeTimes.get() % 2) == 0) {
//                    return ConsumeOrderlyStatus.SUCCESS;
//                } else if ((this.consumeTimes.get() % 3) == 0) {
//                    return ConsumeOrderlyStatus.ROLLBACK;
//                } else if ((this.consumeTimes.get() % 4) == 0) {
//                    return ConsumeOrderlyStatus.COMMIT;
//                } else if ((this.consumeTimes.get() % 5) == 0) {
//                    context.setSuspendCurrentQueueTimeMillis(3000);
//                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
//                }
                return ConsumeOrderlyStatus.SUCCESS;

            }
        });

        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
    
	private static void processMsg(List<MessageExt> msgs) {
		for(MessageExt msg : msgs ) {
			System.out.printf(Thread.currentThread().getName() 
					+ " Receive New Messages: " + new String(msg.getBody()) + "%n");
		}
	}
}