package guava.reflection;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Reflection;

public class ClassPathExample {
	
	
	@Test
	public void test_invokable() throws Exception {
		MyAction myAction = new MyAction();
		Invokable invokable = Invokable.from(myAction.getClass().getDeclaredMethod("privateMethod"));
		if(! invokable.isAccessible()) {
			invokable.setAccessible(true);
		}
		invokable.invoke(myAction);
	}
	
	/**
	 * 通过package搜索class文件有什么用？
	 * 	通过给定的basePackage路径，找到该路径下的class，利用反射创建实例。 
	 * 	实际应用 ---> dao下操作mybatis的接口  
	 * 		<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer" >
				<property name="basePackage" value="com.creditease.fetch.gjj.dao" />
	 * 			<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
	 *		</bean>
	 *
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void test_ClassPath() throws IOException, ClassNotFoundException {
		// sun.misc.Launcher$AppClassLoader@4e25154f
		System.out.println("ClassLoader: " + Thread.currentThread().getContextClassLoader());
		
		ClassPath classpath = ClassPath.from(Thread.currentThread().getContextClassLoader());
		// 指定包
		for( ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses("app.mybatis.dao")) {
			Class<?> clazz = Class.forName(classInfo.getName());
			System.out.println(clazz);
		}
		System.out.println();
		// 指定包及子包
		for( ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive("app.mybatis")) {
			Class<?> clazz = Class.forName(classInfo.getName());
			System.out.println(clazz);
		}
	}
	
	
	@Test
	public void test_daynamicProxy1() {
		Action actionProxy = new ActionWrapper(new MyAction()).getProxy();
		Object rs = actionProxy.execute("alice");
		System.out.println(rs);
	}
	
	@Test
	public void test_daynamicProxy2() {
		InvocationHandler sharedHanlder = new ActionProxy(new MyAction());
		Action actionProxy = Reflection.newProxy(Action.class, sharedHanlder);
		Object rs = actionProxy.execute("alice");
		System.out.println(rs);
		
		Action actionProxy2 = Reflection.newProxy(Action.class, sharedHanlder);
		
		// A proxy instance is equal to another proxy instance if they are for the same interface types and have equal invocation handlers.
		System.out.println(actionProxy.equals(actionProxy2));
		
	}
	
	
	
	
	interface Action {
		Object execute(String name);
	}
	
	static class MyAction  implements Action {

		@Override
		public Object execute(String name) {
			System.out.println("MyAction run...");
			return "hello:" + name;
		}
		
		private void privateMethod() {
			System.out.println("reflection.ClassPathExample.MyAction.privateMethod()");
		}

	}
	
	static class ActionWrapper implements InvocationHandler {
		private Action target;
		public ActionWrapper(Action action) {
			this.target = action;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println(proxy.getClass() == this.getClass());
			System.out.println("before execute");
			Object rs = method.invoke(target, args);
			System.out.println("after execute");
			return rs;
		}
		
		public Action getProxy() {
			return (Action) Proxy.newProxyInstance(
					target.getClass().getClassLoader(), 
					target.getClass().getInterfaces(), 
					this);
		}
		
	}
	
	static class ActionProxy extends AbstractInvocationHandler {
		private Action target;
		
		public ActionProxy(Action action) {
			this.target = action;
		}

		@Override
		protected Object handleInvocation(Object proxyObject, Method paramMethod, Object[] paramArrayOfObject)
				throws Throwable {
			System.out.println("before execute");
			Object rs = paramMethod.invoke(target, paramArrayOfObject);
			System.out.println("after execute");
			return rs;
		}
	}
}
