package com.clonegod.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clonegod.mybatis.domain.User;
import com.clonegod.mybatis.mapper.UserMapper;
import com.github.pagehelper.PageHelper;

@Service
public class UserService {
	
	@Autowired
	UserMapper userMapper;
	
	public List<User> getAllUserByNameLike(String name, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize, true);
		return userMapper.getAllUsersByNameLike(name);
	}
	
	public User getUserById(int id) {
		return userMapper.getUserById(id);
	}
	
	/**
	 * 保存user到数据库
	 * 
	 * @param user
	 * @return	插入记录的id
	 */
	@Transactional
	public User saveUser(User user) {
		userMapper.saveUser(user);
		System.out.println("新插入用户的id=" + user.getId());
		return user;
	}
	
	
	public String getUserNameById(int id) {
		return userMapper.getUserNameById(id);
	}
}
