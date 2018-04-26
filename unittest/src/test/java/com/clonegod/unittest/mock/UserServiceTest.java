package com.clonegod.unittest.mock;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.clonegod.unittest.dao.UserDao;
import com.clonegod.unittest.model.User;
import com.clonegod.unittest.service.UserService;


public class UserServiceTest extends MockitoBasedTest {
	
	// 声明一个模拟的userDao对象，该模拟对象将被注入到userService中
	@Mock
	UserDao userDao;
	
	// 被@Mock标注的对象会自动注入到被@InjectMocks标注的对象中
	@InjectMocks
	UserService userService;

	@Test
	public void testFindUser() {
		// mock userDao各个方法的入参与返回值
		when(userDao.findUser("alice")).thenReturn(new User("alice", "alice123"));
		when(userDao.findAnyUser()).thenReturn(new User("bob", "bob123"));
		
		User alice = userService.findUser("alice");
		assertEquals("alice", alice.getUsername());
		assertEquals("alice123", alice.getPassword());
		
		// spy 可以使一个对象的一部分方法被替换
		userService = spy(userService);
		doNothing().when(userService).checkUserName(anyString());
		
		User bob = userService.findUser(null);
		assertEquals("bob", bob.getUsername());
		assertEquals("bob123", bob.getPassword());
	}

}
