package singleton;

/**
 * 饥饿式 
 */
public class EagerMan {
	
	private EagerMan() {}
	
	private static EagerMan INSTANCE = new EagerMan(); // 类加载后便主动实例化对象
	
	public static EagerMan getInstance() {
		return INSTANCE;
	}
	
}
