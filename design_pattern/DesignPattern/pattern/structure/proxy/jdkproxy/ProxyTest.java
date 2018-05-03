package jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

public class ProxyTest {
/**
 * The principle of Separation of Concerns is one of the main aspects of modern application frameworks like Spring or Hibernate. 
 * 
 * The intention is to separate the cross-cutting-concerns 
 * 	(e.g. database access, transaction management or security checks) 
 * 		from the implementation of the functional requirements. 
 * 
 * One possible solution to realize a transparent separation of concerns is to use the proxy design pattern.
 *
 */
	interface Service {
		public void foobar();
	}

	static class ServiceToBeMonitored implements Service {
		@Override
		public void foobar() {
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	static class PerformanceMonitor implements InvocationHandler {
		private final Object proxiedInstance;

		public PerformanceMonitor(Object proxiedInstance) {
			this.proxiedInstance = proxiedInstance;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			long t0 = System.nanoTime();
			Object result = method.invoke(proxiedInstance, args);
			long t1 = System.nanoTime();
			long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
			System.out.println("Invocation of method " + method.getName() + "() took " + millis + " ms");
			return result;
		}
	}

	public static void main(String[] args) {
		Service service = new ServiceToBeMonitored();
		PerformanceMonitor handler = new PerformanceMonitor(service);
		Service proxy = (Service) Proxy.newProxyInstance(service.getClass().getClassLoader(),
				new Class[] { Service.class }, handler);
		proxy.foobar();
	}

}
