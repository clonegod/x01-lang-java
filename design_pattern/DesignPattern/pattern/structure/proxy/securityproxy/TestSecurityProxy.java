package securityproxy;

public class TestSecurityProxy {
	
	public static void main(String[] args) {
		
		IAccount account = new Account();
		
		AccountSecurityProxy securityAccount = new AccountSecurityProxy(account);
		
		IAccount accountProxye = securityAccount.getSecurityProxy();
		
		// 使用代理对象进行方法的调用
		accountProxye.operation();
		
		
	}
}
