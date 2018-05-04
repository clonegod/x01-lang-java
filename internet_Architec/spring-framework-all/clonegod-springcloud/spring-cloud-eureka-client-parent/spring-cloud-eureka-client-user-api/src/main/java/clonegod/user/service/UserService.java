package clonegod.user.service;

import java.util.Collection;

import clonegod.user.domain.User;

public interface UserService {
	
	public boolean saveUser(User user);
	
	
	public Collection<User> findAll();
	
}
