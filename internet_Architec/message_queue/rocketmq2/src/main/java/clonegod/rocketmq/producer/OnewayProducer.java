package clonegod.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 单向发送消息，不关心消息处理结果---适用于对可靠性要求不高的场景，如日志收集。
 * One-way transmission
 * 
 * Application: 
 *  One-way transmission is used for cases requiring moderate reliability, such as log collection.
 *
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception{
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("Group-Producer-Oneway");
        
        // config name server address
        producer.setNamesrvAddr("192.168.1.201:9876");
        
        //Launch the instance.
        producer.start();
        for (int i = 20; i < 30; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */,
                "TagA" /* Tag */,
                ("Msg send from OnewayProducer " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);

        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}