package state;

public class ContextServlet {

	private UserState userState = new LogOutState();

	public UserState getState() {
		return this.userState;
	}

	public void doService(String userId, String password, String btnType) {
		
		if ("Login".equals(btnType)) {
			this.getState().login(userId, password);
		} else if ("Logout".equals(btnType)) {
			this.getState().logout();
		}

		String nextPage = this.getState().getNextPage();
		System.out.println("跳转至：" + nextPage);
	}
}
