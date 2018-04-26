# dubbo-admin 


注意：从dubbo-2.5.4版本开始进行了很大的升级（更新了大量依赖的第三方框架的版本），加入了一些JDK1.8中的API。因此，实验环境需要安装JDK1.8。

新版本会提供springboot-dubbo-starter插件，与Springboot进行集成。

**特别说明**

	1、项目开发中可以使用dubbo-2.5.5版本进行开发，该版本兼容JDK1.7。
	
	2、而dubbo-admin，需要使用dubbo-2.5.6以上版本Tomcat才能正常启动，且需要JDK1.8。
	
	如果使用dubbo-admin其它旧版本，就会报异常：
	spring容器启动时，FileNotFoundException: /WEB-INF/applicationContext.xml，
	这个问题很是纠结啊！！！

	因此，dubbo-admin可以使用最新版本，且使用JDK1.8，而项目开发照样使用JDK1.7即可。
	---提示：可配置Tomcat使用指定的JDK版本，在catalina.sh中配置JAVA_HOME。
	


### 1、下载dubbo源码，编译打包生成dubbo-admin.war
[dubbo-2.5.8.zip](https://github.com/alibaba/dubbo/archive/dubbo-2.5.8.zip)

解压，进入dubbo-admin模块，使用Maven进行打包

	mvn package -DskipTests

进入target目录，可以看到生成了dubbo-admin-2.5.8.war

### 2、部署到Tomcat
	1) 复制war包到${TOMCAT_HOME}/webapps目录下
	
	2) 解压dubbo-admin-2.5.8.war
	cd webapps
	unzip dubbo-admin-2.5.8.war -d ./dubbo-admin

	3) 删除war包
	rm -f dubbo-admin-2.5.8.war
	

	4) 配置dubbo的注册中心地址
	vi dubbo-admin/WEB-INF/dubbo.properties
	
	# dubbo注册中心地址-默认使用zookeeper作为注册中心
	dubbo.registry.address=zookeeper://192.168.1.201:2181?backup=192.168.1.202:2181,192.168.1.203:2181
	# dubbo管理控制台的用户登录密码
	dubbo.admin.root.password=root
	dubbo.admin.guest.password=guest

### 3、启动Tomcat，访问控制台
	# 先确保zookeeper集群已经正常启动(略)

	# 启动tomcat
	bin/startup.sh
	
	# 查看启动日志
	tail -f -n 300 logs/catalina.out

### 浏览器访问
	http://localhost:8080/dubbo-admin


	
	
	


