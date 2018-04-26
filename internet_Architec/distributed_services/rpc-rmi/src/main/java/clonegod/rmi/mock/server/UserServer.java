package clonegod.rmi.mock.server;

import clonegod.rmi.mock.api.User;

public class UserServer extends User {
	
	public static void main(String[] args) {
		
		UserServer user = new UserServer();
		user.setAge(18);
		
		User_Skeleton skeleton = new User_Skeleton(user);
		
		skeleton.start();
		
	}
	
}
