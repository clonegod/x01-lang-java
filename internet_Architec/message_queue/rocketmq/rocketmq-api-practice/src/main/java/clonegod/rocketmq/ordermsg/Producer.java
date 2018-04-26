package clonegod.rocketmq.ordermsg;

import java.util.List;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;


/**
 * Producer，发送顺序消息
 */
public class Producer {
    public static void main(String[] args) {
        try {
        	DefaultMQProducer producer = new DefaultMQProducer("PRODUER-ordering");
            producer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
            producer.start();

            String[] tags = new String[] { "TagA", "TagB", "TagC"};
            
            // 0 3 6 TopicA 具有相同的orderId: 0
            // 1 4 7 TopicB 具有相同的orderId: 1
            // 2 5 8 TopicC 具有相同的orderId: 2
            for (int i = 0; i < 9; i++) {
                // 订单ID相同的消息要有序
                int orderId = i % 3; // 订单创建，订单支付，订单完成  3条消息都有相同的订单号
                Message msg =
                		// keys 可以设置为某个具体消息的主键id
                        new Message(Consumer1.TOPIC_NAME, tags[i % tags.length], "KEY" + i,
                            ("Hello RocketMQ_ " + orderId + "_" + i).getBytes());

                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                	/** 对orderId进行取模，选择将消息发送到Topic下的哪个队列 */
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg; // orderId
                        int index = id % mqs.size(); // 按订单号对队列数取模，相同订单号的消息将被分配到同一个队列上
                        return mqs.get(index);
                    }
                }, orderId);

                System.out.println(sendResult);
            }

            producer.shutdown();
        }
        catch (MQClientException e) {
            e.printStackTrace();
        }
        catch (RemotingException e) {
            e.printStackTrace();
        }
        catch (MQBrokerException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
