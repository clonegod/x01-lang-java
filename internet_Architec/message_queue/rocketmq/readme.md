# RocketMQ
##### RocketMQ是一款分布式、队列模型的消息中间件，具有以下特点：
优势：消息重试强、支持顺序消费、支持事务消费、并发能力强、堆积能力强

	* >>> 强调集群无单点问题，原生就是集群模式
		1、双主模型 2master 
			- 大部分项目使用该模式已足够满足需求
			- Master宕机后，其上的未被消费的消息不能被consumer端消费，缺失消息的实时消费
		
		2、多主多从模型 + 异步复制	2master-2slave-async 
			-实时性要求高的场景下使用
			-Master宕机后，由Slave节点推送消息到consumer，实现消息的实时消费
			-在Master恢复前，Slave仅提供消息给consumer消费，Slave只读不可写
			-在Master恢复后，会同步slave上的消息消费状态，保持主从消息状态一致
			-主从间消息复制使用异步方式，性能好，但Master宕机可能导致少量数据丢失
		
		3、多主多从模型 + 同步双写	2master-2slave-sync
			- 最严苛的场景下使用
			- 主从间消息复制使用同步方式，最安全，可确保主从间消息是完全一致的
		
		主从复制的两种持久化机制：
			异步复制 - 性能好（消息从Master以异步方式复制到Slave）
			同步双写 - 适合对消息可靠性要求极高的场合，比如设计到money的应用

	* >>> 完善的消息重试机制，确保消息在流转过程中不丢失
		消息确认机制
		消息方向：producer -> broker
			producer端的消息重试 - 确保消息发送到broker 
			producer.setRetryTimesWhenSendFailed(10);
		
		broker对所有消息进行持久化 - 确保消息在broker上不丢失

		消息方向：broker -> consumer
			consumer端的消息重试 - 确保消息从broker推送到消费端
			网络异常导致broker推送消息到consumer失败的重试-消费者宕机
			消费端程序异常导致的重试
		说明：
			先启动consumer订阅，再启动producer的情况下,
			consumer只要不宕机，broker对其上的消息处理没有时间要求，
			broker不会因为consumer长时间未返回消息确认而将消息发送到其它consumer。

	* >>> 支持消息的有序消费（保证严格的消息顺序）
	 	有序消息的底层实现:
	 	1、producer的保证：
			orderId按队列个数取模，实现具有相同orderId的消息被放到同一个队列中(QueueSelector)；
	 	2、broker端的保证：
			将消息按顺序发送给consumer；
	 	3、consumer端的保证：
			消费线程与队列是具有绑定关系的，一个队列的数据仅能被一个专门的线程来消费。
		
		基于以上保证，对于相同orderId的消息，由于分配在了同一个队列，
		在consumer端又是由单线程处理该队列上的消息，因此，确保了消息的顺序消费。
		
		另外，在consumer端通过线程池并发消费消息：
			不同orderId消息被并发消费！
			相同orderId的消息被有序消费！
		
		补充：
		1、正常情况下，双Master模式在Master不宕机的情况下可以保证消息的顺序消费；
		2、如果Master宕机，则无法提供消息顺序消费的保障。
			Broker重启，队列总数发生发化，哈希取模后定位的队列会变化，因此产生短暂的消息顺序不一致。
		3、如果要求某个Master宕机时也需要保障消息的顺序消费：
			3个条件: a.部署集群为多主多从模式; b.同步双写机制；c.能自动完成主备切换
			只有满足上面3个条件，才能保证在高可用的情况下也支持严格的消息顺序消费。
			注意：主备自动切换的功能未开源。
			因此，对于开源版本来讲，当broker宕机时，无法保证严格的顺序消息。	

	* >>> 分布式事务的支持
		事务的完整执行涉及到多个不同系统的数据源，
		无法利用传统本地事务来控制两个数据源的事务特性-ACID。
				
		1、全局性事务：
			A数据源，B数据源，保证A，B数据源同时成功，同时失败
			A事务，B事务，两个事务并行执行，由第三方来控制A，B事务都提交成功，或者都回滚。
			
			两阶段提交： Two-Phase Commit, 2PC
			两阶段提交协议经常被用来实现分布式事务。
			一般分为协调器C和若干事务执行器Si两种角色，事务执行器就是具体的数据库。
			缺点：
				两阶段提交涉及多次节点间的网络通信，通信时间长；
				事务时间相对变长，锁定资源的时间也变长，造成资源等待的时间增加；
			由于分布式事务存在很严重的性能问题，大部分高并发服务都避免使用。
			往往通过其它途径来解决数据一致性的问题。

		2、分布式事务 - MQ
			-> rocketmq-transaction.md
				

	* > 提供两种消费端消费模型：
		consumer集群消费-消费端水平扩展
			同组的consumer分摊所订阅topic上的消息，每条消息仅被1个consumer消费
		consumer广播消费-不同系统对同一个消息的处理方式不同
			同一条消息会推送给同组的每一个consumer

	* > 提供两种消费端消费拉取方式：
		push -> 智能型broker+哑consumer
			broker负责推送消息，consumer只管消费
			Consumer使用：DefaultMQPushConsumer
		pull -> 哑broker+智能型consumer
			consumer主动从broker拉取消息，并维护已消费消息的offset
			Consumer使用：DefaultMQPullConsumer
			类似kafka的消息消费方式，由消费端通过拉取方式完成消息的消费？

	* > 亿级消息堆积能力（缓解消费端压力）
		消息存储是怎么设计的？CommitLog ConsumerQueue Index
		支持上万个队列的高并发读写，并且保证性能稳定

	* > 消息都是持久化存储，且无容量限制
		实际底层是通过定期清除历史数据来解决存储容量的问题
		broker上存储消息的三个相关文件:
			commitlog 原始消息数据
			consumerqueue	消费队列，记录消息偏移量，便于快速定位消息
			index	消息的索引文件

	* > 丰富的API提供各种场景的解决方案
		基于电商的各种场景需求而定制的产品
	
	* > RocketMQ无法避免消息重复	
		消息可能会重复推送给consumer，如果业务对重复消息敏感，则需要在业务层面进行去重。
		需要消费端/业务端进行业务逻辑上的去重处理，保证消息的幂等性！！！
		引起消息重复的因素：
			网络不可达/不稳定等因素，需要依靠消息重发机制来确保消息不丢失。
		幂等性+去重的建议：
			1、为消息设置唯一键，比如使用消息内容中具有唯一性的字段来去重，比如订单号。
			2、去重表，设置字段的唯一性约束来防止重复数据。
		

