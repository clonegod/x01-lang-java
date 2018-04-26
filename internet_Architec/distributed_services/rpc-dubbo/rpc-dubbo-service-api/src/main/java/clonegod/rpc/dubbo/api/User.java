package clonegod.rpc.dubbo.api;

import java.io.Serializable;

/**
 * User对象需要在网络上传输，必须实现序列化接口
 * 
 * @author clonegod@163.com
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1071686750375691408L;
	
	private String username;
	private String realName;
	private String certNo;
	private boolean active;
	
	public User() {
		super();
	}
	public User(String username, String realName, String certNo, boolean active) {
		super();
		this.username = username;
		this.realName = realName;
		this.certNo = certNo;
		this.active = active;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", realName=" + realName + ", certNo=" + certNo + ", active=" + active
				+ "]";
	}
	
}
