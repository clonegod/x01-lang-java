package com.asynclife.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asynclife.mappers.UserMapper;
import com.asynclife.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper UserMapper;

	@Override
	public User getUserById(int id) {
		return UserMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> getUsers() {
		return UserMapper.selectAll();
	}

	@Override
	public int insert(User User) {
		
		int result = UserMapper.insert(User);
		
		System.out.println(result);
		return result;
	}

}