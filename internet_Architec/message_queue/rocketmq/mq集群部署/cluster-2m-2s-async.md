# RocketMQ部署 【两主两从+异步复制】
@_@

### 规划集群环境
ip | 角色 | 模式
------------ | ------------- | -------------
192.168.1.201 | nameServer1, brokerServer1 | Master1
192.168.1.202 | nameServer2, brokerServer2 | Master2
192.168.1.203 | nameServer3, brokerServer1-Slave | Master1-Slave
192.168.1.204 | nameServer4, brokerServer2-Slave | Master2-Slave

### Hosts添加信息(两台机器)
	# vi /etc/hosts
	192.168.1.201           rocketmq-nameserver1
	192.168.1.201           rocketmq-master1    
	192.168.1.202           rocketmq-nameserver2
	192.168.1.202           rocketmq-master2
	192.168.1.203           rocketmq-nameserver3
	192.168.1.203           rocketmq-master1-slave    
	192.168.1.204           rocketmq-nameserver4
	192.168.1.204           rocketmq-master2-slave

### 配置文件 2m-2s-async.properties
	
	# 进入rocketmq配置文件目录
	cd /usr/local/rocketmq/conf/2m-2s-async

###### broker-a.properties  配置broker-a为Master节点
	# broker集群的名称
	brokerClusterName=DefaultCluster
	
	# 当前broker节点的名称
	brokerName=broker-a
	
	# 0 表示Master节点
	brokerId=0
	
	#nameserver的地址
	namesrvAddr=rocketmq-nameserver1:9876;rocketmq-nameserver2:9876;rocketmq-nameserver3:9876;rocketmq-nameserver4:9876;
	
	#节点的角色，以及master-slave模式下的数据同步策略
	#- ASYNC_MASTER 异步复制Master
	#- SYNC_MASTER 同步双写Master
	#- SLAVE
	brokerRole=ASYNC_MASTER
	
	# 数据从内存缓存写入磁盘的方式：同步刷盘、异步刷盘
	flushDiskType=ASYNC_FLUSH

	#存储路径等其他配置，参考2m-noslave配置，此处略

###### broker-a-s.properties  配置broker-a的slave节点
	# 指定当前slave节点所属的Master节点的名称
	brokerName=broker-a

	# 1 表示Slave节点(大于0的整数都表示slave节点)
	brokerId=1
	
	# 节点的角色-slave节点
	brokerRole=SLAVE

	#其他配置参考broker-a.properties，此处略

###### broker-b.properties	配置broker-b为Master节点
	# broker集群的名称
	brokerClusterName=DefaultCluster
	
	# 当前broker节点的名称
	brokerName=broker-b
	
	# 0 表示Master节点
	brokerId=0
	
	#nameserver的地址
	namesrvAddr=rocketmq-nameserver1:9876;rocketmq-nameserver2:9876;rocketmq-nameserver3:9876;rocketmq-nameserver4:9876;
	
	#节点的角色，以及master-slave模式下的数据同步策略
	#- ASYNC_MASTER 异步复制Master
	#- SYNC_MASTER 同步双写Master
	#- SLAVE
	brokerRole=ASYNC_MASTER
	
	# 数据从内存缓存写入磁盘的方式：同步刷盘、异步刷盘
	flushDiskType=ASYNC_FLUSH

	#其他配置参考broker-a.properties，此处略


###### broker-b-s.properties  配置broker-b的slave节点
	# 指定当前slave节点所属的Master节点的名称
	brokerName=broker-b

	# 1 表示Slave节点(大于0的整数都表示slave节点)
	brokerId=1
	
	# 节点的角色-slave节点
	brokerRole=SLAVE

	#其他配置参考broker-a.properties，此处略


## 启动集群
### 先启动4个namesrv节点，分别在192.168.1.201/202/203/204上执行
	cd /usr/local/rocketmq/bin
	nohup sh mqnamesrv &

### 再启动4个broker节点
	#192.168.1.201 启动Master节点 broker-a
	nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-2s-async/broker-a.properties >/dev/null 2>&1 &

	#192.168.1.202 启动Master节点 broker-b
	nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-2s-async/broker-b.properties >/dev/null 2>&1 &

	#192.168.1.203 启动Slave节点 broker-a-s
	nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-2s-async/broker-a-s.properties >/dev/null 2>&1 &

	#192.168.1.204 启动Slave节点 broker-b-s
	nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-2s-async/broker-b-s.properties >/dev/null 2>&1 &


## 在rocketmq-console中配置namesrv的地址
	[root@node1 ~]# cd /usr/local/tomcat/webapps
	[root@node1 webapps]# mkdir rocketmq-console
	[root@node1 webapps]# unzip rocketmq-console.war -d rocketmq-console
	[root@node1 webapps]# cd rocketmq-console/WEB-INF/classes
	[root@node1 classes]# vi config.properties
		#rocketmq.namesrv.addr=127.0.0.1:9876
		rocketmq.namesrv.addr=192.168.1.201:9876;192.168.1.202:9876;192.168.1.203:9876;192.168.1.204:9876
		throwDone=true