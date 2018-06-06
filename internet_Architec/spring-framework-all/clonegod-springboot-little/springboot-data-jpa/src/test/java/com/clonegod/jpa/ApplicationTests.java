package com.clonegod.jpa;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clonegod.jpa.entity.User;
import com.clonegod.jpa.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	UserService userService;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testSaveUser() {
		for(int i = 0; i < 10; i++) {
			userService.save(new User(
					"Alice" + ThreadLocalRandom.current().nextInt(9999999),
					10  + ThreadLocalRandom.current().nextInt(10),
					new Date()));
		}
	}
	
	@Test
	public void getUserById() {
		User user = userService.get(1);
		System.out.println(user.toString());
	}
	
	@Test
	public void getAllUsers() {
		userService.getAll().forEach(x -> System.out.println(x.toString()));
	}
	
	@Test
	public void getAllUsersSorted() {
		userService.getAllSort().forEach(x -> System.out.println(x.toString()));
	}
	
	@Test
	public void findUserByName() {
		userService.findByName("Alice9%").forEach(System.out::println);
	}
	
	@Test
	public void deleteUserById() {
		userService.delete(1);
	}
}
