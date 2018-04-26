package singleton;

public class LazyMan {
	
	private LazyMan() {}
	
	private static /*volatile*/ LazyMan INSTANCE;
	
	public static synchronized LazyMan getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new LazyMan();
		}
		return INSTANCE;
	}
	
	public static LazyMan getInstance2() {
		if(INSTANCE == null) {
			synchronized (LazyMan.class) {
				if(INSTANCE == null) {
					INSTANCE = new LazyMan();
				}
			}
		}
		return INSTANCE;
	}
	
}
