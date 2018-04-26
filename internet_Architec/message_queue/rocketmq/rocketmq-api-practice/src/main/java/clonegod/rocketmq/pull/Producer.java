package clonegod.rocketmq.pull;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class Producer {
	
	private static final String PRODUCER_GROUP_NAME = "PRODUCER_pull";
	
    public static void main(String[] args) throws MQClientException, InterruptedException {
    	
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP_NAME);
        producer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        
        producer.setRetryTimesWhenSendFailed(10); // producer重试消息发送
        
        producer.start();
        
        for (int i = 0; i < 5; i++) {
            try {
                Message msg = new Message("TopicSchedulePull",// topic - 消息所属主题
                    "Tag1",// tag - 主题下的消息二次过滤
                    "key" + i,
                    ("Hello RocketMQ @" + i).getBytes()// body - 消息实体
                        );
                SendResult sendResult = producer.send(msg, 1000);
                System.out.println(sendResult);
                Thread.sleep(1000);
            }
            catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
        
        // 发送消息结束后，关闭producer，释放资源
        producer.shutdown();
    }
}
