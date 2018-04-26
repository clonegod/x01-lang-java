package decorator.coffee;

import decorator.coffee.base.Beverage;
import decorator.coffee.coffee.DarkRoast;
import decorator.coffee.coffee.Espresso;
import decorator.coffee.coffee.HouseBlend;
import decorator.coffee.decorator.Mocha;
import decorator.coffee.decorator.Soy;
import decorator.coffee.decorator.Whip;

public class TestCoffeeCost {
	
	public static void main(String args[]) {
		
		Beverage beverage = new Espresso(Beverage.SIZE.TALL);
		System.out.println(beverage.getDescription() + " $" + beverage.cost());
		
		Beverage beverage2 = new DarkRoast(Beverage.SIZE.GRANDE);
		beverage2 = new Mocha(beverage2);
		beverage2 = new Mocha(beverage2);
		beverage2 = new Whip(beverage2);
		System.out.println(beverage2.getDescription() + " $" + beverage2.cost());
		
		Beverage beverage3 = new HouseBlend(Beverage.SIZE.VENTI);
		beverage3 = new Soy(beverage3);
		beverage3 = new Mocha(beverage3);
		beverage3 = new Whip(beverage3);
		System.out.println(beverage3.getDescription() + " $" + beverage3.cost());
	}
}
