package clonegod.rpc.dubbo.service.consumer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import clonegod.rpc.dubbo.api.User;
import clonegod.rpc.dubbo.service.HelloService;
import clonegod.rpc.dubbo.service.UserService;

public class DubboServiceConsumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
        context.start();
        
        // obtain proxy object for remote invocation
        HelloService helloService = context.getBean("helloService", HelloService.class); 
        
        // execute remote invocation
        String result = helloService.sayHello("CloneGod");
        System.out.printf("sayHello, result = %s\n", result);
       
        // obtain proxy object for remote invocation
        UserService userService = context.getBean("userService", UserService.class); 
        
        int count = 0;
        while(true) {
        	if(++count >= 60) {
        		break;
        	}
        	// execute remote invocation
        	User alice = userService.getUserInfo("alice");
        	System.out.printf("getUserInfo, result = %s\n", alice.toString());
        	
        	User bob = userService.getUserInfo("bob");
        	System.out.printf("getUserInfo, result = %s\n", bob.toString());
        	
        	User cindy = userService.getUserInfo("cindy");
        	System.out.printf("getUserInfo, result = %s\n", cindy);
        	
        	Thread.sleep(1000);
        }
       
        
        context.close();
    }
}