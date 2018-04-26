# RocketMQ部署 【双Master方式】
@_@

### 规划集群环境
ip | 角色 | 模式
------------ | ------------- | -------------
192.168.1.201 | nameServer1, brokerServer1 | Master1
192.168.1.202 | nameServer2, brokerServer2 | Master2

### Hosts添加信息(两台机器)
	# vi /etc/hosts
	192.168.1.201           rocketmq-nameserver1
	192.168.1.201           rocketmq-master1    
	192.168.1.202           rocketmq-nameserver2
	192.168.1.202           rocketmq-master2
	
	# 重启network
	systemctl restart network

### 上传rocketmq-3.2.6 、 解压
##### 注：仅需上传到一个节点上，最后将整个已配置好的目录直接复制到另一个节点
	# windows上传
	scp alibaba-rocketmq-3.2.6.tar.gz root@192.168.1.201:/usr/local/software
	# 解压
	[root@node1 software]# tar -zxvf alibaba-rocketmq-3.2.6.tar.gz -C /usr/local
	[root@node1 software]# cd /usr/local
	# 修正目录版本
	[root@node1 local]# mv alibaba-rocketmq alibaba-rocketmq-3.2.6
	# 创建软链接
	[root@node1 local]# ln -s alibaba-rocketmq-3.2.6 rocketmq

### 创建存储路径(两台机器)
	[root@node1 local]# mkdir /usr/local/rocketmq/store
	[root@node1 local]# mkdir /usr/local/rocketmq/store/commitlog
	[root@node1 local]# mkdir /usr/local/rocketmq/store/consumequeue
	[root@node1 local]# mkdir /usr/local/rocketmq/store/index

## RocketMQ配置文件
###### # vi /usr/local/rocketmq/conf/2m-noslave/broker-a.properties
	brokerName=broker-a
###### # vi /usr/local/rocketmq/conf/2m-noslave/broker-b.properties 
	brokerName=broker-b

### broker-n.properties配置

	#所属集群名字
	brokerClusterName=rocketmq-cluster
	
	#broker名字，注意此处不同的配置文件填写的不一样
	#<<<---分别指定不同broker的名称--->>>
	#broker-a.properties中配置为broker-a 
	#broker-b.properties中配置为broker-b
	brokerName=broker-a|broker-b

	#0 表示 Master， >0 表示 Slave
	brokerId=0
	
	#nameServer地址，分号分割
	namesrvAddr=rocketmq-nameserver1:9876;rocketmq-nameserver2:9876
	
	#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
	defaultTopicQueueNums=4
	
	#是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
	autoCreateTopicEnable=true
	#是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
	autoCreateSubscriptionGroup=true
	
	#Broker 对外服务的监听端口
	listenPort=10911
	
	#删除文件时间点，默认凌晨 4点
	deleteWhen=04
	#文件保留时间，默认 48 小时
	fileReservedTime=120
	#commitLog每个文件的大小默认1G
	mapedFileSizeCommitLog=1073741824
	#ConsumeQueue每个文件默认存30W条，根据业务情况调整
	mapedFileSizeConsumeQueue=300000
	#destroyMapedFileIntervalForcibly=120000
	#redeleteHangedFileInterval=120000
	#检测物理文件磁盘空间
	diskMaxUsedSpaceRatio=88
	
	#存储路径
	storePathRootDir=/usr/local/rocketmq/store
	#commitLog 存储路径
	storePathCommitLog=/usr/local/rocketmq/store/commitlog
	#消费队列存储路径存储路径
	storePathConsumeQueue=/usr/local/rocketmq/store/consumequeue
	#消息索引存储路径
	storePathIndex=/usr/local/rocketmq/store/index
	#checkpoint 文件存储路径
	storeCheckpoint=/usr/local/rocketmq/store/checkpoint
	#abort 文件存储路径
	abortFile=/usr/local/rocketmq/store/abort
	
	#限制的消息大小
	maxMessageSize=65536
	#flushCommitLogLeastPages=4
	#flushConsumeQueueLeastPages=2
	#flushCommitLogThoroughInterval=10000
	#flushConsumeQueueThoroughInterval=60000
	
	#节点的角色，以及master-slave模式下的数据同步策略
	#- ASYNC_MASTER 异步复制Master
	#- SYNC_MASTER 同步双写Master
	#- SLAVE
	brokerRole=ASYNC_MASTER
	
	#系统缓冲区刷盘方式
	#- ASYNC_FLUSH 异步刷盘
	#- SYNC_FLUSH 同步刷盘
	flushDiskType=ASYNC_FLUSH
	
	#checkTransactionMessageEnable=false
	#发消息线程池数量
	#sendMessageThreadPoolNums=128
	#拉消息线程池数量
	#pullMessageThreadPoolNums=128

