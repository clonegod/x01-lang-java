package decorator;

import java.util.Date;

public class Client {
	public static void main(String[] args) {
		Order order = new OrderBody();
		order.setCustomerName("张三");
		order.setSalesDate(new Date());
		
		OrderItem coffee = new OrderItem();
		coffee.name("咖啡").units(10).unitPrice(3.05);

		OrderItem water = new OrderItem();
		water.name("农夫三泉").units(5).unitPrice(0.88);
		
		order.addItem(coffee);
		order.addItem(water);
		
		// 第一版发票
		Order orderV1 = new OrderHeader(new OrderFooter(order));
		orderV1.print();
		
		System.out.println("\n\n\n");

		// 第二版发票
		Order orderV2 = new OrderHeaderV2(new OrderFooterV2(order));
		orderV2.print();
	}
}
