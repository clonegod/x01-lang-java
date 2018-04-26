package adapter;

public class VIPAdapter implements VIP {
	
	Customer adaptee;
	
	public VIPAdapter(Customer adaptee) {
		this.adaptee = adaptee;
	}
	
	/**
	 * 此接口提供转换功能
	 */
	public void showVip() {
		adaptee.showCustomer();
	}

}
