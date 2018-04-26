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
package clonegod.rocketmq.pull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;


/**
 * PullConsumer，订阅消息
 * 	需要在消费端维护每个队列的消息偏移量 
 * 	由于这种方式拉取消息非常慢，不建议使用。
 */
public class PullConsumer {
	// 实际应用中，需要把队列偏移量存储到db中。
    private static final Map<MessageQueue, Long> offseTable = new HashMap<MessageQueue, Long>();


    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("PULLCONSUMER");
        consumer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        consumer.start();
        
        // 获取Topic下的所有队列
        Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("TopicPull");
        
        // 循环每个队列，从队列中拉取消息
        for (MessageQueue mq : mqs) {
            System.out.println("\nConsume from the queue: " + mq);
            SINGLE_MQ: while (true) {
                try {
                	// 从queue中获取数据，需指定每次拉取消息的偏移量，每次最多拉取的消息个数
                    PullResult pullResult =
                            consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    System.out.println(pullResult);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
	                    case FOUND:
	                        List<MessageExt> msgs = pullResult.getMsgFoundList();
	                        for(MessageExt msg : msgs) {
	                        	System.out.println(new String(msg.getBody()));
	                        }
	                        break;
	                    case NO_MATCHED_MSG:
	                        break;
	                    case NO_NEW_MSG:
	                    	System.out.println("队列中暂时没有新的消息，结束当前队列的拉取");
	                        break SINGLE_MQ;
	                    case OFFSET_ILLEGAL:
	                        break;
	                    default:
	                        break;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        consumer.shutdown();
    }


    /**
     * consumer需要维护每个队列拉取消息的偏移量
     * 
     * @param mq
     * @param offset
     */
    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        offseTable.put(mq, offset);
    }

    
    /**
     * 获取下次拉取消息的偏移量
     * 
     * @param mq
     * @return
     */
    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = offseTable.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }

}
