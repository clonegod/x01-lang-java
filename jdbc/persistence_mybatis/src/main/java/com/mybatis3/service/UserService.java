package com.mybatis3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatis3.domain.User;
import com.mybatis3.mappers.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;

	public User getUserByUserName(String username){
		User user = userMapper.getUser(username);
		return user;
	}
	
	@Transactional
	public void insertUser(User user) {
		int count = userMapper.insertUser(user);
		// int i = 1 / 0; // rollback
		System.out.println("插入条"+count+"记录");
	}
}