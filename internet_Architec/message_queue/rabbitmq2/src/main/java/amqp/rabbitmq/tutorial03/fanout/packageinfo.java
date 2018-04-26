package amqp.rabbitmq.tutorial03.fanout;

/**
 * Exchange 模型 - fanout
 * 	- it's only capable of mindless broadcasting. 疯狂地广播任意消息，所有绑定到该Exchange的队列都会收到消息。
 * 
 * 1条消息被多个客户端处理---核心：每个客户端接受到相同的消息副本，但是各自的处理逻辑不同
 * 	客户端A - 记录日志到文件
 * 	客户端B - 打印日志到控制台
 */
