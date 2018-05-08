package com.clonegod.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping(value= {"/", "/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping(value= {"/error-500"})
	public String error500() {
		throw new RuntimeException("测试异常情况下浏览器显示的页面");
	}
	
}
