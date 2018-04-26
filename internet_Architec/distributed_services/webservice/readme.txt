WebService最大的好处---异构平台的通用服务，与具体语言无关的网络服务。

dtd
	约束XML中可以出现的元素以及内容限定
	
schema - xsd - xjc-> java
	约束XML中的元素，以及丰富的限定规则
	可以通过xsd转换为对于的JAVA类
	xjc -d F:\asynclife\source\app\app-webservice\target 
	    -p com.xml.schema 
	    F:\asynclife\source\app\app-webservice\src\main\java\com\asynclife\xml\schema\all\classroom.xsd


xml - stax dom4j 


wsimport 通过WSDL导出Java类
	wsimport -d F:\asynclife\source\app\app-webservice\target -p com.ws.client -keep -verbose http://localhost:8083/myWS/add?wsdl

Maven 插件
--------------
方式一：
SOAP - Simple Object Access Protocal 基于XML传递消息
	WSDL的结构
		1.types 描述方法的参数类型，通过xsd进行描述
		2.message 传递的消息：请求消息、响应消息
		3.portType	指定接口，以及接口中的方法
		4.binding	指定传递消息所使用的样式-以XML样式封装数据
		5.service	指定服务发布的名称、地址

	TCPMon 监控传输的SOAP消息内容 
		
方式二：
jax-ws - 直接用Java发布WebService


方式三：
webservice框架
CXF、Axis、Metro

==============================================
webservice的开发方式
1. 代码优先
	小系统适合直接此方式：先写代码，通过代码发布webservice，简单快速
	
2. 契约优先
	大系统开发，涉及到很多子系统的时候，适合采用此种方式：先编写数据标准schema,再编写WSDL，再通过WSDL发布webservice
	


	