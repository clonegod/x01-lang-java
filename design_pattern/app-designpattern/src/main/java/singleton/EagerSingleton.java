package singleton;

public class EagerSingleton {
	
	private EagerSingleton() {}
	
	private static final EagerSingleton self = new EagerSingleton();
	
	public static EagerSingleton getInstance() {
		return self;
	}
	
}
