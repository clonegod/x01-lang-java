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
package clonegod.rocketmq.transaction;

import java.util.concurrent.TimeUnit;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.Message;


/**
 * 发送事务消息例子
 * 
 */
public class TransactionProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException {

        final TransactionMQProducer producer = new TransactionMQProducer("Producer_Transaction");
        producer.setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        /**
        // 未决事务的配置 - 开源版本未提供实现！
        producer.setCheckThreadPoolMinSize(2); // 事务回查最小并发数
        producer.setCheckThreadPoolMaxSize(2); // 事务回查最大并发数
        producer.setCheckRequestHoldMax(2000);  // 队列数
        */
        producer.setTransactionCheckListener(new TransactionCheckListenerImpl());
        producer.start();

        // 本地事务执行器，由客户端回调
        TransactionExecuterImpl tranExecuter = new TransactionExecuterImpl();
        
        for (int i = 1; i <= 2; i++) {
            try {
                Message msg =
                        new Message(Consumer.TOPIC_NAME,
                        		"Transaction"+i, 
                        		"KEY" + i,
                            ("Hello RocketMQ " + i).getBytes());
                SendResult sendResult = producer.sendMessageInTransaction(msg, tranExecuter, "attach");
                System.out.println(sendResult);
            } catch (MQClientException e) {
                e.printStackTrace();
            }
            
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        
        /**
         * 应用退出时，调用shutdown来清理资源，关闭网络连接，从MQ服务器上注销本producer
         * 注意：建议在应用TOMCAT容器的退出钩子里调用producer的shutdown方法。
         */
        Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				producer.shutdown();
			}
        });

    }
}
