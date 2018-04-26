package dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MapperProxy implements InvocationHandler {
	
	private UserMapper mapper;

	public MapperProxy(UserMapper target) {
		super();
		this.mapper = target;
	}
	

	public UserMapper getMapper() {
		return (UserMapper) Proxy.newProxyInstance(
					mapper.getClass().getClassLoader(),
					mapper.getClass().getInterfaces(),
					this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(this.getClass().getName() + ": before proxy execute...");
		
		Object result = method.invoke(mapper, args);
		
		System.out.println(this.getClass().getName() + ": after proxy execute...");
		
		return result;
	}
	
}
