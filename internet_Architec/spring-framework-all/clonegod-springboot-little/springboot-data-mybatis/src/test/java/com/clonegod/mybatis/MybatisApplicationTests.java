package com.clonegod.mybatis;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.clonegod.mybatis.domain.User;
import com.clonegod.mybatis.service.UserService;
import com.github.pagehelper.Page;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTests {

	@Autowired
	UserService userService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void getAllUsersNameLike() throws InterruptedException {
		int pageNum = 1;
		int pageSize = 2;
		while(true) {
			System.out.printf("\n------pageNum: %d PageSize: %d-------\n", pageNum, pageSize);
			List<User> userList = userService.getAllUserByNameLike("Alice%", pageNum, pageSize);
			userList.forEach(System.out::println);
			long total = ((Page<User>)userList).getTotal();
			System.out.println("Total=" + total);
			if(pageNum * pageSize < total) {
				Thread.sleep(1000);
				pageNum += 1;
			} else {
				System.out.println("所有记录已查询完毕，退出！");
				break;
			}
		}
	}
	
	@Test
	public void getUserById() {
		System.out.println(userService.getUserById(1));
	}
	
	@Test
	public void saveUser() {
		User user = new User("Bob007", 23, new Date());
		User userWithId = userService.saveUser(user);
		System.out.println(userWithId);
	}
	
	@Test	
	public void selectUserNameById() {
		String name = userService.getUserNameById(1);
		System.out.println(name);
	}
}
