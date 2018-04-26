package com.mybatis3.service.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mybatis3.domain.User;
import com.mybatis3.service.UserService;

public class UserServiceTest {
	
	ApplicationContext ctx;
	
	@Before
	public void setUp() {
		 ctx = new ClassPathXmlApplicationContext("classpath:application*.xml");
	}
	
	@Test
	public void testQuery() {
		UserService userService = (UserService) ctx.getBean("userService");
		User user = userService.getUserByUserName("张三");
		System.out.println(user);
		
	}

	@Test
	public void testInsert() {
		UserService userService = (UserService) ctx.getBean("userService");
		User user = new User();
		user.setUsername("test1");
		user.setPassword("password1");
		
		userService.insertUser(user);
		
		System.out.println(user.getId());
	}
	
}
