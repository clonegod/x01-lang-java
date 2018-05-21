## 1、下载压缩包，解压
	http://mirror.bit.edu.cn/apache/kafka/1.1.0/kafka_2.11-1.1.0.tgz

	解压到：E:\local\kafka_2.11-1.1.0

## 2、启动zookeeper（内置单机版zookeeper）
	cd /d E:\local\kafka_2.11-1.1.0\bin\windows

	zookeeper-server-start.bat ../../config/zookeeper.properties

## 3、启动kafka（默认配置 2181端口）
	cd /d E:\local\kafka_2.11-1.1.0\bin\windows

	kafka-server-start.bat ../../config/server.properties



