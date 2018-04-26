package com.clonegod.jpa.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.clonegod.jpa.model.User;
import com.clonegod.jpa.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepos;
	
	/**
	 * save, update, delete 需要在事务上下文中运行，使用@Transactional对相关方法进行事务绑定。
	 * 
	 */
	
	@Transactional
	public void save(User user) {
		userRepos.save(user);
	}
	
	public User get(int userId) {
		return userRepos.findOne(userId);
	}
	
	public Iterable<User> getAll() {
		return userRepos.findAll();
	}
	
	public Iterable<User> getAllSort() {
		return userRepos.findAll(new Sort(Sort.Direction.ASC, "age", "name"));
	}
	
	
	public Iterable<User> findByName(String userName) {
		return userRepos.findByNameLike(userName);
	}
	
	@Transactional
	public void delete(int userId) {
		userRepos.delete(userId);
	}
}
