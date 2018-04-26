package singleton;

import java.io.Serializable;

public class SerializeMan implements Serializable {
	
	private static final long serialVersionUID = 4455437186520041024L;

	private SerializeMan() {}
	
	private final static SerializeMan INSTANCE = new SerializeMan();
	
	public static SerializeMan getInstance() {
		return INSTANCE;
	}
	
	/** 
	 * 序列化的约定方法
	 * 	- 反序列化时返回给定的已有对象实例，而不是从新创建一个!
	 */
	public Object readResolve() {
		return INSTANCE;
	}
	
}
