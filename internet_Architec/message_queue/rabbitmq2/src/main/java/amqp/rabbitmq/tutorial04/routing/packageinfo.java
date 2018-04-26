package amqp.rabbitmq.tutorial04.routing;

/**
 * Exchange 模型 - Direct
 * 
 * routingKey: receiver can selectively receiving message.
 * 
 * 发送方 binding：
 * 	Exchange + routingKey // 发送方没有指定队列，在接收方创建队列之前，在routingKey规则下向Exchange发送的消息都会丢失。
 * 
 * 接收方 binding：
 * 	Exchange + routingKey + Queue
 * 
 * binding key depends on the exchange type：
 * 	- fanout 类型的 exchanges, 会忽略binding key.
 * 	- direct 只可以绑定简单的规则，只能从1个维度设置绑定规则：eg. info, warning, error
 * 	- topic	可设置多条件规则，可以从多个维度进行规则的设置：eg. kenel.logs.*, cron.logs.error
 * 
 * Direct
 * 	1条消息只被1个客户端处理
 */
