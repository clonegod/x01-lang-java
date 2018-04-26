package decorator;

public class OrderHeader extends OrderDecorator {

	public OrderHeader(Order order) {
		super(order);
	}
	
	public void print() {
		this.printHeader();
		super.order.print();
	}
	
	public void printHeader() {
		System.out.println("\t***I N V O I C E***\t");
		System.out.println("XYZ Incorporated\nDate of Sale: ");
		System.out.println(order.getSalesDate());
		System.out.println("==============================================");
		System.out.println("Item\t\tUnits\tUnit Price\tSubtotal");
	}

}
