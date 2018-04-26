/**
 * 广播消费模式
 * 	- 消费模式只需要在Consumer端进行设置即可
 * 	- Producer不关心消费模式
 * 	- Consumer端可以选择集群消费（消息被分散到消费集群的不同节点），也可以选择广播消费（每条消息都会送推送给每一个消费者）。
 * 
 * @author clonegod@163.com
 *
 */
package clonegod.rocketmq.broadcast;