package com.asynclife.service;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.asynclife.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring.xml",
		"classpath:spring/spring-mybatis.xml" })
public class TestUserService {

	private static final Logger LOGGER = Logger.getLogger(TestUserService.class);

	@Autowired
	private UserService userService;

	
	@Test
	public void testQueryById1() {
		User User = userService.getUserById(1);
		LOGGER.info(JSON.toJSON(User));
	}

	@Test
	public void testQueryAll() {
		List<User> Users = userService.getUsers();
		LOGGER.info(JSON.toJSON(Users));
	}

	@Test
	public void testInsert() {
		User User = new User();
		User.setUsername("xiaoming");
		User.setPassword("123abc");
		int result = userService.insert(User);
		System.out.println(result);
	}
}
