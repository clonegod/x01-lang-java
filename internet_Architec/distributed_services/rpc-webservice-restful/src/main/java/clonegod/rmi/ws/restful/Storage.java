package clonegod.rmi.ws.restful;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {
	
	public static final ConcurrentHashMap<Integer, User> USERS = new ConcurrentHashMap<>();
	
	
	static {
		USERS.put(1, new User(1, "Alice", new Date()));
		USERS.put(2, new User(2, "Bob", new Date()));
		USERS.put(3, new User(3, "Mic", new Date()));
	}
	
}
