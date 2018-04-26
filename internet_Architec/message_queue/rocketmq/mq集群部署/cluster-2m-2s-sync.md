# RocketMQ部署 【双Master方式】
@_@

## 规划集群环境
ip | 角色 | 模式
------------ | -------------
192.168.1.201 | nameServer1, brokerServer1 | Master1
192.168.1.202 | nameServer2, brokerServer2 | Master2

## Hosts添加信息(两台机器)
	# vi /etc/hosts
	192.168.1.201           rocketmq-nameserver1
	192.168.1.201           rocketmq-master1    
	192.168.1.202           rocketmq-nameserver2
	192.168.1.202           rocketmq-master2
	
	# 重启network
	systemctl restart network

## 上传、解压(两台机器)
	# windows上传
	scp alibaba-rocketmq-3.2.6.tar.gz root@192.168.1.201:/usr/local/software
	# 解压
	[root@node1 software]# tar -zxvf alibaba-rocketmq-3.2.6.tar.gz -C /usr/local
	[root@node1 software]# cd /usr/local
	# 修正目录版本
	[root@node1 local]# mv alibaba-rocketmq alibaba-rocketmq-3.2.6
	# 创建软链接
	[root@node1 local]# ln -s alibaba-rocketmq-3.2.6 rocketmq

## 创建存储路径(两台机器)
	[root@node1 local]# mkdir /usr/local/rocketmq/store
	[root@node1 local]# mkdir /usr/local/rocketmq/store/commitlog
	[root@node1 local]# mkdir /usr/local/rocketmq/store/consumequeue
	[root@node1 local]# mkdir /usr/local/rocketmq/store/index
