package com.asynclife.service;

import java.util.List;

import com.asynclife.model.User;

public interface UserService {

	User getUserById(int id);
	
	List<User> getUsers();
	
	int insert(User userInfo);
}
