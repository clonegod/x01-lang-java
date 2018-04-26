
package strategy.booksales;

public class FlatRateStrategy extends DiscountStrategy {
	
	private double amount; // 固定折扣 1元

	public FlatRateStrategy(double price, int copies, double amount) {
		super(price, copies);
		this.amount = amount;
	}

	@Override
	public double calculateDiscount() {
		return copies * amount; // 册数 * 固定折扣
	}
	
}
