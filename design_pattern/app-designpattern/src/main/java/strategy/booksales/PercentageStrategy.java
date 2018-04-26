
package strategy.booksales;

public class PercentageStrategy extends DiscountStrategy {
	
	private double percent;

	public PercentageStrategy(double price, int copies, double percent) {
		super(price, copies);
		this.percent = percent;
	}

	@Override
	public double calculateDiscount() {
		return copies * price * percent;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}
	
}
