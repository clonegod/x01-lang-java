package com.clonegod.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clonegod.jdbc.dao.UserDao;
import com.clonegod.jdbc.model.User;

@Service
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	// 设置事务环境
	@Transactional
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}
	
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}
}
