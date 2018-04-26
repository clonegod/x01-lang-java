package clonegod.rmi.mock.client;

import java.io.IOException;

import clonegod.rmi.mock.api.User;

public class UserClient {

	public static void main(String[] args) {
		try {
			User user = new User_Stub();
			int age = user.getAge();
			System.out.println("age=" + age);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
