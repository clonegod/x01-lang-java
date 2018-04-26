package clonegod.dubbo.user.dto;

import java.io.Serializable;

public class UserLoginResponse extends Response implements Serializable{
    private static final long serialVersionUID = -4595924096093523471L;

	public UserLoginResponse() {
		super();
	}

	public UserLoginResponse(String code, String memo) {
		super(code, memo);
	}

	public static UserLoginResponse fail(String code, String memo) {
    	return new UserLoginResponse(code, memo);
    }
	
	public static UserLoginResponse success() {
		return new UserLoginResponse("000000", "SUCCESS");
	}
}
