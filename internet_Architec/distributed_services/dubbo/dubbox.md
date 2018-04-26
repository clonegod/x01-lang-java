## Dubbox - Dubbo Extension

GitHub 链接: [dubbox](https://github.com/dangdangdotcom/dubbox)

![dubbox-rest](https://github.com/clonegod/tools/blob/master/images/dubbox-rest.jpg)

**dubbox新增特性**

	1、 支持REST风格远程调用（HTTP + JSON/XML) - 基于非常成熟的JBoss RestEasy框架

	2、 支持基于Kryo和FST的Java高效序列化实现（为Dubbo的RPC协议添加新的序列化实现）

	3、 支持基于嵌入式Tomcat的HTTP remoting体系
	
	4、 支持完全基于Java代码的Dubbo配置, 基于Spring的Java Config
	   --->实现纯Java代码方式来配置dubbo

	5、 支持基于Jackson的JSON序列化（为dubbo增加新的JSON序列化实现）
	
	6、 第三方依赖框架的升级: Spring, ZooKeeper客户端


**dubbox使用最佳实践**

	1、服务提供方：使用注解配置REST服务 + 部分xml配置dubbo服务；
	2、服务消费方：使用xml配置引用的服务；


## 使用Dubbox
注: 使用JDK1.7版本进行编译

	从GitHub下载源码包
	注：Maven Repository上没有dubbox的包，因此需要手动对项目进行编译打包，再依赖到项目中使用。
	https://github.com/dangdangdotcom/dubbox

	编译打包到本地仓库
	mvn clean install -DskipTests
	
	或者发布到本地私服Nexus
	mvn clean deploy -DskipTests

## 部署dubbo-admin到Tomcat
	cd ${TOMCAT_HOME}/webapps
	unzip dubbo-admin-2.8.4.war -d dubbox-admin
	
	rm -f dubbo-admin-2.8.4.war
	
	# 配置dubbo.properties
	dubbo.registry.address=zookeeper://192.168.1.201:2181?backup=192.168.1.202:2181,192.168.1.203:2181
	dubbo.admin.root.password=root
	dubbo.admin.guest.password=guest
	
	# 启动tomcat
	bin/startup.sh

	# 查看启动日志
	tail -f -n 300 logs/catalina.out

### 浏览器访问
	http://localhost:8080/dubbox-admin