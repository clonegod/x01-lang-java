# 说明
rocketmq开源版本3.2.6对分布式事务仅提供部分支持，砍掉了“producer发送确认消息失败时，由broker主动进行本地事务执行结果的回查”这一功能。

也就是说，使用rocketmq的开源版本做分布式事务，当“确认消息”丢失的情况发生时，rocketmq时不会进行“未决事务本地回查”的！！！

producer端发送确认消息失败的问题，虽然是小概率事件（网络原因，服务器宕机等因素），但是涉及到money，就必须想办法解决！

以下的内容就是对如何利用开源版本的rocketmq提供的不完整的分布式事务功能，结合其它的手段来实现一个比较可行的方案，提供类似“未决事务本地回查”的功能。

### 解决rocketmq分布式事务的关键思路
	1、依赖数据库提供的ACID特性来实现。
	将业务数据的操作与消息状态的记录，都放到同一个数据源的一个事务中进行
	通过数据库来提供数据存储的原子性、一致性保证。
	2、最好不要引入第三方的技术框架来解决rocketmq的分布式事务问题
	因为涉及到不同类型的数据源，要保证不同数据源中的数据强一致性非常困难！
	比如redis存一份，mysql存一份，但没有可靠的机制来提供数据一致性的保证。


consumer端将已经消费成功的消息，发送给producer，producer对这些消息进行标记。

### 具体办法
绕过rocketmq的”未决事务消息回查“机制，直接在consumer端与producer端进行"对账"

consumer启动定时任务，定时发送已消费的消息给producer，producer接收到消息后，更新对应消息为已确认消费成功。

关键点：consumer每次向producer发送的消息不能重复。

producer启动定时任务，定时检查那些未被确认消费成功的消息，根据条件有选择的进行消息重发。

关键点：producer应该以最合理的方式来判断一条消息是否应该重发。

========================================================================
## consumer端启动一个定时任务
定时扫描该表，每次根据last_check_time抽取一批消息，发送给producer。
##### 消费端抽取逻辑：
	第1次，全部抽取: 
		taskid xxx , last_check_time=1900-01-01 00:00:00
		# 查询数据库当前时间
		curtime = select unix_timestamp(now()) 
		# 查询所有已消费的记录
		select * from balance where update_time > last_check_time
		# 更新
		update confirm set last_check_time = curtime where update_time <= curtime 

	第2次，从上一次抽取时间后开始:
		# 查询数据库当前时间
		curtime = select unix_timestamp(now()) 
		# 查询所有已消费的记录
		select * from balance where update_time > last_check_time
		# 更新
		update confirm set last_check_time = curtime where update_time <= curtime 


**重要**

由于rocketmq原本就无法避免消息重复消费的问题，因此，对于consumer端来讲，必须实现消息的幂等性消费！！！


=================================================================================
## producer端启动一个定时任务
### producer端check逻辑
筛选出consumer_confirm =0的消息，这些消息都是没有被consumer消费确认的消息。

producer接收到consumer的确认消费通知消息后，更新对应消息的状态为"已确认消费成功"。

对于producer端未得到确认的消息，分两种情况：

	1、确认消息发送失败，消费对consumer端不可见，consumer端无法消费此消息。
	=> producer向MQ重新提交消息。===这就是要解决的关键问题所在！
	
	2、消息还在MQ上堆积，或者正在发送到consumer，或正在被消费，
		或者不在consumer本次批量check的范围内。

那么producer端未得到确认的消息到底属于哪种情况呢？

因为第1种情况，肯定是需要重发消息的，而第2种情况又无法确定，

因此，解决办法就是，尽可能减少由于第2种情况所导致的消息重发！


**avg_circle_time** 

一条消息从发送确认消息到broker，broker到consumer，consumer处理消息，consumer发送确认消息给producer，整个流程所需的平均时间。

	查询条件 consumer_confirm = 0 and update_time + avg_circle_time > current_time
	--- 满足该条件的消息，可能是正在被consumer处理中，忽略这些消息。

	查询条件 consumer_confirm = 0 and update_time + avg_circle_time <= current_time
	--- 满足该条件的消息，则表示已经过了消息的平均消费时间了，判定为producer端发送的确认消息发生丢失
	--- 这种情况就需要producer重新发送消息到broker了。

	对于那些重新发送的消息，需要更新update_time为update_time + avg_circle_time 
	以便进入下一次check。