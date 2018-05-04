package clonegod.user.service.provider.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import clonegod.user.domain.User;
import clonegod.user.service.UserService;

/**
 * 用户服务提供方 
 *
 */
@RestController
public class UserRestApiController {

	private final UserService userService;

	@Autowired
	public UserRestApiController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostMapping("/user/save")
	public Object saveUser(@RequestBody User user) {
		boolean success = userService.saveUser(user);
		
		return success ? user : null;
	}
	
	@GetMapping("user/list")
	public Object findAll() {
		return userService.findAll();
	}
	
}
