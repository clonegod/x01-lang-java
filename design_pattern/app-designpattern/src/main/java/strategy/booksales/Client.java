package strategy.booksales;

public class Client {
	public static void main(String[] args) {
		double price = 100.00;
		int copies = 10;
		double amount = 1.02;
		double percent = 0.10;
		
		DiscountStrategy strategy1 = new NoDiscountStrategy(price, copies);
		System.out.println(strategy1.calculateDiscount());
		
		DiscountStrategy strategy2 = new FlatRateStrategy(price, copies, amount);
		System.out.println(strategy2.calculateDiscount());

		DiscountStrategy strategy3 = new PercentageStrategy(price, copies, percent);
		System.out.println(strategy3.calculateDiscount());
		
	}
}
