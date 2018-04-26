package com.clonegod.fastjson.web;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clonegod.fastjson.model.User;

@RestController
public class SampleController {

    @RequestMapping(path="/fastjson/user", produces="application/json")
    public User user() {
    	User user = new User(100L, "Alice-爱丽丝", new Date());
    	user.setRemark("备注信息");
    	return user;
    }

}