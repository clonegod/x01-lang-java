package clonegod.netty05.heartbeat;

import java.io.Serializable;

public class AuthInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String ip;
	private String key;
	
	public AuthInfo(String ip, String key) {
		super();
		this.ip = ip;
		this.key = key;
	}
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "AuthInfo [ip=" + ip + ", key=" + key + "]";
	}
	
}
