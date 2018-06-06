package com.clonegod.jpa.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clonegod.jpa.entity.User;
import com.clonegod.jpa.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/user/list")
    public Page<User> list(Pageable pageable){
        return userService.findAll(pageable);
    }

}