##

## RocketMQ 环境搭建
-	双主模型
-	两主两从 + 异步复制
-	两主两从 + 同步双写


## RocketMQ 使用详解
### GroupName 组名称
在RocketMQ里，有一个很重要的概念，那就是GroupName。

同一个组内的节点，被认为是具有相同业务处理逻辑的单元，可以相互替代的。

无论是生产端还是消费端，都必须指定一个GroupName，这个组名称是维护在应用系统级别上的。

比如在生产端指定一个名称：ProducerGroupName，这个名称需要由应用系统来保证唯一性。

生产端指定GroupName的目的：处理事务消息时，MQ可能会将事务处理结果回调发送给同一组的其它Producer节点来处理。

在消费端指定同一个GroupName的目的：将几个consumer作为消息处理的一个逻辑整体来对待。

### Topic 主题
	每个主题Topic表示一个逻辑上存储的概念。
	在MQ上，每个Topic会有与之对应的多个Queue队列，Queue是物理存储的概念。
	一个Topic下默认配置了4个队列，另外也可以在程序中配置队列的个数。

### 集群消费、广播消费
	RocketMQ使用订阅主题的方式来进行消息的传递，支持两种消息消费模型。
 
集群消费

	消费端需要设置消费模式-MessageModel.CLUSTERING
	该模式在consumer端可实现水平扩展，并以均衡消费的方式处理broker上的消息。
	正常情况（单条消息）：
		先启动消费端订阅Topic，之后生产端再向broker推送消息。
		MQ每次仅向consumer发送一条消息，consumer确认处理完成后才发送下一条消息。
	特殊情况（批量消息）：
		支持生产端先推送消息到MQ，之后消费端才向MQ订阅主题。
		这种情况下MQ可以1次发送多条消息给消费端处理。
		如果有批量消息的处理需求，更好的方式是采用Pull类型的consumer。
	
