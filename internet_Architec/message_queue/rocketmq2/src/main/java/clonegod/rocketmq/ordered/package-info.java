/**
 * 有序消息
 * RocketMQ provides ordered messages using FIFO order.
 * 
 * 实现原理：
 * 	每个消息都有唯一的业务编号，根据业务编号%队列个数，得到该业务号下的消息应该发送到哪个队列上。
 *  同一个业务号下，不管有多少条消息，都往一个队列推送，队列使用FIFO策略，即可保证消息的有序消费。
 * 
 * 注：需要客户端处理重复消息的问题！！！
 * 
 * @author clonegod@163.com
 * 
 */
package clonegod.rocketmq.ordered;