package clonegod.rocketmq.ordered;

import java.util.List;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class OrderedProducer {
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
    	DefaultMQProducer producer = new DefaultMQProducer("Group-Producer-Order-Msg");
        
        producer.setNamesrvAddr("192.168.1.201:9876");
        
        //Launch the instance.
        producer.start();
        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for(int batch = 0; batch < 3; batch++) {
        	for (int i = 0; i < 10; i++) {
        		int orderId = i % 10;
        		//Create a message instance, specifying topic, tag and message body.
        		Message msg = new Message("OrderedTopicTest", 
        				tags[i % tags.length], 
        				"KEY" + i, // ?
        				(String.format("Ordered Msg From OrderedProducer: batch=%s, orderId=%s ", batch, orderId))
        					.getBytes(RemotingHelper.DEFAULT_CHARSET));
        		SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
        			@Override
        			public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
        				Integer _orderId = (Integer) arg;
        				// 根据业务号（例如订单号）选择消息队列，同一业务号的消息总是发往同一个队列，在同一个队列中使用先进先出的策略即可实现消息的有序处理
        				int index = _orderId % mqs.size();
        				return mqs.get(index); // 根据业务号得到消息在队列中的索引位，从而获取到对应的队列
        			}
        		}, orderId);
        		System.out.printf("%s%n", sendResult);
        	}
        }
        //server shutdown
        producer.shutdown();
    }
}