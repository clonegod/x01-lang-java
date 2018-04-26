package singleton;

/**
 * 枚举-属于注册式单例
 *
 */
public enum RegisterManEnum {
	
	RED, WHITE, BLACK;
	
	public String getName() {
		return this.name().toLowerCase();
	}
	
}
