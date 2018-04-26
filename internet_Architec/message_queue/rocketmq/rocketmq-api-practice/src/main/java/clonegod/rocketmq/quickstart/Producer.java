/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package clonegod.rocketmq.quickstart;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;


/**
 * Producer，发送消息
 * 要点：
 * 	1、创建Producer 
 * 	创建Producer时，需要指定GroupName
 *  在同一个JVM进程内（tomcat容器），GroupName只能绑定到唯一的一个Producer实例对象上。即不能创建两个相同GroupName的Producer实例！
 *  
 *  2、配置NameSrv地址
 *  
 *  3、创建消息
 *  	消息实体：Topic + Tag + Body
 *  
 *  4、发生消息
 *  
 *  //5、关闭producer
 */
public class Producer {
	
	private static final String UNIQUE_PRODUCER_GROUP_NAME = "PRODUCER_quickstart";
	
    public static void main(String[] args) throws MQClientException, InterruptedException {
    	
        DefaultMQProducer producer = new DefaultMQProducer(UNIQUE_PRODUCER_GROUP_NAME);
        // com.alibaba.rocketmq.client.exception.MQClientException: No name server address, please set it.
        producer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        
        producer.setRetryTimesWhenSendFailed(10); // producer重试消息发送的次数
        
        producer.start();
        
        // 不能创建两个相同GroupName的Producer实例！
        // The producer group[PRODUCER_quickstart] has been created before, specify another name please.
        /**
        DefaultMQProducer producer2 = new DefaultMQProducer(UNIQUE_PRODUCER_GROUP_NAME);
        producer2.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        producer2.start();
		*/
        
        for (int i = 0; i < 10; i++) {
            try {
                Message msg = new Message("TopicQuickStart",// topic - 消息所属主题
                    "TagA",// tag - 主题下的消息二次过滤
                    ("Hello RocketMQ " + i + "@" + System.currentTimeMillis()).getBytes()// body - 消息实体
                        );
                SendResult sendResult = producer.send(msg, 1000);
                System.out.println(sendResult);
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
