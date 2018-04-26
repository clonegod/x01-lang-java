package singleton;

/** 
 * 静态内部类中创建对象  
 */
public class InnerClassHolderMan {
	
	public static InnerClassHolderMan getInstance() {
		/* 该方法被调用时，才会真正实例化单例对象 */
		return LazyHolder.INSTANCE;
	}
	
	/** 不需要持有外部类的实例，也不需要访问外部类的成员属性和方法，因此定义为static类型的内部嵌套类  STATIC NESTED CLASS  */
	private static class LazyHolder {
		private static final InnerClassHolderMan INSTANCE = new InnerClassHolderMan();
	}
	
	// 额外增强，防止被反射创建对象
	private static boolean initialized = false;
	private InnerClassHolderMan() {
		System.out.println("constructor execute...");
		if(! initialized) {
			initialized = true; // 通过状态变量来限制构造函数只被调用1次
		} else {
			throw new RuntimeException("Warning: 单例规则已被侵犯"); // 非正常途径获取单例对象，直接抛异常
		}
	}
	
	// 测试
	public static void main(String[] args) {
		try {
			InnerClassHolderMan instance = InnerClassHolderMan.getInstance();
			System.out.println(instance);
			
			// 测试通过反射来实例化单例类 
			InnerClassHolderMan evilObj = 
					(InnerClassHolderMan) Class.forName("singleton.StaticInnerClass").newInstance();
			
			System.out.println(evilObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
