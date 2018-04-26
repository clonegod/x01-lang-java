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
 * Consumer，订阅消息，消息重试逻辑
 * 
 * DefaultMQPushConsumer - 推送模式
 * MessageListenerConcurrently - 并发消费-无序
 * 
 * Consumer端使用DefaultMQPushConsumer，且使用MessageListenerConcurrently进行消息的无序消费
 * 
 * >>> 测试broker 1次推送1条消息给consumer，并对消费时发生的异常进行处理
 * 1、启动consumer订阅topic
 * 2、再先启动producer发送2条消息
 */
public class ConsumerRetryLogic {
	
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
        
        // 客户端批量消费消息
        // - 正常情况下，应该是consumer先启动并对Topic进行订阅，然后producer再发送消息到broker。这种情况下。consumer每次仅能获取1个消息进行消费。
        // - 另一种情况是，producer先启动并发送消息到broker上，然后才启动的consumer，此时就会根据batchMaxSize参数的值进行批量推送（该参数仅控制最多1次推送多少条）
        //   处理批量消息时，如果某一条消息发生异常，向broker返回RECONSUME_LATER，则下次会将该批次的所有消息全部再给consumer消费，即消息整体回滚。
        consumer.setConsumeMessageBatchMaxSize(10);
        
        // MessageListenerConcurrently - 消费端并发消费（消息无序消费的场景）
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            	System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());
            	// 正常情况下，broker每次仅向每个消费者线程推送1条消息
            	MessageExt msg = msgs.get(0); 
            	try {
            		String topic = msg.getTopic();
					String msgBody = new String(msg.getBody(), StandardCharsets.UTF_8);
					String tags = msg.getTags();
					System.out.println(String.format("%s - 收到消息：topic=%s, msgBody=%s, tags=%s", 
							Thread.currentThread().getName(), topic, msgBody, tags));
					
					// 模拟异常消息
					if(msgBody.contains("Hello RocketMQ 2")) {
						System.out.println("=============");
						System.out.println(msg);
						System.out.println("=============");
						int i = 1 / 0;
					}
				} catch (Exception e) {
					e.printStackTrace();
					// 重试若干次之后，对消息进行确认，不再重试了
					if(msg.getReconsumeTimes() < 2) {
						// 消息处理异常时，告诉broker稍后重发消息。broker 内部重发机制： 1s 10s 30s 60s ...
						return ConsumeConcurrentlyStatus.RECONSUME_LATER; 
					} else {
						System.err.println("消息重试2次都没有成功，不再重试。");
						System.out.println("开始记录日志到文件或数据库，由定时任务处理异常消息...");
						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
					}
				}
            	
            	// 确认消息已完成消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();

        System.out.println("Consumer Started.");
    }
}
