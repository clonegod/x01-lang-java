package singleton;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例容器--注册式单例，类似spring单例bean的容器，每个对象仅被注册1次
 *
 */
public class RegisterMan {
	
	private RegisterMan() {}
	
	private static final ConcurrentHashMap<String, Object> register = new ConcurrentHashMap<>();
	
	public static Object getInstance(String className) {
		if(className == null) {
			className = RegisterMan.class.getName();
		}
		
		try {
			register.putIfAbsent(className, Class.forName(className).newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return register.get(className);
	}
	
}
