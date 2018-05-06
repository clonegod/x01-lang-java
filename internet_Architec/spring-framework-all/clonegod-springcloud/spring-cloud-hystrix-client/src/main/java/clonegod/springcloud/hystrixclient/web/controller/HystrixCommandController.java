package clonegod.springcloud.hystrixclient.web.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class HystrixCommandController {
	
	private static final Random random = new Random();

	/**
	 * 注解方式使用 Hystrix
	 * 
	 * 	当{@link #hello()} 方法调用超时或失败时，fallback 方法{@link #handleError()} 将作为替代值返回给客户端（客户端需要能够理解返回的内容）
	 */
	@GetMapping("/hello")
	// The @HystrixCommand is provided by a Netflix contrib library called "javanica".
	@HystrixCommand(fallbackMethod="handleError",
					commandProperties= {
							@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="100")
					})
	public String hello() throws Exception {
		int rand = random.nextInt(200);
		
		System.out.printf("process request take %s mills\n", rand);
		
		// 如果随机值大于100，则触发hystrix熔断
		TimeUnit.MILLISECONDS.sleep(rand);
		
		return "Hello,World";
	}
	
	// 处理熔断
	public String handleError() {
		return "9900: Server Fault - Hystrix by javanica";
	}
	
}
