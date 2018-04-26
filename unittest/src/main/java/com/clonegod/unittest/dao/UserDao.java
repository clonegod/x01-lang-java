package com.clonegod.unittest.dao;

import org.springframework.stereotype.Repository;

import com.clonegod.unittest.model.User;

@Repository
public class UserDao {
	
	public User findUser(String username) {
		return new User(username, "123456");
	}
	
	public User findAnyUser() {
		return new User("BOB", "123456");
	}
	
}
