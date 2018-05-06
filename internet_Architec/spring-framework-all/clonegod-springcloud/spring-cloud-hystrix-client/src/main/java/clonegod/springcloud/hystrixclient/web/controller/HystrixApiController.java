package clonegod.springcloud.hystrixclient.web.controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCommandGroupKey;

@RestController
public class HystrixApiController {
	
	private static final Random random = new Random();
	
	@GetMapping("/hello2")
	public String hello2() throws Exception {
		return new HelloHystrixCommand("MySimpleGroup", 100).execute();
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
