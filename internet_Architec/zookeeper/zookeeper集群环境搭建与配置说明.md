## zookeeper集群环境搭建与配置说明
>3或5节点 leader选举: leader + follower

![Image of zkservice](https://github.com/clonegod/tools/blob/master/images/zkservice.jpg)

#

## 集群规划
	操作系统：Centos7
	Java版本：JDK_1.8.0_144
	zookeeper版本：3.4.10
		3个节点：
		node1 192.168.1.201
		node2 192.168.1.202
		node3 192.168.1.203

## 准备工作（3个节点）
	- 关闭防火墙
		systemctl disable firewalld
	- 安装JDK
		/usr/local/java
	- 配置HOSTNAME
		vi /etc/hosts
			192.168.1.201           node1.clonegod.com      node1
			192.168.1.202           node2.clonegod.com      node2
			192.168.1.203           node3.clonegod.com      node3
		vi /etc/sysconfig/network
			HOSTNAME=node1.clonegod.com
	- 创建用户（可选）
		groupdadd hadoop
		useradd -g hadoop hadoop
		passwd hadoop
	- ssh免密（可选）
		ssh-keygen -t rsa -f ~/.ssh/id_rsa
		ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop@192.168.1.201
		ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop@192.168.1.202
		ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop@192.168.1.203

 
	
## 安装zookeeper-3.4.10
### 下载、解压、创建软连接
	cd /usr/local/software
	wget https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/stable/zookeeper-3.4.10.tar.gz
	tar -xzvf zookeeper-3.4.10.tar.gz -C /usr/local
	cd /usr/local
	ln -s zookeeper-3.4.10 zookeeper

### 配置环境变量
	vi /etc/profile.d/zookeeper.sh 
		export ZOOKEEPER_HOME=/usr/local/zookeeper
		export PATH=$ZOOKEEPER_HOME/bin:$PATH
	source /etc/profile
	which zkServer.sh

### 配置zookeeper（node1-192.168.1.201）
首先，在192.168.1.201节点完成配置，然后再将zookeeper复制到其它节点。
##### 1、创建数据存储目录、日志目录
	mkdir -p /usr/local/zookeeper/zkData 
	mkdir -p /usr/local/zookeeper/zkData/logs	

##### 2、配置zoo.cfg
	cd /usr/local/zookeeper/conf
	cp zoo_sample.cfg zoo.cfg
	vi zoo.cfg
		# The number of milliseconds of each tick
		tickTime=2000
		
		# The number of ticks that the initial synchronization phase can take
		initLimit=10
		
		# The number of ticks that can pass between
		# sending a request and getting an acknowledgement
		syncLimit=5
		
		# the directory where the snapshot is stored.
		# do not use /tmp for storage, /tmp here is just example sakes.
		dataDir=/usr/local/zookeeper/zkData
		dataLogDir=/usr/local/zookeeper/zkData/logs
		
		# the port at which the clients will connect
		clientPort=2181
		
		# the maximum number of client connections.
		# increase this if you need to handle more clients
		#maxClientCnxns=60
				
		server.1=node1:2888:3888
		server.2=node2:2888:3888
		server.3=node3:2888:3888

##### 3、配置zk实例在集群中的唯一身份标识
	# 在zkData目录下，创建myid。该文件必须放到数据库快照目录下，也就是zkData目录下。
	cd /usr/local/zookeeper
	echo 1 > zkData/myid

### 分发zookeeper到其它节点
	cd  /usr/local # 先切换到父目录，再使用rsync命令
	rsync -avzR zookeeper-3.4.10/ node2:/usr/local
	rsync -avzR zookeeper-3.4.10/ node3:/usr/local

### 配置zk集群的其它节点（node2 - 192.168.1.202）
	ln -s /usr/local/zookeeper-3.4.10 /usr/local/zookeeper
	cd /usr/local/zookeeper
	echo 2 > zkData/myid 
	

### 配置zk集群的其它节点（node3 - 192.168.1.203）	
	ln -s /usr/local/zookeeper-3.4.10 /usr/local/zookeeper
	cd /usr/local/zookeeper
	echo 3 > zkData/myid

## >>>启动zk集群
	# 分别在3个节点上启动zookeeper
	/usr/local/zookeeper/bin/zkServer.sh start

/usr/local/zookeeper/bin/zkServer.sh 
	start
	start-foreground
	stop
	restart
	status

{start|start-foreground|stop|restart|status|upgrade|print-cmd}

## >>>检查zk集群状态
	# 分别在3个节点上查看zookeeper的状态
	/usr/local/zookeeper/bin/zkServer.sh status
	# 192.168.1.201
		Mode: follower
	# 192.168.1.202
		Mode: follower
	# 192.168.1.203
		Mode: leader

## >>>使用zkCli.sh连接zookeeper
	# 在zk集群的任意节点上登陆都可以
	/usr/local/zookeeper/bin/zkCli.sh
	# 输出
		[zk: localhost:2181(CONNECTED) 0] ls /
		[zookeeper]

----------------------------------------------------------------------------
## zoo.cfg 配置文件参数含义说明：
### tickTime
基本时间单元，以毫秒为单位。

initLimit 和 syncLimit 以此为单位进行配置基本时间单元，以毫秒为单位。
这个时间是作为 Zookeeper 服务器之间或客户端与服务器之间维持心跳的时间间隔，也就是每隔tickTime 时间就会发送一个心跳。

### dataDir
zk保存数据的目录。

存储内存中数据库快照的位置，顾名思义就是 Zookeeper 保存数据的目录。
默认情况下，Zookeeper 将写数据的日志文件也保存在这个目录里。
建议单独设置dataLogDir配置来存储日志数据。

### dataLogdir
zk事务日志的保存目录。

zk日志存储路径。如果没指定，则默认保存到dataDir目录中，与数据库快照放在一起。

### clientPort
zk对客户端开发的服务端口号。

这个端口就是客户端连接 Zookeeper 服务器的端口，Zookeeper 会监听这个端口，接受客户端的访问请求。

### initLimit
整个zk集群达成一致状态的上限时间（leader选举完成）。

这个配置项是用来配置 Zookeeper 接受客户端（这里所说的客户端不是用户连接 Zookeeper 服务器的客户端，而是 Zookeeper 服务器集群中连接到 Leader 的 Follower 服务器）初始化连接时（长连接）最长能忍受多少个心跳时间间隔数。当已经超过 10 个心跳的时间（也就是 tickTime）长度后 Zookeeper 服务器还没有收到客户端的返回信息，那么表明这个客户端连接失败。总的时间长度就是 10*2000=20 秒

### syncLimit
leader发送消息给follower，且follower回复leader的一个消息同步上限时间。

这个配置项标识 Leader 与 Follower 之间发送消息，请求和应答时间长度，最长不能超过多少个 tickTime 的时间长度，总的时间长度就是 5*2000=10 秒

### server.A = B:C:D
A 表示这个是第几号服务器（必须与myid中的序号一致）；
B 是这个服务器的 ip 地址或主机名；
C 表示的是这个服务器与集群中的 Leader 服务器交换信息的端口；
D 表示的是万一集群中的 Leader 服务器挂了，需要一个端口来重新进行选举，选出一个新的 Leader。