广播消费

	消费端需要设置消费模式-MessageModel.BROADCASTING
	这中模式，MQ将会把消息发送给所有订阅了相关主题的consumer
	一条消息被发送到多个consumer处理，这些consumer对同一个消息的处理逻辑应该是不同的。

## RocketMQ 实战 - Broker
-	接收消息
-	推送消息
-	消息重试

## RocketMQ 实战 - Producer
	推送消息到broker,1个producer可以向多个主题发送消息。
	可以通过Tag定义一些简单的标签，由consumer端通过Tag来过滤消息。
	三种不同模式的Producer
		- NomalProducer 普通
		- OrderProducer 顺序
		- TransactionProducer 事务
	
	普通模式 - 普通消息的并发无序消费
		使用传统的发送即可，这种模式不能保证消息的顺序一致性。

	顺序模式 - 消息按顺序消费
		遵循全局顺序的时候，在MQ上使用同一个queue对消息进行存储（单队列存储）
		局部顺序的时候可以使用多个queue并行消费
		
		利用单队列来实现消息的有序：
		在发送消息时，通过唯一标识（如订单号）来选择队列
		这样就能将消息被发送到同一个broker下的同一个队列上
		基于消息发送到MQ同一个队列上时间的先后顺序，从而实现了消息的有序消费。
				
		比如一个订单将产生三条消息：订单创建、订单付款、订单完成
		这3条消息必须被顺序消费才有意义。
		注：相同订单号的消息一定是被发送到同一个broker下的同一个队列存储的！
		
	事务模式 - 使用MQ来实现分布式事务的提交
		在rocketMQ里，事务的处理分为两个阶段提交。
		第一个阶段，预先把消息发送到MQ上，此时消息对消费端是不可见的。
		第二个阶段，本地事务提交成功后，通过回调方式向MQ返回一个确认消息：
			如果本地事务成功，则返回COMMIT_MESSAGE，此时MQ上的数据对consumer端可见。
			如果本地事务失败，返回ROLLBACK_MESSAGE，消息将从MQ上删除。

## RocketMQ 实战 - Consumer
-	push consumer 处理broker推送的消息
-	pull consumer 主动从broker拉取消息
-	concurrently	并发消费消息
-	orderly	有序消费消息

## RocketMQ 架构设计
	# 模块间的层次关系
	上层：namesrv + broker + filtersrv + tool 
	中间：client + store + srvutil 
	底层：common + remoting

-	**rocketmq-namesrv** 		提供协调服务
	-	保存消息的TopicName，队列的运行时meta信息
	-	类似SOA服务中的注册中心
-	**rocketmq-broker** 		提供消息管理服务
	-	接收producer的推送的消息
	-	调用store接口完成消息的持久化
	-	推送消息给consumer
	-	消息重试机制
-	**rocketmq-client** 		客户端API
	-	producer
	-	consumer
-	**rocketmq-remoting** 		数据通信服务
	-	底层框架-netty4
	-	各种序列化的支持
-	**rocketmq-store** 			数据存储服务
	-	海量数据的存储
	-	海量数据的高效检索
-	**rocketmq-filtersrv**		支持客户端通过脚本定制复杂的消息过滤策略
	-	直接在broker上进行精细的消息过滤
	-	避免推送大量消息到consumer，而consumer仅仅只需要处理部分消息
-	**rocketmq-common** 		公共方法
-	**rocketmq-srvutil**	心跳、端口、连接
-	**rocketmq-tools**	管理命令（服务器上运维管理）


## RocketMQ 管理员操作集群
	# 切换到rocketmq的bin目录
	cd /usr/local/rocketmq/bin

	# 查看cluster集群状态
	sh mqadmin clusterlist -m -n 192.168.1.201:9876;192.168.1.202:9876
