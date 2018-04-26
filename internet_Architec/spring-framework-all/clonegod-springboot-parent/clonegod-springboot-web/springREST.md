## REST理论基础

### [REST 的定义](https://en.wikipedia.org/wiki/Representational_state_transfer)

### 资源操作
	GET, POST, PUT, DELETE
	幂等方法

### 自描述消息
	Content-Type	  响应内容的类型
	MIME-Type	  多媒体资源类型 Accept: text/html,application/json,image/jpeg...

## REST On Spring Web MVC

### 服务端核心接口

	org.springframework.web.bind.annotation

#### 定义相关
- @Controller
- @RestController

#### 映射相关
- @RequestMapping
- @PathVariable
- @RequestParam

- @GetMapping
- @PostMapping
- @PutMapping
- @DeleteMapping

#### 方法相关
- RequestMethod

	GET 	幂等性 查询资源
	
	PUT		幂等性 更新资源

	POST 	非幂等性  新建资源
		
	DELETE	幂等性 删除资源

	HEAD
	
	PATCH
	
	OPTIONS
	
	TRACE


### Spring REST 对返回内容类型的配置

##### 自描述消息类型 - Accept： XXX
accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8

	第一优先顺序：
		text/html > application/xhtml+xml > application/xml
		q=0.9 表示该优先级的权重
	第二优先顺序：
		image/webp > image/apng > */*
		q=0.8 表示该优先级的权重

spring将根据请求类容，选择最佳的messageConverter生成客户端所期望的响应内容

	@EnableWebMvc 
		-> DelegatingWebMvcConfiguration 
		-> WebMvcConfigurationSupport.requestMappingHandlerAdapter()
		-> WebMvcConfigurationSupport.getMessageConverters();
		-> WebMvcConfigurationSupport.addDefaultHttpMessageConverters()

WebMvcConfigurationSupport 在加载的时候，就会查询classpath下是否存在指定类型的类。
如果存在，则会在配置messageConverter的时候，加入对于的Convert到HttpMessageConverter集合中，最后设置到 RequestMappingHandlerAdapter 中。

##### 配置spring REST支持返回xml格式的内容
默认情况下，spring是没有依赖com.fasterxml.jackson.dataformat.xml.XmlMapper相关的jar包。

因此，默认情况下是不支持返回xml格式数据的。需要手动增加对应的依赖包的引入，则可以返回xml格式数据。

查询类所属的jar：http://search.maven.org/#advancedsearch%7Cgav


	返回JSON - 使用jackson处理json
	private static final boolean jackson2Present =
			ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper",
					WebMvcConfigurationSupport.class.getClassLoader()) &&
			ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator",
					WebMvcConfigurationSupport.class.getClassLoader());
	
	返回XML - 使用jackson处理xml
	private static final boolean jackson2XmlPresent =
			ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper",
					WebMvcConfigurationSupport.class.getClassLoader());
	
	返回JSON - 使用GSON处理json
	private static final boolean gsonPresent =
			ClassUtils.isPresent("com.google.gson.Gson",
					WebMvcConfigurationSupport.class.getClassLoader());
	

所有的HTTP自描述消息处理器均保存在messageConverters中，这个集合会设置到RequestMappingHandlerAdapter，最终控制返回类型写出的格式。

messageConverters集合中，包含很多自描述消息类型的处理，比如JSON,XML,TEXT等

以application/json为例，Spring Boot 中默认使用jackson2序列化方式。
其中媒体类型：application/json，它的处理类MappingJackson2XmlHttpMessageConverter提供2个方法：

	读 read* : 通过HTTP请求内容转换成对应的Bean
	写 write* : 通过Bean序列化对应文本内容作为响应内容


### 扩展自定义的MessageConverter

客户端需要在header中传入特定的内容类型，这样spring在选择messageConverter时，才可能会使用到自定义的messageConverter。

	@RequestMapping 中的 consumes 对应HTTP请求头中的 "Content-Type"

	@RequestMapping 中的 produces 对应HTTP请求头中的 "Accept"

---------------

## REST On Spring Web Flux

### 服务端核心接口

#### Reactor相关
- Flux (0..N)
- Mono (0..1)

#### 映射相关
- org.springframework.web.reactive.function.server.RouterFunctions.route
- org.springframework.web.reactive.function.server.RequestPredicate.POST

### 编程模型

- Annotation 驱动

-  函数式


https://typora.io/