package clonegod.dubbo.user.dto;

import java.io.Serializable;

public class UserLoginRequest implements Serializable{

    private static final long serialVersionUID = -1885649422664747478L;
    private String username;

    private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserLoginRequest [username=" + username + ", password=" + password + "]";
	}
	
}
