package bridge;

import bridge.driver.MySqlDriver;
import bridge.driver.OracleDriver;

public class TestBridge {
	public static void main(String[] args) {
		Bridge bridge01 = new BridgeImpl();
		bridge01.setDriver(new MySqlDriver());
		bridge01.doConnection();
		
		
		Bridge bridge02 = new BridgeImpl();
		bridge02.setDriver(new OracleDriver());
		bridge02.doConnection();
		
	}
}
