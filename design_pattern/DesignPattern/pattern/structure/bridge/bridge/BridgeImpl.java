package bridge;


public class BridgeImpl extends Bridge {
	
	@Override
	public void doConnection() {
		super.getDriver().createConn();
	}

}
