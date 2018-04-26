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

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;


/**
 * Consumer，订阅消息 
 * 
 * 1、创建Consumer实例
 * 	在同一个JVM进程内（tomcat容器），同一个ConsumerGroupName只能绑定到一个Consumer实例上。
 *  说明：只启动1个consumer实例，不代表只有1个线程进行消费！consumer内部是通过线程池提供多线程进行消息的并发消费的！
 *  
 * 2、配置NameSrv地址
 * 
 * 3、订阅Topic，并指定消息过滤的Tag（如果不基于Tag过滤，使用*进行通配）
 * 
 * 4、绑定消息处理器-当消息达到时，对消息进行处理
 * 
 * 5、启动consumer，consumer线程一直运行，不关闭，等待处理消息
 */
public class Consumer {
	
	private static final String UNIQUE_CONSUMER_GROUP_NAME = "CONSUMER_quickstart";

    public static void main(String[] args) throws InterruptedException, MQClientException {
    	// "please_rename_unique_group_name"
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(UNIQUE_CONSUMER_GROUP_NAME);
        
        consumer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        
        // 订阅Topic，可以指定Tag对消息进行二次过滤
        consumer.subscribe("TopicQuickStart", "*");
        
        // MessageListenerConcurrently - 消费端并发消费（消息无序消费的场景）
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());
            	try {
            		// 正常情况下，broker每次仅向每个消费者线程推送1条消息
            		MessageExt msg = msgs.get(0); 
            		String topic = msg.getTopic();
					String msgBody = new String(msg.getBody(), StandardCharsets.UTF_8);
					String tags = msg.getTags();
					System.out.println(String.format("%s - 收到消息：topic=%s, msgBody=%s, tags=%s", 
							Thread.currentThread().getName(), topic, msgBody, tags));
				} catch (Exception e) {
					e.printStackTrace();
					// 消息处理异常时，告诉broker稍后重发消息。broker 内部重发机制： 1s 10s 30s 60s ...
					return ConsumeConcurrentlyStatus.RECONSUME_LATER; 
				}
            	
            	// 确认消息已完成消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.println("Consumer Started.");
    }
}
