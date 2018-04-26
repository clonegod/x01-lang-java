package clonegod.rpc.dubbo.service;

import clonegod.rpc.dubbo.api.User;

public interface UserService {
	
	public User getUserInfo(String username);
	
}
