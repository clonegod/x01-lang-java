# rocketmq - 分布式事务
使用消息中间件来实现，解决高并发时的消息处理效率问题。

**注意：**

开源版本3.2.6对分布式事务提供部分支持！

broker上实现的"未确认prepared消息的回查"机制被砍掉/闭源了!

因此，producer端如果对事务消息进行确认时，如果发送失败，开源版本是没有提供解决方案的。

## rocketmq分布式事务底层实现原理
### 第一部分：producer <-> MQ
1、producer发送prepared预处理消息到broker上，此时消息对consumer端不可见；

2、broker收到消息后，回调producer的callback方法，producer在callback方法中执行本地事务；

本地事务执行完成后，向broker返回确认消息：

	a、producer本地事务执行成功，则返回COMMIT_MESSAGE
	b、producer本地事务执行失败，则返回ROLLBACK_MESSAGE

![Image of rocketmq-transaction](https://github.com/clonegod/tools/blob/master/images/rocketmq-transaction.png)

如果producer发送的确认消息发送失败（网络原因或producer宕机）：

则由“未决事务，服务器回查客户端”机制进行处理（未开源）：
	
	未决事务所对应的事务消息的状态为：LocalTransactionState.UNKNOW
	在broker上通过定时job检查那些未收到确认的prepared消息，
	然后，broker向producer确认这些未决消息的本地事务执行结果是成功还是失败：
	producer通过回调方法中的msg获取到事务消息的key，然后查询数据库，便可知道事务执行结果。

这样就保证了producer的本地事务和broker上的prepared消息处理逻辑的一致/正确性。
	
producer端本地事务的执行结果与broker上的事务消息在整体上保持逻辑一致：

	A事务在producer上执行成功，则消息必须对consumer端可见；
	A事务在producer上执行失败，则消息必须对consumer端不可见；

只要能够保证producer的事务执行和broker上的事务消息的一致，就已经完成分布式事务的第一步，也是最重要的一步。

接下来，就是由broker将事务消息发送给相关的consumer来消费，

### 第二部分：MQ <-> consumer
在consumer端的消费就是分布式事务的第二部分。

	如果consumer处理事务成功，则整个分布式事务正确执行并结束。
	如果consumer处理事务失败，则通过“人工介入”或者其它补偿方式来处理。

从MQ到consumer，可能发生以下几种情况：

	consumer发生异常：broker会重发消息给consumer，需考虑重复消费+幂等性。
	consumer宕机：broker会重发消息给同组的consumer，需考虑重复消费+幂等性。
	consumer消费失败（异常消息）：consumer记录日志，通知producer消息有问题

### 总结
	分布式事务第一部分，由producer和MQ结合起来保障本地事务与事务消息的一致性
	分布式事务第二部分，由MQ的消息重试机制来保证消息被consumer消费到。
	分布式事务的第一部分执行不需要考虑第二部分事务的结果。

分布式事务的特点：

1、A事务(阶段一)执行时不考虑B事务（阶段二），只需要保证A事务与MQ上的prepared消息处理逻辑一致即可；

2、由MQ提供的消息重试机制来保证事务消息一定会被consumer端消费到。

3、B事务如果处理失败（几率比较小），可通过“人工介入”方式进行补偿处理。