package proxy.protectandlog;

public class AccessValidator {
	public boolean validateUser(String userId) {
		return "Admin".equals(userId);
	}
}
