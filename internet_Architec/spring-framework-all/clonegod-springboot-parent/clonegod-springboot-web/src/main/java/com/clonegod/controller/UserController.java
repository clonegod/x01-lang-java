package com.clonegod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clonegod.api.User;
import com.clonegod.service.UserService;

// 使用 Spring Web MVC
@RestController // 等效于  @Controller + @ResponseBody
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
		this.userService = userService;
	}


	/**
     * RequestMappingHandlerMapping
     * 解读为 @RequestMapping Handler Mapping 处理映射关系
     * 	
     * 	RequestMapping 	绑定请求路由到handler上
     * 	Handler 		指的是 geUser()
     *  Mapping			将客户端请求的URI映射到匹配路由的handler方法上
     */
    @RequestMapping(path="/clonegod",method=RequestMethod.GET)
    public User getUser() {
    	return userService.findOne(100L);
    }
    
    /**
     * @PostMapping 是 @RequestMapping 的复合体，为新的REST风格而提供
     * 	@RequestMapping(method = RequestMethod.POST)
     * 
     * REST:
     * @PostMapping		Create
     * @GetMapping		Read
     * @PutMapping		Update
     * @DeleteMapping	Delete
     * 
     */
    @PostMapping("/user/save")
    public User save(@RequestBody User user) {
        userService.save(user);
        return user;
    }
    
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
    	return userService.findOne(id);
    }
    
}

