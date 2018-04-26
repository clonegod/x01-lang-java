package com.mybatis.sample1.test;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.mybatis.sample1.dao.UserRepository;
import com.mybatis.sample1.model.User;


// 设置测试类中所有方法的执行顺序：按方法名称的ASCII先后顺序依次执行
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
	
	UserRepository userRepo;
	
	@Before
	public void setUp() {
		userRepo = new UserRepository();
	}
	
	
	@Test
	public void testOrder01_Insert() {
		User u1 = new User();
		u1.setUsername("alice");
		u1.setPassword("123456");
		u1.setFirstName("firstName=");
		u1.setLastName("lastName");
		userRepo.insert(u1);
		
		User u2 = userRepo.getById(u1.getId());
		System.out.println(u2);
		
	}
	
	@Test
	public void testOrder02_Update() {
		User user = new User();
		user.setId(1);
		user.setFirstName("alice");
		user.setLastName("Li");
		userRepo.updateByMapperInterface(user);
	}
	
	@Test
	public void testOrder03_Update() {
		User user = new User();
		user.setId(2);
		user.setFirstName("alice");
		user.setLastName("Green");
		userRepo.updateBySqlId(user);
	}

	@Test
	public void testOrder04_delete() {
		userRepo.deleteByAnnotation(1);
	}
	
}
