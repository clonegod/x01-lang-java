package com.clonegod.jdbc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clonegod.jdbc.model.Order;
import com.clonegod.jdbc.model.User;
import com.clonegod.jdbc.service.OrderService;
import com.clonegod.jdbc.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	UserService userService;
	
	@Autowired
	OrderService orderService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testSaveUser() {
		int id = userService.insertUser(new User("Alice", 20, new Date()));
		System.out.println("id=" + id);
	}
	
	@Test
	public void testFindUser() {
		System.out.println(userService.findByUserName("Alice"));
	}

	@Test
	public void testBatchInsertOrders() throws InterruptedException {
		ThreadLocalRandom trand = ThreadLocalRandom.current();
		for(int n = 1; n <= 10; n++) {
			System.out.println(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()) + ", n=" + n);
			List<Order> orders = new ArrayList<>();
			for(int i = 1; i <= 10_000; i++) {
				orders.add(new Order((short)trand.nextInt(10), trand.nextInt(1000000), new Date()));
			}
			orderService.batchInsert(orders);
		}
	}
}
