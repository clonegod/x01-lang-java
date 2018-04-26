package bridge;

import bridge.driver.Driver;

/**
 * 提供桥梁作用-将调用者与具体行为的实现者进行隔离。
 * 	通过持有外部顶层接口来执行接口中定义的方法
 * 
 * 桥梁模式的关键：被桥梁连接的另一端实现某个特定的接口，具体实现类可以随便改变。如数据库驱动的切换，这些驱动都遵守JDBC接口规范。
 */
public abstract class Bridge {
	
	// 持有外部接口
	private Driver driver;

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}


	protected abstract void doConnection();
}
