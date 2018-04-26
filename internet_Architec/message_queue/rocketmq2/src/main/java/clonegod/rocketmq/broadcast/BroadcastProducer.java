package clonegod.rocketmq.broadcast;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class BroadcastProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("Group-Producer-Broadcast");
        producer.setNamesrvAddr("192.168.1.201:9876");
        producer.start();

        for (int i = 0; i < 10; i++){
            Message msg = new Message("TopicTest",
                "TagA",
                "OrderID188",
                ("This is a Broadcast Msg-" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}