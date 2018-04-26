package proxy.list;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ListProxyFactory implements InvocationHandler {
	
	private Object target;
	
	public ListProxyFactory(Object target) {
		this.target = target;
	}
	
	public Object getProxy() {
		Class<?> cls = target.getClass();
		return Proxy.newProxyInstance(
				cls.getClassLoader(), 
				cls.getInterfaces(), 
				this);
	}
	
	/**
	 * 代理对象被调用时，此方法便会被调用
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		before();
		for(Object arg : args) {
			System.out.println("arg= "+arg);
		}
		Object ret = method.invoke(target, args);
		after();
		return ret;
	}

	private void before() {
		System.out.println("before ...");
	}
	
	private void after() {
		System.out.println("after ...");
	}

}
