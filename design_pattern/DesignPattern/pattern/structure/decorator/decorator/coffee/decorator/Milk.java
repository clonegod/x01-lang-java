package decorator.coffee.decorator;

import decorator.coffee.base.Beverage;
import decorator.coffee.base.CondimentDecorator;

public class Milk extends CondimentDecorator {

	Beverage beverage;
	
	public Milk(Beverage beverage) {
		this.beverage = beverage;
	}
	
	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Milk";
	}

	@Override
	public double cost() {
		return .10 + beverage.cost();
	}

}
