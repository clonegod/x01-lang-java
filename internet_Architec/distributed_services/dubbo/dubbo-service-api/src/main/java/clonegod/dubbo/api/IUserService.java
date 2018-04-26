package clonegod.dubbo.api;

import java.util.List;

public interface IUserService {
	
	public String sayHello(String name);
	
	public User getById(long id);
	
	public List<User> getAllUsers();
	
}