### 修改logback.xml中配置的log存储目录
	mkdir -p /usr/local/rocketmq/logs
	cd /usr/local/rocketmq/conf
	sed -i 's#${user.home}#/usr/local/rocketmq#g' *.xml

## 修改启动脚本参数

##### 调整broker的JVM启动参数（开发环境虚拟机内存很小，调小堆的分配）
	vim /usr/local/rocketmq/bin/runbroker.sh
	#==================================================================================
	# JVM Configuration 
	#==================================================================================
	#JAVA_OPT="${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn2g -XX:PermSize=128m -XX:MaxPermSize=320m"
	JAVA_OPT="${JAVA_OPT} -server -Xms1g -Xmx1g -Xmn512m -XX:PermSize=128m -XX:MaxPermSize=320m"
	
##### 调整nameServer的JVM启动参数（开发环境虚拟机内存很小，调小堆的分配）
	vim /usr/local/rocketmq/bin/runserver.sh
	#==================================================================================
	# JVM Configuration
	#==================================================================================
	#JAVA_OPT="${JAVA_OPT} -server -Xms4g -Xmx4g -Xmn2g -XX:PermSize=128m -XX:MaxPermSize=320m"
	JAVA_OPT="${JAVA_OPT} -server -Xms1g -Xmx1g -Xmn512m -XX:PermSize=128m -XX:MaxPermSize=320m"



## 分发已配置好的rocketmq到集群的其它节点
##### 从192.168.1.201复制rocketmq到192.1681.202节点上
	[root@node1 local]# scp -r /usr/local/alibaba-rocketmq-3.2.6 node2:/usr/local

##### 192.1681.202节点上，设置软连接
	[root@node2 ~]# cd /usr/local
	[root@node2 local]# ln -s alibaba-rocketmq-3.2.6 rocketmq
	
	
## 1、先启动NameServer (2个节点)
	cd /usr/local/rocketmq/bin
	nohup sh mqnamesrv &

## 2、启动BrokerServer-A (192.168.1.201)
	cd /usr/local/rocketmq/bin
	nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-noslave/broker-a.properties >/dev/null 2>&1 &

## 3、启动BrokerServer-B (192.168.1.202)
	cd /usr/local/rocketmq/bin
	nohup sh mqbroker -c /usr/local/rocketmq/conf/2m-noslave/broker-b.properties >/dev/null 2>&1 &
	
### 两个节点上分别执行命令，检测服务启动情况
	netstat -ntlp | grep 
	jps
	tail -f -n 500 /usr/local/rocketmq/logs/rocketmqlogs/namesrv.log
	tail -f -n 500 /usr/local/rocketmq/logs/rocketmqlogs/broker.log


## 数据清理
	# cd /usr/local/rocketmq/bin
	# --先关闭broker，再关闭nameserver
	# sh mqshutdown broker
	# sh mqshutdown namesrv
	# --等待停止
	# rm -rf /usr/local/rocketmq/store
	# mkdir /usr/local/rocketmq/store
	# mkdir /usr/local/rocketmq/store/commitlog
	# mkdir /usr/local/rocketmq/store/consumequeue
	# mkdir /usr/local/rocketmq/store/index
	# --按照上面步骤重启NameServer与BrokerServer

## RocketMQ Console
##### 在tomcat中部署rocketmq-console.war
浏览器访问:  [http://192.168.1.201:8080/rocketmq-console/](http://192.168.1.201:8080/rocketmq-console/)

	# windows上传到192.168.1.201
	scp rocketmq-console.war root@192.168.1.201:/usr/local/tomcat/webapps
	
	# 编辑namesrv地址
	[root@node1 ~]# cd /usr/local/tomcat/webapps
	[root@node1 webapps]# mkdir rocketmq-console_2
	[root@node1 webapps]# unzip rocketmq-console.war -d rocketmq-console_2
	[root@node1 webapps]# cd rocketmq-console_2/WEB-INF/classes
	[root@node1 classes]# vi config.properties
		#rocketmq.namesrv.addr=127.0.0.1:9876
		rocketmq.namesrv.addr=192.168.1.201:9876;192.168.1.202:9876
		throwDone=true

	# 重启tomcat
	[root@node1 ~]# ps -ef | grep tomcat | grep -v "grep" | awk '{print $2}' | xargs -n 1 kill -9 
	[root@node1 ~]# /usr/local/tomcat/bin/startup.sh

