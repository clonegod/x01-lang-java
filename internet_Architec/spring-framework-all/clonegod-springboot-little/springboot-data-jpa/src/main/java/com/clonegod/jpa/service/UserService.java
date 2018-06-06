package com.clonegod.jpa.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.clonegod.jpa.entity.User;
import com.clonegod.jpa.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepos;
	
	/**
	 * 事务管理
	 * save, update, delete 需要在事务上下文中运行，使用@Transactional对相关方法进行事务绑定。
	 * 
	 */
	
	@Transactional
	public void save(User user) {
		userRepos.save(user);
	}
	
	@Transactional
	public void delete(int userId) {
		userRepos.delete(userId);
	}
	
	public User get(int userId) {
		return userRepos.findOne(userId);
	}
	
	public Iterable<User> getAll() {
		return userRepos.findAll();
	}
	
	/**
	 * 排序
	 */
	public Iterable<User> getAllSort() {
		return userRepos.findAll(new Sort(Sort.Direction.ASC, "age", "name"));
	}
	
	/**
	 * LIKE 模糊查询
	 */
	public Iterable<User> findByName(String userName) {
		return userRepos.findByNameLike(userName);
	}
	
	/**
	 * 分页查询
	 */
	public Page<User> findAll(Pageable pageable) {
		return userRepos.findAll(pageable);
	}
}
