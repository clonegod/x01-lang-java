package decorator.coffee.decorator;

import decorator.coffee.base.Beverage;
import decorator.coffee.base.CondimentDecorator;

public class Whip extends CondimentDecorator {

	Beverage beverage;
	
	public Whip(Beverage beverage) {
		this.beverage = beverage;
	}
	
	@Override
	public String getDescription() {
		return beverage.getDescription() + ", Whip";
	}

	@Override
	public double cost() {
		return .40 + beverage.cost();
	}

}
