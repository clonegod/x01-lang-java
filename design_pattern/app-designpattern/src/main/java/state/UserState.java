package state;

/**
 * 将变化封装到不同的状态对象中
 * 
 * @author Administrator
 *
 */
public abstract class UserState {
	
	private UserState state;
	
	private String nextPage;
	
	protected static String PAGE_WELCOME = "welcome.html";
	protected static String PAGE_LOGIN = "login.html";
	
	public UserState() {
		nextPage = PAGE_LOGIN;
	}
	
	
	public abstract boolean login(String userId, String password);
	
	public abstract void logout();
	
	public void setCurrentState(UserState state) {
		this.state = state;
	}
	
	public UserState getCurrentState() {
		if(this.state == null) {
			this.state = new LoginState();
		}
		return state;
	}
	
	public String getNextPage() {
		return nextPage;
	}
	
	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}
}
