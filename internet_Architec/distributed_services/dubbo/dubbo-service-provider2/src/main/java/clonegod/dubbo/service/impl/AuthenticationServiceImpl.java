package clonegod.dubbo.service.impl;

import clonegod.dubbo.api.IAuthenticationService;

public class AuthenticationServiceImpl implements IAuthenticationService {

	@Override
	public boolean authenticate(String username, String password) {
		return Math.random() > 0.5d;
	}

}
