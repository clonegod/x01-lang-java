package com.clonegod.unittest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.clonegod.unittest.dao.UserDao;
import com.clonegod.unittest.model.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	
	public User findUser(String username) {
		
		// com.clonegod.unittest.dao.UserDao$$EnhancerByMockitoWithCGLIB$$b1b5f19c
		System.err.println(userDao.getClass().getName());

		User user = null;
		
		checkUserName(username);
		
		
		if(! StringUtils.isEmpty(username)) {
			user = userDao.findUser(username);
		} else {
			user = userDao.findAnyUser();
		}
		return user;  
	}
	
	public void checkUserName(String username) {
		if(username==null) {
			throw new IllegalArgumentException("username cann't be null"); 
		}
	}
	
}
