package clonegod.rpc.dubbo.service.impl;

import java.util.HashMap;
import java.util.Map;

import clonegod.rpc.dubbo.api.User;
import clonegod.rpc.dubbo.service.UserService;

public class UserServiceImpl implements UserService {
	static Map<String, User> users = new HashMap<>();
	
	static {
		users.put("alice", new User("alice", "爱丽丝", "123456", true));
		users.put("bob", new User("bob", "鲍勃", "123456789", false));
	}

	@Override
	public User getUserInfo(String username) {
		User user = users.get(username);
		return user;
	}

	
}
