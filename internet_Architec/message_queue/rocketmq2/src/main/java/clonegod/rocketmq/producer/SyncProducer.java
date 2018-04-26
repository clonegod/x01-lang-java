package clonegod.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 发送“同步”消息
 * Reliable synchronous transmission
 * 
 * Application: 
 *  Reliable synchronous transmission is used in extensive scenes, 
 * such as important notification messages, SMS notification, SMS marketing system, etc..
 *  在大多数场景中使用可靠的“同步”传输，如：重要通知信息，短信通知，短信营销系统等
 */
public class SyncProducer {
	
    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("Group-Producer-Sync");
        
        // config name server address
        producer.setNamesrvAddr("192.168.1.201:9876");
        
        //Launch the instance.
        producer.start();
        for (int i = 0; i < 10; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */,
                "TagA" /* Tag */,
                ("Msg send from SyncProducer " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
