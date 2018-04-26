package securityproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AccountSecurityProxy implements InvocationHandler { 
	
	 private Object realTarget; 
	 
	 public AccountSecurityProxy(Object o) { 
		 realTarget = o; 
	 } 
		
	 public Object invoke(Object object, Method method, Object[] arguments) throws Throwable { 
		 if (object instanceof IAccount && method.getName().equals("operation")) { 
			 // 在调用方法前，进行安全检查
			 SecurityChecker.checkSecurity(); 
		 } 
		 // 执行被代理对象的方法
		 Object retValue = method.invoke(realTarget, arguments);
		 return retValue;
	 }
	 
	 /**
	  * @return 返回Account接口的代理对象
	  */
	public IAccount getSecurityProxy() {
		 return (IAccount) Proxy.newProxyInstance( 
				 realTarget.getClass().getClassLoader(), 
				 realTarget.getClass().getInterfaces(), 
				 this
			 ); 
	 }

 }