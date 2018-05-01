
# Spring Cloud Config Client

## Sping/Spring Boot 事件机制
### 设计模式
	观察者模式
	事件/监听器模式
	
#### 观察者模式（一般，采用订阅者被动接收消息的方式）
	java.util.Observable		发布者/主题
	java.util.Observer			订阅者/订阅

#### 事件监听模式	
	java.util.EventObject		事件对象（事件对象总是关联着事件源）
	java.util.EventListener	事件监听接口（标记接口）
	
### Spring 核心接口
	ApplicationEvent		
	ApplicationListener	
	
### Spring Boot 事件机制
	org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
	org.springframework.boot.context.event.ApplicationPreparedEvent
	org.springframework.boot.context.event.ApplicationStartingEvent
	org.springframework.boot.context.event.ApplicationReadyEvent
	org.springframework.boot.context.event.ApplicationFailedEvent
	

#### org.springframework.boot.context.config.ConfigFileApplicationListener

	管理配置文件，比如application.properties或application.yaml
	
	application-{profile}.properties
	application.properties

#### java SPI - java.util.ServiceLoader<S>	
	
#### spring监听器的配置 - Spring SPI (spring.factories)

```
	spring-boot-1.5.12.RELEASE.jar
			META-INF
				spring.factories
```
			
##### Application Listeners
	org.springframework.context.ApplicationListener=\
	org.springframework.boot.ClearCachesApplicationListener,\
	org.springframework.boot.builder.ParentContextCloserApplicationListener,\
	org.springframework.boot.context.FileEncodingApplicationListener,\
	org.springframework.boot.context.config.AnsiOutputApplicationListener,\
	org.springframework.boot.context.config.ConfigFileApplicationListener,\
	org.springframework.boot.context.config.DelegatingApplicationListener,\
	org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener,\
	org.springframework.boot.logging.ClasspathLoggingApplicationListener,\
	org.springframework.boot.logging.LoggingApplicationListener
 	
 	
##### 如何控制监听器的执行顺序
	两种方式：
		实现接口 - org.springframework.core.Ordered
		标记注解 - @Order

	order值越小，优先级越高，越先被执行
	
---------------------------

## Spring Cloud 事件/监听器

```
	spring-cloud-context-1.3.3.RELEASE.jar
			META-INF
				spring.factories
```

### Application Listeners   - spring cloud 中配置的多个事件监听器，引入spring cloud 相关组件
	org.springframework.context.ApplicationListener=\
	org.springframework.cloud.bootstrap.BootstrapApplicationListener,\
	org.springframework.cloud.bootstrap.LoggingSystemShutdownListener,\
	org.springframework.cloud.context.restart.RestartListener


### BootstrapApplicationListener - 学习spring cloud 如何对事件监听器进行装配的
	org.springframework.cloud.bootstrap.BootstrapApplicationListener
	
	1、负责加载bootstrap.properties或bootstrap.yaml;

	2、负责初始化BootStrap ApplicaitonContext, id="bootstrap"
		final ConfigurableApplicationContext context = builder.run();
	
	3、BootstrapApplicationListener加载优先级高于ConfigFileApplicationListener
		BootstrapApplicationListener 配置的order：DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 5
		ConfigFileApplicationListener配置的order: DEFAULT_ORDER = Ordered.HIGHEST_PRECEDENCE + 10
	
	4、Bootstrap 是Spring的一个根上下文对象，parent == null
	-> spring cloud中存在两个context
		bootstrap.properties 		-> root context
		application.properties 	-> child context 
	
	由于bootstrap在application之前初始化，因此将bootstrap的配置放到application.properties中是无效的。
	正确的做法是，在启动命令行中配置bootstrap相关的属性：
	比如，修改默认bootstrap配置文件的名称，--spring.cloud.bootstrap.name=myBootstrap.properties
	比如，修改默认bootstrap配置文件的路径，--spring.cloud.bootstrap.location=/path/to/somewhere

	
