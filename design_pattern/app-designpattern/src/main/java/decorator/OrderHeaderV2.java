package decorator;

import com.hqh.util.AppUtil;

public class OrderHeaderV2 extends OrderDecorator {

	public OrderHeaderV2(Order order) {
		super(order);
	}
	
	public void print() {
		this.printHeader();
		super.order.print();
	}
	
	public void printHeader() {
		System.out.println("\t***I N V O I C E***\t");
		System.out.println("XYZ Incorporated\nDate of Sale: ");
		System.out.println(AppUtil.parseCurrentDateTime(order.getSalesDate()));
		System.out.println("==============================================");
		System.out.println("Item\t\tUnits\tUnit Price\tSubtotal");
	}

}
