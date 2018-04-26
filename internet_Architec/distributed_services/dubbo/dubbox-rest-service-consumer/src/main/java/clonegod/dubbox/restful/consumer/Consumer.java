package clonegod.dubbox.restful.consumer;

import java.util.Date;
import java.util.UUID;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import clonegod.dubbox.restful.api.IUserService;
import clonegod.dubbox.restful.api.User;

public class Consumer {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "dubbo-consumer.xml" });
		context.start();
		
		// 按dubbo rpc的方式，调用provider提供的服务，这里的测试与restful无关 
		IUserService us = (IUserService) context.getBean("userService");
		
		System.out.println("----------测试GET");
		System.out.println(us.getUser());
		System.out.println(us.getUser(1));
		System.out.println(us.getUser(1, "b1"));
		
		
		System.out.println("----------测试POST");
		us.testPost();
		System.out.println(us.postUser(UUID.randomUUID().toString()));
		System.out.println(us.postUser(new User("1", "alice", "123456", 20, new Date())));

		
		System.out.println("----------测试PUT");
		System.out.println(us.testPut(new User("2", "bob", "123456", 20, new Date())));
		
		System.out.println("----------测试DELETE");
		System.out.println(us.testDelete(1));
		
		context.close();
		
	}

}