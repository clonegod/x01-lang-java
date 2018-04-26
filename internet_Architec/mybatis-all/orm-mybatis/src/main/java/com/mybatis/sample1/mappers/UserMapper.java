package com.mybatis.sample1.mappers;

import org.apache.ibatis.annotations.Delete;

import com.mybatis.sample1.model.User;

public interface UserMapper {
	
	public User getUserById(int id);
	
	public int insertUser(User user);
	
	public void updateUser(User user);
	
	@Delete("DELETE FROM user where id = #{id}")
	public void deleteUser(int id);
	
}
