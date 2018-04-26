package com.clonegod.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.clonegod.demo.model.User;

@Repository
public interface UserMapper {
	
	List<User> findUserByUsername(@Param("username") String username);

	int getCount(@Param("username") String username);

}
