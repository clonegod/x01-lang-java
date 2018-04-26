使用JSP作为页面模板：

1、创建工程pom类型为war。
必须添加jsp相关依赖，否则无法正常处理jsp模板。
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<scope>provided</scope>
		</dependency>
		
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>jstl</artifactId>
			<version>1.2</version>
		</dependency>
		
		<dependency>
			<groupId>org.apache.tomcat.embed</groupId>
			<artifactId>tomcat-embed-jasper</artifactId>
			<scope>provided</scope>
		</dependency>

2、以下两个配置将启用jsp，使用InternalResourceViewResolver 作为jsp视图的解析处理器.
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

3、将jsp页面放到：src/main/webapp/WEB-INF/jsp路径下。

-------------------------------------------------

项目打包后的目录结构：
springboot-template-jsp-0.0.1-SNAPSHOT
	/ META-INF
		 / MANIFEST.MF	指定项目启动类路径等信息
	
	/ WEB-INF
		 / classes	项目java文件编译后的存放目录(src/main/java, src/main/resources)
		 / jsp		项目jsp页面的存放目录(src/main/webapp/WEB-INF)
		 / lib		项目的依赖包存放目录(pom中的依赖包)
