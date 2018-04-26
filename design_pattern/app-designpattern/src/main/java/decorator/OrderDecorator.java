package decorator;

public class OrderDecorator extends Order {
	protected Order order;

	public OrderDecorator(Order order) {
		this.order = order;
		this.setSalesDate(order.getSalesDate());
		this.setCustomerName(order.getCustomerName());
	}
	
	public void print() {
		super.print();
	}
	
	
}
