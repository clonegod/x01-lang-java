# Spring Cloud Netflix Hystrix
###### hystrix 可以在服务端进行短路控制，也可以在客户端实现，类似AOP封装。
	
	hystrix有三种使用方式：
	
	第一种： 注解方式 - javanica 
	The @HystrixCommand is provided by a Netflix contrib library called "javanica". 
	Spring Cloud automatically wraps Spring beans with that annotation in a proxy that is connected to the Hystrix circuit breaker.
	The circuit breaker calculates when to open and close the circuit, and what to do in case of a failure.
	
	第2种： 编程方式
		private class HelloHystrixCommand extends com.netflix.hystrix.HystrixCommand<String> {
			// 执行任务
			protected String run() throws Exception { ... }
			
			// 熔断处理
			protected String getFallback() { ... }
		}
	
	第3种： hystrix dashboard
		

## 第1种： 注解方式
	激活Hystrix
		@EnableHystrix

	Hystrix 配置项
		详细参考 https://github.com/Netflix/Hystrix/wiki/Configuration
	
	
## 第2种： 编程方式

	import java.util.Random;
	import java.util.concurrent.TimeUnit;
	
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.RestController;
	
	import com.netflix.hystrix.HystrixCommandGroupKey;
	
	@RestController
	public class HystrixApiController {
		
		private static final Random random = new Random();
		
		@GetMapping("/hello2")
		public String hello() throws Exception {
			return new HelloHystrixCommand("SimpleGroup", 100).execute();
		}
		
		/**
		 * 编程方式使用Hystrix
		 *
		 */
		private class HelloHystrixCommand extends com.netflix.hystrix.HystrixCommand<String> {
			
			protected HelloHystrixCommand(String groupKey, int timeoutInMilliseconds) {
				super(HystrixCommandGroupKey.Factory.asKey(groupKey), timeoutInMilliseconds);
			}
	
			// 处理输入的内容
			@Override
			protected String run() throws Exception {
				int rand = random.nextInt(200);
				
				System.out.printf("process request take %s mills\n", rand);
				
				// 如果随机值大于100，则触发hystrix熔断
				TimeUnit.MILLISECONDS.sleep(rand);
				
				return "Hello,World";
			}
	
			// 容错执行
			@Override
			protected String getFallback() {
				return HystrixApiController.this.handleError();
			}
			
		}
	
		public String handleError() {
			return "9900: Server Fault - Hystrix by API";
		}
		
	}	


#### Hystrix Endpoint (/hystrix.stream)
	http://localhost:8080/hystrix.stream
	
	data: {
	    "type": "HystrixThreadPool",
	    "name": "HystrixCommandController",
	    "currentTime": 1525626767515,
	    "currentActiveCount": 0,
	    "currentCompletedTaskCount": 6,
	    "currentCorePoolSize": 10,
	    "currentLargestPoolSize": 6,
	    "currentMaximumPoolSize": 10,
	    "currentPoolSize": 6,
	    "currentQueueSize": 0,
	    "currentTaskCount": 6,
	    "rollingCountThreadsExecuted": 0,
	    "rollingMaxActiveThreads": 0,
	    "rollingCountCommandRejections": 0,
	    "propertyValue_queueSizeRejectionThreshold": 5,
	    "propertyValue_metricsRollingStatisticalWindowInMilliseconds": 10000,
	    "reportingHosts": 1
	}


	
## 第3种： Spring Cloud Hystrix DashBorad

#### 激活Hystrix DashBoard
	@EnableHystrixDashboard

#### Hystrix DashBoard (/hystrix) - 实时监测熔断情况
	打开 http://localhost:8080/hystrix
		将 http://localhost:8080/hystrix.stream 填入input框，就会实时输出hystrix的熔断情况
	
	缺点：
		将每个方法的熔断统计输出到Dashboard上，不方便查看。
		可以使用Turbine来解决这个问题。
	
#### 整合Netflix Turbine
	Looking at an individual instances Hystrix data is not very useful in terms of the overall health of the system. 
	
	Turbine is an application that aggregates all of the relevant /hystrix.stream endpoints into a combined /turbine.stream for use in the Hystrix Dashboard.
	
	
	
