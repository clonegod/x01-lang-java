package state;

import sun.rmi.log.LogOutputStream;

public class LoginState extends UserState {

	/**
	 * 已登录状态下，再次登录
	 */
	@Override
	public boolean login(String userId, String password) {
		setNextPage(PAGE_WELCOME);
		setCurrentState(new LoginState());
		return true;
	}

	/**
	 * 已登录状态下，进行登出操作
	 */
	@Override
	public void logout() {
		setNextPage(PAGE_LOGIN);
		setCurrentState(new LogOutState());
	}

}
