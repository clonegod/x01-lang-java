package singleton;

public class LazySingleton {
	
	private LazySingleton() {}
	
	private static LazySingleton self = null;
	
	
	public synchronized static LazySingleton getInstance() {
		if(self == null) {
			self = new LazySingleton();
		}
		return self;
	}
	
}
