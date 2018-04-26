
package strategy.booksales;

public class NoDiscountStrategy extends DiscountStrategy {
	
	public NoDiscountStrategy(double price, int copies) {
		super(price, copies);
	}

	@Override
	public double calculateDiscount() {
		return 0; //无折扣
	}

}
