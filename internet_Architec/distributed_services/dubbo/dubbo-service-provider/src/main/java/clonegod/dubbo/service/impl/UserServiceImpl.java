package clonegod.dubbo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clonegod.dubbo.api.IAuthenticationService;
import clonegod.dubbo.api.IUserService;
import clonegod.dubbo.api.User;

public class UserServiceImpl implements IUserService {
	
	static Map<Long, User> USERS = new HashMap<>();
	
	IAuthenticationService authenticationService;
	
	static {
		try {
			User alice = new User(1L, "alice", "111111", 21, new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-01"));
			User bob   = new User(2L, "bob",   "222222", 22, new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-02"));
			User cindy = new User(3L, "cindy", "333333", 23, new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-03"));
			USERS.put(alice.getId(), alice);
			USERS.put(bob.getId(), bob);
			USERS.put(cindy.getId(), cindy);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String sayHello(String name) {
		return "Hello: " + name;
	}

	@Override
	public User getById(long id) {
		User user = USERS.get(id);
		boolean pass = authenticationService.authenticate(user.getName(), user.getPassword());
		if(pass) {
			user.setMessage(null);
		} else {
			user.setMessage("用户名或密码错误");
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		return new ArrayList<>(USERS.values());
	}

	public IAuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(IAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

}
