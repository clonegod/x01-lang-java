package strategy.booksales;

public abstract class DiscountStrategy {
	
	protected double price; //图书单价
	
	protected int copies; // 购买册数
	
	public DiscountStrategy(double price, int copies) {
		this.price = price;
		this.copies = copies;
	}

	/**
	 * 策略方法：计算折扣总额
	 * @return
	 */
	public abstract double calculateDiscount();
}
