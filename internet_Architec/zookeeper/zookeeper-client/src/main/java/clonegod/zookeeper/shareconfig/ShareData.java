package clonegod.zookeeper.shareconfig;

/**
 * 共享配置类---设置为单例
 *
 */
public class ShareData {
	
	private String url;
	private String username;
	private String password;

	private ShareData() {}
	
	private static final ShareData instance = new ShareData();
	
	public static ShareData getInstance() {
		return instance; 
	}
	
	public ShareData url(String url) {
		this.url = url;
		return this;
	}
	public ShareData username(String username) {
		this.username = username;
		return this;
	}
	public ShareData password(String password) {
		this.password = password;
		return this;
	}

	@Override
	public String toString() {
		return "ShareData [url=" + url + ", username=" + username + ", password=" + password + "]";
	}
	public void printDatas() {
		System.out.println("==============Reload==================");
		System.out.println(toString());
	}
	
	
	
}
