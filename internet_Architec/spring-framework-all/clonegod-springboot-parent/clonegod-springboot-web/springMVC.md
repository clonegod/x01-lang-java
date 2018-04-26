### Spring Web MVC

#### 主要内容


-   Spring Web MVC 介绍：整体介绍 Spring Web MVC 框架设计思想、功能特性、以及插播式实现

MVC

	M: Model

	V: View

	C: Controller -> DispatcherServlet


Controller的细分

	Front Controller 		=> DispatcherServlet

	Application Controller 	=> @Controller or Controller

	ServletContextListener -> ContextLoaderListener -> Root WebApplicationContext
							  DispatcherServlet		-> Servlet WebApplicationContext

DispatcherServlet 的继承体系

	DispatcherServlet extends FrameworkServlet  extends HttpServletBean implements ApplicationContextAware


![](https://docs.spring.io/spring/docs/5.0.5.RELEASE/spring-framework-reference/images/mvc-context-hierarchy.png)

######################################

----------


- Spring Web MVC 实战：详细说明DispatcherServlet、@Controller和@RequestMapping的基本原理、@RequestParam、@RequestBody和@ResponseBody使用方式、以及它们之间关系


DispatcherServlet 的自动装配

org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration

```

@AutoConfigureOrder(Integer.MIN_VALUE)

@Configuration

@ConditionalOnWebApplication(type = Type.SERVLET)

@ConditionalOnClass({DispatcherServlet.class})

@AutoConfigureAfter({ServletWebServerFactoryAutoConfiguration.class})

@EnableConfigurationProperties({ServerProperties.class})

public class DispatcherServletAutoConfiguration {
```

spring Web MVC 的配置Bean：

@ConfigurationProperties(prefix = "spring.mvc")
WebMvcProperties

springboot 允许通过 appliacatio.properties 覆盖配置配置- 配置外部化 


@ResponseBody 的实现

	org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor

######################################

----------


- 映射处理：介绍DispatcherServlet与RequestMappingHandlerMapping之间的交互原理，HandlerInterceptor的职责以及使用

HandlerMapping -> RequestMappingHandlerMapping

寻找与Request URI 匹配的handler，其中hanlder是Controller中处理请求的具体方法。

执行顺序：

	preHandle(return true) -> HandleMethod -> postHandle -> afterCompletion
	
	preHandle(return false) -> End


######################################

----------


- 异常处理：介绍DispatcherServlet中执行过程中，如何优雅并且高效地处理异常的逻辑，如归类处理以及提供友好的交互界面等

传统Servlet在web.xml中处理错误页面:

<error-page>处理逻辑（全局的Http请求错误码处理）：

	1、处理状态码	<error-code>

	2、处理异常类型	<exception-type>

	3、处理异常的服务地址	<location>

SpringMVC 处理异常的方式(专门针对Controller中抛出的异常进行拦截处理)：
	
	1、创建一个类，声明 @ControllerAdvice + @ResponseBody ( = @RestControllerAdvice)
	
	2、在@ExceptionHandler 标注的方法中处理异常，返回错误信息给客户端

springboot 处理错误页面的方式（全局的Http请求错误码处理）：

	1、实现ErrorPageRegistrar接口

	2、registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));	

	3、在Controller中提供一个handler来处理发生的ErrorPage错误（path匹配到注册的/404.html路径）。可以返回一个View，也可以用@ResponseBody返回json。


######################################

----------

- 视图解析：介绍 Spring Web MVC 视图解析的过程和原理、以及内容协调视图处理器的使用场景

View
	render 方法：处理页面渲染逻辑，例如Velocity，JSP，Thymeleaf等

ViewResolver 
	
	View Resolver = 页面 + 解析器 -> resolve view name

	requestURI -> @RequsetMappingHandlerMapping -> HandleMethod -> "viewName"

	完整页面： prefixe + "viewName" + suffix


##### ContentNegotiationViewResolver 
    作用：
	View有多种实现方式，比如JSP，thymeleaf等，handler返回viewName之后该用哪个视图解析器进行处理呢？
	ContentNegotiationViewResolver就是用来从多个视图解析器中选择一个最佳的解析器来处理View。

	最佳匹配原则：
	基于请求内容类型选择最佳的视图解析器。
	getCandidateViews
		# 用于获取所有候选的ViewResolver
		private List<View> getCandidateViews(String viewName, Locale locale, List<MediaType> requestedMediaTypes) 
		内部选择逻辑：
		1、viewResolvers 应用启动时注册到spring中的视图解析器，可以注册多个；
		2、遍历请求的requestedMediaTypes类型，得到请求资源的后缀类型，将viewName与得到的后缀suffix组装得到完整的资源名称；
		3、使用候选的ViewResolver尝试加载View，如果加载view成功，加入到candidateViews
	
	getBestView 
	# 从候选viewResolver中选择最佳的
	getBestView(List<View> candidateViews, List<MediaType> requestedMediaTypes, RequestAttributes attrs
	根据客户端请求的MediaType获取requestedMediaTypes
	遍历candidateViews，得到每个视图解析器兼容的mediaType，返回第一个匹配到的视图View。
		ThymeleafView、MappingJackson2JsonView、JstlView、GroovyMarkupView等

	Order --- 值越小，优先级越高，值越大，优先级越低
	如果有多个视图都匹配客户端请求的内容类型，那么遍历这些候选的viewResolver时，优先级高的会先被遍历到，则被选择到。
	默认情况下，所有ViewResolver的Order都是配置的最低优先级，即：
	int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
	int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

######################################

----------

- Thymeleaf 视图技术：介绍新一代视图技术 Thymeleaf ，包括其使用场景、实际应用以及技术优势

spring 推荐使用的视图框架，支持HTML5

自动装配类：

	@ConditionalOnClass({TemplateMode.class})
	org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration
    
	@ConfigurationProperties(prefix = "spring.thymeleaf")
		public class ThymeleafProperties {
		private String prefix = "classpath:/templates/";
		private String suffix = ".html";

可配置化：
	spring.thymeleaf.prefix=classpath:/thymeleaf/
	spring.thymeleaf.suffix=.html
	spring.thymeleaf.cache=false
	spring.thymeleaf.template-resolver-order=-1

######################################

----------

- 国际化：利用Locale技术，实现视图内容的国际化

Locale/LocaleContext

LocaleContextHolder

LocaleResolver/LocaleContextResolver

org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver

	class AbstractEngineContext
    public final String getMessage(
            final Class<?> origin, final String key, final Object[] messageParameters, final boolean useAbsentMessageRepresentation) {

	org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration

	org.thymeleaf.spring5.messageresolver.SpringMessageResolver
	
	org.springframework.context.support.ResourceBundleMessageSource: basenames=[i18n/messages]

