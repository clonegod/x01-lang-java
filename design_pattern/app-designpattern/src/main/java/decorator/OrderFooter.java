package decorator;

import com.hqh.util.AppUtil;

public class OrderFooter extends OrderDecorator {

	public OrderFooter(Order order) {
		super(order);
	}

	public void print() {
		super.order.print();
		this.printFooter();
	}
	
	public void printFooter() {
		System.out.println("==============================================");
		System.out.println("Total\t\t\t\t\t"
				+ AppUtil.formatCurrency(super.order.getGrandTotal()));
	}
	
}
