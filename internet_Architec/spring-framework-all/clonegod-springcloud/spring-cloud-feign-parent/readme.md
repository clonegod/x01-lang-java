# Spring Cloud Feign - Declarative REST Client
	
	声明式的REST客户端，在客户端通过@EnableFeignClients注解可以将指定接口声明为Feign接口；
	
	Feign 的接口定义要求Controller强制实现的，可以理解为Controller被 Feign 接口进行了代理；
	
	service consumer 与 service provider 必须遵循相同的契约（接口定义：Controller中定义的URI要相同）；
	一般做法是：在公共api中定义API接口，供consumer和provider依赖使用；
	客户端和服务端的Controller都实现同样的接口，即双方都遵守契约，于是Feign就可以将consumer端的请求代理转发到provider端。
	在请求转发的过程中，可能会从Eureka上获取服务地址，然后基于Ribbon的负载均衡策略选择一个server进行调用。
	如果不使用Eureka注册中心来管理服务地址，那么可以在配置文件中，以白名单方式配置Ribbon请求时的可用服务地址列表。

# 生产环境该怎样使用Feign ？
	使用Feign需要更多Spring Cloud 的整合
		Feign 作为客户端
		Ribbon 作为负载均衡
		Eureka 作为注册中心
		Zuul 作为网关
		Security 作为安全 OAuth 2 认证


##  申明式 Web 服务客户端：Feign
	申明式：接口声明、Annotation 驱动

	Web 服务：HTTP 的方式作为通讯协议
	
	客户端：用于服务调用的存根
	
	Feign：原生并不是 Spring Web MVC的实现，是基于JAX-RS（Java REST 规范）实现。
			Spring Cloud 封装了Feign ，使其支持 Spring Web MVC、RestTemplate、HttpMessageConverter
			
	另外：RestTemplate以及 Spring Web MVC 可以显示地自定义 HttpMessageConverter实现。


#### 注册中心（Eureka Server）：服务发现和注册
	略
	
	
#### Feign 声明接口（契约）：定义一种 Java 强类型接口
	Feign API 暴露 URI，比如："/person/save"
	
	@FeignClient(value="person-service", 
	//			fallback = HystrixClientFallback.class, 
				fallbackFactory=HystrixClientFallbackFactory.class)
	public interface PersonService {
	
		/**
		 * 保存Person
		 * @param person
		 * @return	保存成功，返回true，否则false
		 */
		@PostMapping("/person/save")
		public boolean save(@RequestBody Person person);
		
		/**
		 * 获取所有Person的集合
		 * @return 
		 */
		@GetMapping("/person/list")
		public Collection<Person> findAll();
		
	}
	
#### Feign 客户（服务消费）端：调用Feign 申明接口
	
	@RestController
	public class PersonClientController implements PersonService {
		...
	}
	
#### Feign 服务（服务提供）端：非强制实现 Feign 申明接口，但建议实现接口的方式来满足契约的一致性
	@RestController
	public class PersonServiceController implements PersonService {
		...
	}


## Feign 整合Netflix Ribbon
	Ribbon的几种使用方式
		可以不依赖Eureka，通过白名单方式直接在application.properties中配置已知的服务接口地址；
		可以依赖Eureka来提供服务地址，从Eureka中获取服务地址并进行服务调用；
		Ribbon复制均衡策略可自定义扩展实现；

#### Ribbon负载均衡规则-Rule

	IRule
		ClientConfigEnabledRoundRobinRule
			BestAvailableRule	最佳可用规则
			PredicateBasedRule	
				AvailabilityFilteringRule	可用性过滤规则
				ZoneAvoidanceRule				规避区域规则
		RandomRule	随机规则
		RetryRule		重试规则
		RoundRobinRule		轮询规则
			WeightedResponseTimeRule		RT权重规则

#### 自定义Ribbon的规则（客户端-consumer）
	Provide a Key to Ribbon’s IRule - 将业务字段传递到IRule的实现类中，供负载均衡算法使用
		RequestContext.getCurrentContext()
		        .set(FilterConstants.LOAD_BALANCER_KEY, "canary-test");

		注意：
		Above code must be executed before RibbonRoutingFilter is executed and Zuul’s pre filter is the best place to do that. 
			
			
## Feign 整合Hystrix
	Feign可以利用hystrix来提供服务降级/回退的功能。
	即当远程服务调用失败时，用一个默认的实现来替代远程服务。
	返回给客户端的是预先定制好的结果，而不是真正的服务调用结果。
	
#### Feign Hystrix Fallbacks
	Hystrix supports the notion of a fallback: a default code path that is executed when they circuit is open or there is an error.

	两种Fallback方式：
		1、仅提供默认返回值，不关心服务调用时发生的异常：
				HystrixClientFallback
				
		2、可提供默认返回值，而且可获取到服务调用时所发生的异常：
				HystrixClientFallbackFactory implements FallbackFactory<PersonService>
	
	
	Feign开启Hystrix的步骤：
		1、依赖：spring-cloud-starter-hystrix
		2、启用hystrix：@EnableHystrix
		3、启用Feign的hystrix支持：feign.hystrix.enabled=true
		4、在服务接口契约上，配置fallback属性或者fallbackFactory属性，指向对应的处理类class
		5、将Hystrix Fallback或FallbackFactory作为Bean发布到spring容器中
	
	