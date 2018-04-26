/**
 * 批量发送消息
 * 	- 批量发送消息可提高传递小消息的性能
 * 	- total size of the messages in one batch should be no more than 1MB.
 * 	- 相同批次的消息应该具有：相同的主题，相同的waitStoreMsgOK，不支持scheduler延迟类型消息。
 * 
 * @author clonegod@163.com
 *
 */
package clonegod.rocketmq.batch;