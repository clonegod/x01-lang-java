package com.asynclife.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@RequestMapping("/hello")
	public String hello() {
		
		return "test01";
	}
	
	
}
