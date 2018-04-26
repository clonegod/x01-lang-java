package amqp.rabbitmq.tutorial05.topic;

/**
 * Exchange 模型 - Topic
 * 
 * Topic 可定制多条件规则：
 * 	reouteKey可由多个单词组成，使用点号作为分隔符，且routingKey长度不超过255bytes
 * 	"接收方"可使用特殊字符来配置bindingKey，使用点号作为分隔符：
 * 	* (star) can substitute for exactly one word.
 * 	# (hash) can substitute for zero or more words.
 * 
 * Topic的灵活之处：
 * 	1. 绑定规则可以从多个维度进行设置。
 * 	2. 接收方设置bindingKey=#，可实现fanout的效果，接收到exchange上所有的消息
 * 	3. 接收方设置bindingKey没有使用通配符时（*、#），可实现direct的效果，只接收exchange上对应reouteKey上的消息
 * 
 * Topic
 * 	1条消息可以被多个订阅的客户端接收
 */
