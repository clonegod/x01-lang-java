package state;

public class LogOutState extends UserState {
	
	/**
	 * 已登出的状态下，进行登录操作
	 */
	@Override
	public boolean login(String userId, String password) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT COUNT(*) FROM user_info WHERE user_id = ?")
			.append(" AND password = ?");
		int count = 1; //query from db
		
		boolean isValid = count > 0;
		if(isValid) {
			this.setNextPage(PAGE_WELCOME);
			this.setCurrentState(new LoginState());
		} else {
			this.setNextPage(PAGE_LOGIN);
			this.setCurrentState(new LogOutState());
		}
		
		return isValid;
	}

	/**
	 * 已登出的状态下，再次登出
	 */
	@Override
	public void logout() {
		setNextPage(PAGE_LOGIN);
		setCurrentState(new LogOutState());
	}

}
