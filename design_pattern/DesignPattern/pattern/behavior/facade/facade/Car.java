package facade;

/**
 * Car 就是1个facade
 *
 */
public class Car {
	Engine engine;
	
	public Car() {
		this.engine = new Engine();
	}

	/**
	 * 简化外部调用，客户端只需要调用start即可。不需要了解内部启动的细节。
	 * @param key
	 */
	public void start(Key key) {
		Doors doors = new Doors();
		boolean authorized = key.turns();
		if(authorized) {
			engine.start();
			updateDashboardDisplay();
			doors.lock();
		}
	}

	private void updateDashboardDisplay() {
		System.out.println("更新仪表盘");
	}
	
}
