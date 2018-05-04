package clonegod.user.service.consumer.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import clonegod.user.domain.User;
import clonegod.user.service.UserService;

@RestController
public class UserRestApiController {

	private final UserService userService;

	@Autowired
	public UserRestApiController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/user/save")
	public Object saveUser(@RequestBody User user) {
		boolean success = userService.saveUser(user);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", success);
		resultMap.put("user", user);
		
		return resultMap;
	}
	
	@GetMapping("user/list")
	public Object findAll() {
		return userService.findAll();
	}
	
	
}
