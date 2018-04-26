package amqp.rabbitmq.tutorial02.workqueue;

/**
 * NewTask创建一个work queue，由多个Worker实例从同一个队列里获取任务进行处理。
 * Broker模式使用Round-Robin策略进行消息投递。
 * ---每个消息只会被1个消费者处理。
 */
