package clonegod.rocketmq.batch;

import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 
 * @author clonegod@163.com
 *
 */
public class BatchProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("Group-Producer-Broadcast");
        producer.setNamesrvAddr("192.168.1.201:9876");
        producer.start();
    	
        List<Message> messages = getHugMsg();
    	
    	ListSplitter splitter = new ListSplitter(messages);
    	while (splitter.hasNext()) {
    	   try {
    		   // 1次获取1批消息进行发送
    	       List<Message>  listItem = splitter.next();
    	       SendResult sendResult = producer.send(listItem);
    	       System.out.printf("%s%n", sendResult);
    	   } catch (Exception e) {
    	       e.printStackTrace();
    	       //handle the error
    	   }
    	}
    	
    	producer.shutdown();
    }

	private static List<Message> getHugMsg() {
    	String topic = "BatchTopicTest";
    	String tag = "TagA";
    	//boolean waitStoreMsgDone = true;

    	List<Message> messages = new ArrayList<>();
//    	 相同批次的消息应该具有：相同的主题，相同的waitStoreMsgOK
//    	messages.add(new Message(topic, tag, "OrderID001", "Little Msg Of Batch Mode 0".getBytes()));
//    	messages.add(new Message(topic, tag, "OrderID002", "Little Msg Of Batch Mode 1".getBytes()));
//    	messages.add(new Message(topic, tag, "OrderID003", "Little Msg Of Batch Mode 2".getBytes()));
    	for(int i = 10; i < 20; i++) {
    		messages.add(new Message(topic, tag, 
    				"OrderID00" + i, 
    				("Little Msg Of Batch Mode " + i).getBytes()));
    	}
		return messages;
	}
}