### ConfigurableApplicationContext
	org.springframework.context.ConfigurableApplicationContext
	ApplicationContext的若干种实现类中的一种
	

### Bootstrap 配置属性
	1、Bootstrap配置文件名称
		spring.cloud.bootstrap.name
		String configName = environment.resolvePlaceholders("${spring.cloud.bootstrap.name:bootstrap}");
	
	2、Bootstrap配置文件路径
		spring.cloud.bootstrap.location
		String configLocation = environment.resolvePlaceholders("${spring.cloud.bootstrap.location:}");

	3、动态更新远程服务配置属性（通过HTTP请求修改系统中的环境参数）
		并不是所有参数都可以修改生效，比如对server.port进行修改，那也不会影响当前运行的服务的端口。
		spring.cloud.config.allowOverride，默认为true --- 是否修改成功，还受management.security影响。
		
		POST请求/env，修改env中的参数值
		POST to /env to update the Environment
		
		比如，application.properties中配置了server.clients.max=100，现在将其修改为200
			POST with x-www-form-urlencoded	
				http://localhost:8080/env	
				server.clients.max 200
		
	4、自定义Bootstrap配置
		@BootstrapConfiguration
		
		Entries in the factories file are used to create the bootstrap application context.
		在/META-INF/spring.factories中，增加自定义的配置bean，将其加入到系统启动流程中，类似 SPI。
		
		# Bootstrap components
		org.springframework.cloud.bootstrap.BootstrapConfiguration=\
		org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration,\
		org.springframework.cloud.bootstrap.encrypt.EncryptionBootstrapConfiguration,\
		org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration,\
		org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration
	
	
	5、自定义Bootstrap配置属性源
		PropertySourceLocator	
	
		第1步，implements PropertySourceLocator，实现locate方法，返回1个PropertySource
		第2步，标记@Configuration注解，发布为Bean
		第3步，标记@Order(Ordered.HIGHEST_PRECEDENCE)，设置合理的初始化优先级顺序
		第4步，在META-INF/spring.factories中，将自定义PropertySource加入到BootstrapConfiguration中 

----------------------

## 使用 Environment 端点

### environment相关知识点
	Environment
		|- ConfigurableEnvironment
			|- AbstractEnvironment
				|- MockEnvironment
				|- StandardEnvironment
					| - StandardServletEnvironment

	Environment内部实现 - MutablePropertySources
	org.springframework.core.env.MutablePropertySources中使用一个List维护所有的PropertySource
	(CopyOnWriteArrayList是线程安全版的ArrayList实现)
		private final List<PropertySource<?>> propertySourceList = 
			new CopyOnWriteArrayList<PropertySource<?>>();

	1个Environment关联1个propertySources，
	1个propertySources关联多个propertySource
	比较常用的PropertySource实现：StandardEnvironment，内部将systemEnvironment和systemProperties属性进行了读取。
	
	
	关于PropertySource的底层实现，可参考Spring Framework相关源码：
		AbstractApplicationContext提供propertySource初始化的接口定义，具体初始化由相关子类实现，
		AbstractRefreshableWebApplicationContext.initPropertySources()


### Spring Boot Actuator
	/env		备注：打印系统相关的环境变量参数。
				作用：可用来排查问题---通过env的输出，可验证配置的参数是否生效。
		profiles				环境标识
		commandLineArgs		命令行启动参数
		systemProperties	与JVM相关
		systemEnvironment	与操作系统相关
		applicationConfig	应用程序配置
		springCloudClientHostInfo	springCloud客户端信息
	
	
### Spring Framework
	Environment API		备注：@Autowire注入后，可获取到系统中配置的相关环境变量
	

## Spring Boot Actuator 系统状态监控API
	/info		
	/beans		备注：chrome 可添加JSON Viewer插件，自动进行json格式化
	/env
	...


	