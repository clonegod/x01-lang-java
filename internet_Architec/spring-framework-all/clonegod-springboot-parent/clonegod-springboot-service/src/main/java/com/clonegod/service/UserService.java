package com.clonegod.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clonegod.api.User;
import com.clonegod.respository.UserRepository2;

@Service
public class UserService {
	
	@Autowired
	UserRepository2 userRespository;
	
	public Boolean save(User user) {
        return userRespository.save(user);
    }

    public Collection<User> findAll() {
        return userRespository.findAll();
    }

	public User findOne(Long id) {
		return userRespository.findOne(id);
	}
	
}
