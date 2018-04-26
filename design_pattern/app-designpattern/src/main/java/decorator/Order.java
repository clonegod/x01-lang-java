package decorator;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 装饰模式的经典应用！！！
 *
 */
public abstract class Order {
	
	protected List<OrderItem> items = new LinkedList<OrderItem>();

	protected String customerName; //顾客姓名
	
	protected Date salesDate; // 销售日期
	
	
	public void print() {
		for(OrderItem line : items) {
			line.printLine();
		}
	}
	
	public void addItem(OrderItem item) {
		items.add(item);
	}
	
	public void removeItem(OrderItem item) {
		items.remove(item);
	}
	
	public double getGrandTotal() {
		double amt = 0;
		for(OrderItem line : items) {
			amt += line.getSubTotal();
		}
		return amt;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	
}
