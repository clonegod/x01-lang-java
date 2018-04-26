package com.clonegod.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clonegod.demo.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("getTableData")
	public Map<String, Object> getTableData(int pageNum, int pageSize, String username) {
		try {
			return userService.getTableData(pageNum, pageSize, username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
