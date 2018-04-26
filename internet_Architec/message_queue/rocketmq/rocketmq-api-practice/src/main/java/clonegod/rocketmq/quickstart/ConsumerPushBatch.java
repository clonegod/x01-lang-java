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
 * Consumer，订阅批量消息 
 * 
 * DefaultMQPushConsumer - 推送模式
 * MessageListenerConcurrently - 并发消费-无序
 * 
 * Consumer端使用DefaultMQPushConsumer，且使用MessageListenerConcurrently进行消息的无序消费
 * 
 * >>> 测试broker 1次推送多条消息给consumer：
 * 1、先启动producer发送2条消息
 * 2、再启动consumer订阅topic，且consumer设置了MessageBatchMaxSize=10，表示consumer端允许1次最多接收10条消息
 * 
 * 由于producer先启动并发送消息到broker了，此时broker上已经存在多条消息了，则consumer可设置1次获取多条消息，加快消息的消费。
 * 需要注意的是，
 * 		1、consumer端是启动多个线程进行消费的并行，每个线程都可能接收到一批消息。
 * 		2、如果某个线程在处理一批消息时，批次中某个消息处理过程中抛出异常，则需要向broker返回RECONSUME_LATER，这种情况下，该线程接收到的整个批次的消息都会认为是全部处理失败的。
 * 		3、那些处理失败的消息，会稍后由broker重新推送给consumer进行处理。
 */
public class ConsumerPushBatch {
	
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
        // producer先启动并发送消息到broker上，然后才启动的consumer，此时就会根据batchMaxSize参数的值进行批量推送（该参数仅控制最多1次推送多少条）
        // 处理批量消息时，如果某一条消息发生异常，向broker返回RECONSUME_LATER，即消息整体回滚，则下次会将该批次的所有消息全部再给consumer消费。
        consumer.setConsumeMessageBatchMaxSize(10);
        
        // MessageListenerConcurrently - 消费端并发消费（消息无序消费的场景）
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
               System.out.println(Thread.currentThread().getName() + " Receive New Messages: " + msgs.size());
            	
            	try {
            		// 批量消息时，msgs list的 size 可能大于1，因此这里使用for循环处理消息
					for(MessageExt msg : msgs) {
						String topic = msg.getTopic();
						String msgBody = new String(msg.getBody(), StandardCharsets.UTF_8);
						String tags = msg.getTags();
						System.out.println(String.format("%s - 收到消息：topic=%s, msgBody=%s, tags=%s", 
								Thread.currentThread().getName(), topic, msgBody, tags));
						if(Math.random() > 0.5) {
							throw new RuntimeException("模拟消息处理异常。消息处理异常：" + msgBody);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					// 消息处理异常时，告诉broker稍后重发消息  1s 10s 30s 60s ...
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
